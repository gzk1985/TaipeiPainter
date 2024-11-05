package gzk.TaipeiPainter.exporter;

import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import gzk.TaipeiPainter.dao.SqliteConnector;
import gzk.TaipeiPainter.dao.SqliteDAO;
import gzk.TaipeiPainter.dao.XlsxLoader;
import gzk.TaipeiPainter.entity.DoortabletInfo;

public class PdfPrinter {
	private static final Logger LOG = LogManager.getLogger(PdfPrinter.class);
	public static void print(String settingsFileName,String jasperFile) {
        try (Workbook book = XlsxLoader.getWorkbook(settingsFileName);){
			
			SqliteDAO.saveOwnerDoortabletInfo(XlsxLoader.parseOwnerDoortabletInfoSheet(book));
			SqliteDAO.saveManagementFeesReceivable(XlsxLoader.parseManagementFeesReceivableSheet(book));
			SimplePdfExporter export = new SimplePdfExporter(jasperFile);
			for(DoortabletInfo door:SqliteDAO.getDoortabletInfoList()) {
				export.exportPdfReport(door);
			}
        } catch (IOException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {
			SqliteConnector.getInstance().clean();
		}
	}
}
