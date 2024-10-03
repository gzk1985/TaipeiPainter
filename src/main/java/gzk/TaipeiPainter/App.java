package gzk.TaipeiPainter;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

import gzk.TaipeiPainter.dao.SqliteDAO;
import gzk.TaipeiPainter.dao.XlsxLoader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        try (Workbook book = XlsxLoader.getWorkbook("台北畫家管理費.xlsx");){
			
			SqliteDAO.saveOwnerDoortabletInfo(XlsxLoader.parseOwnerDoortabletInfoSheet(book));
			SqliteDAO.saveManagementFeesReceivable(XlsxLoader.parseManagementFeesReceivableSheet(book));
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
