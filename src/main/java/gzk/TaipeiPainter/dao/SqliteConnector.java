package gzk.TaipeiPainter.dao;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SqliteConnector {
	private static final Logger LOG = LogManager.getLogger(SqliteConnector.class);
	private final String dbLocation = System.getProperty("user.dir")+"/";
	private final String dbName = "台北畫家管理費資料庫.db" ;
	private String dbUrl ;
	private static SqliteConnector INSTANCE ;
	
	private SqliteConnector() {
		dbUrl = String.format("jdbc:sqlite:%s%s",dbLocation, dbName);
		File dbFile = new File(dbLocation,dbName);
		if(dbFile.exists()) {
			try {
				FileUtils.forceDelete(dbFile);
			} catch (IOException e) {
				LOG.error(e);
			}
		}
		this.createNewDatabase();
		this.createManagementFeesReceivable();
		this.createOwnerDoortabletInfo();
	}
	public static SqliteConnector getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new SqliteConnector();
		}
		return INSTANCE ;
	}
    public Connection getConnection() throws SQLException {
    	Connection conn = null ;
        try {
            Class.forName("org.sqlite.JDBC");
            
            conn = DriverManager.getConnection(dbUrl);
        } catch (ClassNotFoundException | SQLException | HeadlessException e) {
            throw new SQLException(e);
        }
        return conn;
    }
    
    
    private void createNewDatabase() {
   	 
        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                LOG.info("The driver name is " + meta.getDriverName());
                LOG.info("A new database has been created.");
            }
 
        } catch (SQLException e) {
            LOG.error(e);
        }
    }
    
	private void createManagementFeesReceivable() {
		String sql = "CREATE TABLE \"management_fees_receivable\" (\n"
				+ "	\"doortablet\"	TEXT,\n"
				+ "	\"begin_date\"	TEXT,\n"
				+ "	\"end_date\"	TEXT,\n"
				+ "	\"car_num\"	INTEGER,\n"
				+ "	\"motorcycle_num\"	INTEGER,\n"
				+ "	\"payment_rmk\"	TEXT,\n"
				+ "	PRIMARY KEY(\"doortablet\",\"begin_date\")\n"
				+ ");";
	       try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    	   conn.setAutoCommit(false);
	            try(Statement stat = conn.createStatement()){
	            	stat.execute(sql);
	            	conn.commit();
	            }
	        } catch (SQLException e) {
	        	LOG.error(e);
	        }
	}
	private void createOwnerDoortabletInfo() {
		String sql = "CREATE TABLE \"doortablet_info\" (\n"
				+ "	\"doortablet\"	TEXT,\n"
				+ "	\"owner_name\"	TEXT,\n"
				+ "	\"doortablet_code\"	TEXT,\n"
				+ "	\"number_of_square_meters\"	NUMERIC,\n"
				+ "	\"base_management_fee\"	NUMERIC,\n"
				+ "	\"car_space\"	INTEGER,\n"
				+ "	\"motorcycle_space\"	INTEGER,\n"
				+ "	\"monthly_management_fee\"	NUMERIC,\n"
				+ "	PRIMARY KEY(\"doortablet\")\n"
				+ ");";
	       try (Connection conn = DriverManager.getConnection(dbUrl)) {
	    	   conn.setAutoCommit(false);
	            try(Statement stat = conn.createStatement()){
	            	stat.execute(sql);
	            	conn.commit();
	            }
	        } catch (SQLException e) {
	        	LOG.error(e);
	        }
	}
	public void clean() {
		File dbFile = new File(dbLocation,dbName);
		if(!dbFile.exists()) {
			try {
				FileUtils.forceDelete(dbFile);
			} catch (IOException e) {
				LOG.error(e);
			}
		}
	}
}
