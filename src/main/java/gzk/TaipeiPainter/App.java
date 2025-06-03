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
        PdfPrinter.print(args[0],"管理費補繳通知單-20250204.jasper");
    }
}
