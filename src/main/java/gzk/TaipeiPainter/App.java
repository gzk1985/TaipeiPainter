package gzk.TaipeiPainter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import gzk.TaipeiPainter.exporter.PdfPrinter;

/**
 * Hello world!
 *
 */
public class App {
	private static final Logger LOG = LogManager.getLogger(App.class);
    public static void main( String[] args ){
    	LOG.info( "Hello World!" );
        if(args.length < 2) {
        	LOG.error("需要兩個參數，第一個為設定檔路徑，第二個為通知單日期(YYYY-MM-DD)");
        	return ;
        }
        String settingsFilePath = args[0];
        String thisDate = args[1].replace("/","-");
        //檢查 thisDate 格式是否正確 YYYY-MM-DD
        if(!thisDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
        	LOG.error("通知單日期格式錯誤，應為 YYYY-MM-DD");
        	return ;
        }
        PdfPrinter.print(settingsFilePath,"管理費補繳通知單-20251230.jasper",thisDate);
    }
}
