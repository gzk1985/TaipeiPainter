package gzk.TaipeiPainter;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import gzk.TaipeiPainter.dao.SqliteConnector;
import gzk.TaipeiPainter.dao.SqliteDAO;
import gzk.TaipeiPainter.dao.XlsxLoader;
import gzk.TaipeiPainter.entity.DoortabletInfo;
import gzk.TaipeiPainter.exporter.SimplePdfExporter;

/**
 * Hello world!
 *
 */
public class App 
{private static final Logger LOG = LogManager.getLogger(App.class);
    public static void main( String[] args )
    {
    	LOG.info( "Hello World!" );
        
        try (Workbook book = XlsxLoader.getWorkbook("台北畫家管理費資訊.xlsx");){
			
			SqliteDAO.saveOwnerDoortabletInfo(XlsxLoader.parseOwnerDoortabletInfoSheet(book));
			SqliteDAO.saveManagementFeesReceivable(XlsxLoader.parseManagementFeesReceivableSheet(book));
			SimplePdfExporter export = new SimplePdfExporter();
			for(DoortabletInfo door:SqliteDAO.getDoortabletInfoList()) {
				export.exportPdfReport(door);
			}
        } catch (IOException e) {
			LOG.error(e);
		} finally {
			SqliteConnector.getInstance().clean();
		}
    }
}
