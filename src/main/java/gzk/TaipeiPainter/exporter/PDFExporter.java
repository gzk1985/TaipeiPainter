package gzk.TaipeiPainter.exporter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gzk.TaipeiPainter.entity.OwnerDoortabletInfo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class PDFExporter {
	private String reportRootPath = System.getProperty("user.dir")+"/output/";;
	private String templateRootPath ;
	public PDFExporter(){

	}
	/**
	 * 產生PDF類別
	 * @param portfolioMainInfoList TITLE資料
	 * @param portfolioWeightsPdfInfoList 詳細資料
	 * @param portfolioProposalPo 建議書資料
	 * @param portfolioPdf 總金額
	 */
	public JasperPrint createSamplePdfReport(List<OwnerDoortabletInfo> portfolioMainInfoList) {
		log.info("PdfReportRepositoryImpI createSamplePdfReport.");
		Map map = new HashMap<String, Object>();
		List<ExampleInfo> modelList = new ArrayList();

		// 報表TITLE文字設定
		map.put("commodityName", portfolioMainInfoList.get(PortfolioConst.LIST_FIRST_INDEX).getPortfolioName());
		map.put("commodity", this.checkCommodity(portfolioMainInfoList));
		map.put("commodityDate", DateUtils.getFormatDate(DateUtils.parseDate(portfolioMainInfoList.get(PortfolioConst.LIST_FIRST_INDEX).getReleaseDate())));
		map.put("commodityMoney", new DecimalFormat(CommonPattern.DEFAULT_MONEY_FORMAT).format(portfolioPdf.getInvAmt()).toString());
		map.put("riskLeavel", portfolioMainInfoList.get(PortfolioConst.LIST_FIRST_INDEX).getRiskLevel());
		map.put("proposal", portfolioProposalPo != null ? portfolioProposalPo.getProposal() : "");

		// 資料LIST設定
		portfolioWeightsPdfInfoList.forEach(temp -> {
			ExampleInfo exampleInfo = new ExampleInfo();
			exampleInfo.setCommodityType(this.checkCommodityPoolType(temp.getCommodityPoolType()));
			exampleInfo.setCommodityPool(temp.getCommodityId());
			exampleInfo.setCommodityName(temp.getCommodityPoolName());
			exampleInfo.setInvestmentAmount("9999"); // 測試
			exampleInfo.setPrice("8888"); // 測試
			exampleInfo.setRate("7777"); // 測試
			exampleInfo.setUnits("6666"); // 測試
			exampleInfo.setCommodityWeight(temp.getCommodityWeight());
			modelList.add(exampleInfo);
		});
		try {
			// 讀取jrxml的InputStream
			JasperDesign design = JRXmlLoader.load(jrxml.getInputStream());
			// 讀取製作的jrxml檔案並轉換成compileReport
			JasperReport report = JasperCompileManager.compileReport(design);
			// 透過compile過的JasperReport製作JasperPrint
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, new JRBeanCollectionDataSource(modelList));
			log.info("PdfReportRepositoryImpI JasperFillManager.fillReport({}, {}, {})", jrxml.getURL(), map, modelList);
			return jasperPrint;
		} catch (Exception e) {
			log.error("errorMsg: {}.", e.getMessage());
		} 
	}
}
