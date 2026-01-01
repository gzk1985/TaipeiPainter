package gzk.TaipeiPainter.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gzk.TaipeiPainter.entity.ManagementFeesReceivable;
import gzk.TaipeiPainter.entity.DoortabletInfo;

public class XlsxLoader {
	private static final Logger LOG = LogManager.getLogger(XlsxLoader.class);
	private static final String XLS = ".xls";
	private static final String XLSX = ".xlsx";
	public static Workbook getWorkbook(String path) throws IOException {
		Workbook wb = null;
		if (path == null)
			return null;
		String extString = path.substring(path.lastIndexOf("."));
		InputStream is;
		try {
			is = new FileInputStream(path);
			if (XLS.equals(extString)) {
				wb = new HSSFWorkbook(is);
			} else if (XLSX.equals(extString)) {
				wb = new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			throw new IOException(path,e);
		}
		return wb;
	}
	public static List<DoortabletInfo> parseOwnerDoortabletInfoSheet(Workbook workbook) throws IOException {
		List<DoortabletInfo> excelDataList = new ArrayList<>();
		Sheet sheet = workbook.getSheet("門牌");
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		int firstRowNum = sheet.getFirstRowNum();
		Row firstRow = sheet.getRow(firstRowNum);
		int firstRowCellNum = firstRow.getLastCellNum();
		if (firstRowCellNum<14) {
			throw new IOException("cell數不足，解析Excel失敗");
		}

		int rowStart = firstRowNum + 1;
		int rowEnd = sheet.getPhysicalNumberOfRows();
		for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (null == row) {
				continue;
			}
			DoortabletInfo excelData = convertRowToOwnerDoortabletInfo(row,evaluator);
            excelDataList.add(excelData);
		}
		return excelDataList;
	}
	public static List<ManagementFeesReceivable> parseManagementFeesReceivableSheet(Workbook workbook) throws IOException {
		List<ManagementFeesReceivable> excelDataList = new ArrayList<>();
		Sheet sheet = workbook.getSheet("應收管理費");
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		int firstRowNum = sheet.getFirstRowNum();
		Row firstRow = sheet.getRow(firstRowNum);
		int firstRowCellNum = firstRow.getLastCellNum();
		if (firstRowCellNum<5) {
			throw new IOException("cell數不足，解析Excel失敗");
		}

		int rowStart = firstRowNum + 1;
		int rowEnd = sheet.getPhysicalNumberOfRows();
		for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (null == row) {
				continue;
			}
			ManagementFeesReceivable excelData = convertRowToManagementFeesReceivable(row,evaluator);
            excelDataList.add(excelData);
		}
		return excelDataList;
	}

	private static DoortabletInfo convertRowToOwnerDoortabletInfo(Row row,FormulaEvaluator evaluator) {
		DoortabletInfo excelData = new DoortabletInfo();
		Cell cell;
		int cellNum = 0;
		// 門牌
		cell = row.getCell(cellNum++);
		String doortablet = convertCellValueToString(cell,evaluator);
		excelData.setDoortablet(doortablet);
		cell = row.getCell(cellNum++);
		// 區分所有權人
		String ownerName = convertCellValueToString(cell,evaluator);
		excelData.setOwnerName(ownerName);
		cell = row.getCell(cellNum++);
		// 收款對象
		String receiver = convertCellValueToString(cell,evaluator);
		excelData.setReceiverName(receiver);
		cell = row.getCell(cellNum++);
		// 門牌代碼
		String doortabletCode = convertCellValueToString(cell,evaluator);
		excelData.setDoortabletCode(doortabletCode);
		cell = row.getCell(cellNum++);
		// 建物坪數
		String numberOfSquareMetersStr = convertCellValueToString(cell,evaluator);
		double numberOfSquareMeters = Double.parseDouble(numberOfSquareMetersStr);
		excelData.setNumberfSquareMeters(numberOfSquareMeters);
		cell = row.getCell(cellNum++);
		// 車位坪數
		String parkingSpaceSquareMetersStr = convertCellValueToString(cell,evaluator);
		double parkingSpaceSquareMeters = parkingSpaceSquareMetersStr!=null?Double.parseDouble(parkingSpaceSquareMetersStr):0d;
		excelData.setParkingSpaceSquareMeters(parkingSpaceSquareMeters);
		cell = row.getCell(cellNum++);
		// 管理費坪數
		double numberOfSquareMetersForManagementFee = Double.parseDouble(convertCellValueToString(cell,evaluator));
		excelData.setNumberOfSquareMetersForManagementFee(numberOfSquareMetersForManagementFee);
		cell = row.getCell(cellNum++);
		// 每坪管理費
		double baseManagementFee = Double.parseDouble(convertCellValueToString(cell,evaluator));
		excelData.setBaseManagementFee(baseManagementFee);
		cell = row.getCell(cellNum++);
		// 管理費
		double managementFee = Double.parseDouble(convertCellValueToString(cell,evaluator));
		excelData.setManagementFee(managementFee);
		cell = row.getCell(cellNum++);
		// 汽車數量
		int carNum = Integer.parseInt(convertCellValueToString(cell,evaluator));
		excelData.setCarNum(carNum);
		cell = row.getCell(cellNum++);
		// 機車數量
		int motorcycleNum = Integer.parseInt(convertCellValueToString(cell,evaluator));
		excelData.setMotorcycleNum(motorcycleNum);
		cell = row.getCell(cellNum++);
		// 減免管理費比例
		double reductionRate = Double.parseDouble(convertCellValueToString(cell,evaluator));
		excelData.setReductionRate(reductionRate);
		cell = row.getCell(cellNum++);
		// 扣除減免後管理費
		double adjustedManagementFee = Double.parseDouble(convertCellValueToString(cell,evaluator));
		excelData.setAdjustedManagementFee(adjustedManagementFee);
		cell = row.getCell(cellNum++);
		// 汽車清潔費
		double carCleaningFee = Double.parseDouble(convertCellValueToString(cell,evaluator));
		excelData.setCarCleaningFee(carCleaningFee);
		cell = row.getCell(cellNum++);
		// 機車清潔費
		double motorcycleCleaningFee = Double.parseDouble(convertCellValueToString(cell,evaluator));
		excelData.setMotorcycleCleaningFee(motorcycleCleaningFee);
		cell = row.getCell(cellNum++);
		// 每月應收管理費
		int monthlyManagementFee = Integer.parseInt(convertCellValueToString(cell,evaluator));
		excelData.setMonthlyManagementFee(monthlyManagementFee);
		cell = row.getCell(cellNum++);
		// 要列印
		boolean printable = "V".equals(convertCellValueToString(cell, evaluator));
		excelData.setPrintable(printable);
		cell = row.getCell(cellNum++);
		return excelData;
	}
	private static ManagementFeesReceivable convertRowToManagementFeesReceivable(Row row,FormulaEvaluator evaluator) {
		ManagementFeesReceivable excelData = new ManagementFeesReceivable();
		Cell cell;
		int cellNum = 0;
		// 門牌
		cell = row.getCell(cellNum++);
		evaluator.evaluateFormulaCell(cell);
		String doortablet = convertCellValueToString(cell,evaluator);
		excelData.setDoortablet(doortablet);
		cell = row.getCell(cellNum++);
		// 年月 sqllite 日期格式只能支援 yyyy-MM-dd
		String zyymm = convertCellValueToString(cell,evaluator);
		excelData.setZyymm(zyymm.replace('/','-'));
		cell = row.getCell(cellNum++);
		// 款項說明
		String paymentRmk = convertCellValueToString(cell,evaluator);
		excelData.setPaymentRmk(paymentRmk);
		cell = row.getCell(cellNum++);
		// 應收款項
		String otherAmountStr = convertCellValueToString(cell,evaluator) ;
		BigDecimal otherAmount = otherAmountStr == null? null:new BigDecimal(otherAmountStr);
		excelData.setOtherAmount(otherAmount);
		cell = row.getCell(cellNum++);
		// 已沖銷: '':未沖銷,V:已沖銷
		String reversedStr = convertCellValueToString(cell,evaluator) ;
		int reversed = "V".equals(reversedStr)? 1 : 0;
		excelData.setReversed(reversed);
		cell = row.getCell(cellNum++);
		return excelData;
	}

	public static String convertCellValueToString(Cell cell,FormulaEvaluator evaluator) {
		if (cell == null) {
			return null;
		}
		String returnValue = null;
		if (cell.getCellType() == CellType.FORMULA) {
		    switch (evaluator.evaluateFormulaCell(cell)) {
		        case BOOLEAN:
					Boolean booleanValue = cell.getBooleanCellValue();
					returnValue = booleanValue.toString();
		            break;
		        case NUMERIC:
					Double doubleValue = cell.getNumericCellValue();
					DecimalFormat df = new DecimalFormat("0");
					returnValue = df.format(doubleValue);
		            break;
		        case STRING:
		        	returnValue = cell.getStringCellValue();
		            break;
		        default:
		        	break;
		    }
		}else {
			switch (cell.getCellType()) {
			case NUMERIC:
				Double doubleValue = cell.getNumericCellValue();
				DecimalFormat df = new DecimalFormat("0");
				returnValue = df.format(doubleValue);
				break;
			case STRING:
				returnValue = cell.getStringCellValue();
				break;
			case BOOLEAN:
				Boolean booleanValue = cell.getBooleanCellValue();
				returnValue = booleanValue.toString();
				break;
			case BLANK:
				break;
			case FORMULA:
				returnValue = cell.getCellFormula();
				break;
			case ERROR:
				break;
			default:
				break;
			}
		}

		return returnValue;
	}
}
