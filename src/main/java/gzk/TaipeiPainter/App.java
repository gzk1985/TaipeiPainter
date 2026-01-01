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
        PdfPrinter.print(settingsFilePath,"管理費補繳通知單-20251230.jasper",thisDate);
    }
}
