package gzk.TaipeiPainter.exporter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gzk.TaipeiPainter.dao.SqliteConnector;
import gzk.TaipeiPainter.dao.SqliteDAO;
import gzk.TaipeiPainter.entity.DoortabletInfo;
import net.sf.jasperreports.engine.*;

public class SimplePdfExporter {
	private static final Logger LOG = LogManager.getLogger(SqliteDAO.class);
	private final String reportRootPath = System.getProperty("user.dir");
	private File outputDir ;
	public SimplePdfExporter(){
		this.outputDir = new File(reportRootPath,"output");
		if(!this.outputDir.exists()) {
			this.outputDir.mkdir();
		}else {
			for(File a : this.outputDir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.endsWith(".pdf")) {
						return true ;
					}
					return false;
				}
				
			})) {
				a.delete();
			}
		}
		
	}
	/**
	 * 產生PDF類別
	 * @param portfolioMainInfoList TITLE資料
	 * @param portfolioWeightsPdfInfoList 詳細資料
	 * @param portfolioProposalPo 建議書資料
	 * @param portfolioPdf 總金額
	 */
	public void exportPdfReport(DoortabletInfo doortabletInfo) {
		File fileOutputPath = new File(outputDir,doortabletInfo.getDoortabletCode()+".pdf");
		Map<String,Object> map = new HashMap<>();
		// 報表TITLE文字設定
		map.put("owner_doorplate", doortabletInfo.getDoortablet());
		try (InputStream in = SimplePdfExporter.class.getResourceAsStream("/管理費補繳通知單.jasper")){
			
			// 讀取jrxml的InputStream
//			JasperDesign design = JRXmlLoader.load(in);
//			// 讀取製作的jrxml檔案並轉換成compileReport
//			JasperReport report = JasperCompileManager.compileReport(design);
			// 透過compile過的JasperReport製作JasperPrint
			JasperPrint jasperPrint = JasperFillManager.fillReport(in, map, SqliteConnector.getInstance().getConnection());
			LOG.info(String.format("PDFExporter JasperFillManager.fillReport(%s, %s)", "/管理費補繳通知單.jrxml", doortabletInfo.getDoortablet()));
			JasperExportManager.exportReportToPdfFile(jasperPrint, fileOutputPath.getAbsolutePath());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} 
	}
	
}
