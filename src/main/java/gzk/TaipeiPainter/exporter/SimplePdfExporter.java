package gzk.TaipeiPainter.exporter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gzk.TaipeiPainter.dao.SqliteConnector;
import gzk.TaipeiPainter.dao.SqliteDAO;
import gzk.TaipeiPainter.entity.DoortabletInfo;
import net.sf.jasperreports.engine.*;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class SimplePdfExporter {
	private static final Logger LOG = LogManager.getLogger(SqliteDAO.class);
	private final String reportRootPath = System.getProperty("user.dir");
	private File outputDir ;
	private String jasperFile ;
	public SimplePdfExporter(String jasperFile){
		this.jasperFile = jasperFile.startsWith("/")?jasperFile:"/"+jasperFile ;
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
	 * 產生PDF報表
	 * @param doortabletInfo
	 */
	public void exportPdfReport(DoortabletInfo doortabletInfo,String thisDate) {
		File fileOutputPath = new File(outputDir,doortabletInfo.getDoortabletCode()+".pdf");
		Map<String,Object> map = new HashMap<>();
		// 報表TITLE文字設定
		map.put("owner_doorplate", doortabletInfo.getDoortablet());
		map.put("receiver", doortabletInfo.getReceiverName());
		map.put("this_date",thisDate);
		try (InputStream in = SimplePdfExporter.class.getResourceAsStream(this.jasperFile)){
			
			// 讀取jrxml的InputStream
//			JasperDesign design = JRXmlLoader.load(in);
//			// 讀取製作的jrxml檔案並轉換成compileReport
//			JasperReport report = JasperCompileManager.compileReport(design);
			// 透過compile過的JasperReport製作JasperPrint
			try(Connection conn = SqliteConnector.getInstance().getConnection()){
				JasperPrint jasperPrint = JasperFillManager.fillReport(in, map, conn);
				LOG.info(String.format("PDFExporter JasperFillManager.fillReport(%s, %s)", "/管理費補繳通知單.jrxml", doortabletInfo.getDoortablet()));
				JasperExportManager.exportReportToPdfFile(jasperPrint, fileOutputPath.getAbsolutePath());
			}

			// 2) 合併另一個 PDF（例如：resources 下的 /pdf/another.pdf）
			//    你可以把來源改成方法參數或檔案上傳位置
			File anotherPdf = new File(reportRootPath + "/pdf/management_fee_detail.pdf");
			if (anotherPdf.exists()) {
				File merged = new File(outputDir, doortabletInfo.getDoortabletCode() + "_merged.pdf");
				PDFMergerUtility merger = new PDFMergerUtility();
				merger.setDestinationFileName(merged.getAbsolutePath());
				merger.addSource(fileOutputPath);       // Jasper 產出的 PDF
				merger.addSource(anotherPdf);           // 要合併的 PDF
				merger.mergeDocuments(null);

				LOG.info("Merged PDF generated: " + merged.getAbsolutePath());
			} else {
				LOG.warn("Another PDF not found: " + anotherPdf.getAbsolutePath());
			}

		} catch (Exception e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		} 
	}
	
}
