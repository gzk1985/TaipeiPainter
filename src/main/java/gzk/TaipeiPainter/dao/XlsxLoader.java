package gzk.TaipeiPainter.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gzk.TaipeiPainter.entity.ManagementFeesReceivable;
import gzk.TaipeiPainter.entity.OwnerDoortabletInfo;

public class XlsxLoader {
	private final Logger LOG = LogManager.getLogger(XlsxLoader.class);
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
	public static List<OwnerDoortabletInfo> parseOwnerDoortabletInfoSheet(Workbook workbook) throws IOException {
		List<OwnerDoortabletInfo> excelDataList = new ArrayList<>();
		Sheet sheet = workbook.getSheetAt(1);

		int firstRowNum = sheet.getFirstRowNum();
		Row firstRow = sheet.getRow(firstRowNum);
		int firstRowCellNum = firstRow.getLastCellNum();
		if (null == firstRow||firstRowCellNum<2) {
			throw new IOException("解析Excel失敗");
		}

		int rowStart = firstRowNum + 1;
		int rowEnd = sheet.getPhysicalNumberOfRows();
		for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (null == row) {
				continue;
			}
			OwnerDoortabletInfo excelData = convertRowToOwnerDoortabletInfo(row);
			if (null == excelData) {
				continue;
			}
			excelDataList.add(excelData);
		}
		return excelDataList;
	}
	public static List<ManagementFeesReceivable> parseManagementFeesReceivableSheet(Workbook workbook) throws IOException {
		List<ManagementFeesReceivable> excelDataList = new ArrayList<>();
		Sheet sheet = workbook.getSheetAt(2);

		int firstRowNum = sheet.getFirstRowNum();
		Row firstRow = sheet.getRow(firstRowNum);
		int firstRowCellNum = firstRow.getLastCellNum();
		if (null == firstRow||firstRowCellNum<6) {
			throw new IOException("解析Excel失敗");
		}

		int rowStart = firstRowNum + 1;
		int rowEnd = sheet.getPhysicalNumberOfRows();
		for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (null == row) {
				continue;
			}
			ManagementFeesReceivable excelData = convertRowToManagementFeesReceivable(row);
			if (null == excelData) {
				continue;
			}
			excelDataList.add(excelData);
		}
		return excelDataList;
	}
	private static OwnerDoortabletInfo convertRowToOwnerDoortabletInfo(Row row) {
		OwnerDoortabletInfo excelData = new OwnerDoortabletInfo();
		Cell cell;
		int cellNum = 0;
		// 門牌
		cell = row.getCell(cellNum++);
		String doortablet = convertCellValueToString(cell);
		excelData.setOwnerDoortablet(doortablet);
		cell = row.getCell(cellNum++);
		// 姓名
		String ownerName = convertCellValueToString(cell);
		excelData.setOwnerName(ownerName);
		cell = row.getCell(cellNum++);
		return excelData;
	}
	private static ManagementFeesReceivable convertRowToManagementFeesReceivable(Row row) {
		ManagementFeesReceivable excelData = new ManagementFeesReceivable();
		Cell cell;
		int cellNum = 0;
		// 門牌
		cell = row.getCell(cellNum++);
		String doortablet = convertCellValueToString(cell);
		excelData.setOwnerDoortablet(doortablet);
		cell = row.getCell(cellNum++);
		// 起日
		String beginDate = convertCellValueToString(cell);
		excelData.setBeginDate(beginDate);
		cell = row.getCell(cellNum++);
		// 迄日
		String endDate = convertCellValueToString(cell);
		excelData.setEndDate(endDate);
		cell = row.getCell(cellNum++);
		// 汽車數量
		int carNum = Integer.valueOf(convertCellValueToString(cell));
		excelData.setCarNum(carNum);
		cell = row.getCell(cellNum++);
		// 機車數量
		int motorcycleNum = Integer.valueOf(convertCellValueToString(cell));
		excelData.setMotorcycleNum(motorcycleNum);
		cell = row.getCell(cellNum++);
		// 前置說明
		String paymentRmk = convertCellValueToString(cell);
		excelData.setPaymentRmk(paymentRmk);
		cell = row.getCell(cellNum++);
		return excelData;
	}
	public static String convertCellValueToString(Cell cell) {
		if (cell == null) {
			return null;
		}
		String returnValue = null;
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
		return returnValue;
	}
}
