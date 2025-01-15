package gzk.TaipeiPainter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gzk.TaipeiPainter.entity.ManagementFeesReceivable;
import gzk.TaipeiPainter.entity.DoortabletInfo;


public class SqliteDAO {
	private static final Logger LOG = LogManager.getLogger(SqliteDAO.class);
	
	public static List<DoortabletInfo> getDoortabletInfoList(){
		String sql = "SELECT i.doortablet,i.doortablet_code,i.owner_name,i.printable,r.receiver\n"
				+ "FROM doortablet_info AS i\n"
				+ "LEFT JOIN (\n"
				+ "SELECT DISTINCT doortablet, receiver FROM management_fees_receivable\n"
				+ ") AS r ON (i.doortablet = r.doortablet)\n"
				+ "WHERE i.base_management_fee > 0 ;";
		List<DoortabletInfo> list = new ArrayList<>();
		try(Connection conn = SqliteConnector.getInstance().getConnection()){
			conn.setAutoCommit(false);
			try(PreparedStatement pstmt = conn.prepareStatement(sql)){
				try(ResultSet res = pstmt.executeQuery()){
					while(res.next()) {
						DoortabletInfo o = new DoortabletInfo();
						o.setDoortablet(res.getString("doortablet"));
						o.setDoortabletCode(res.getString("doortablet_code"));
						o.setOwnerName(res.getString("owner_name"));
						o.setReceiver(res.getString("receiver"));
						o.setPrintable(res.getBoolean("printable"));
						list.add(o);
					}
				}
			}
		} catch (SQLException e) {
			LOG.error(e);
		}
		return list ;
	}
	
	public static void saveOwnerDoortabletInfo(List<DoortabletInfo> datas) {
		String sql = "INSERT INTO doortablet_info (doortablet,owner_name,doortablet_code,number_of_square_meters,base_management_fee,car_space,motorcycle_space,payment_frequency,monthly_management_fee,printable) VALUES (?,?,?,?,?,?,?,?,?,?) ;";
		try(Connection conn = SqliteConnector.getInstance().getConnection()){
			conn.setAutoCommit(false);
			try(PreparedStatement pstmt = conn.prepareStatement(sql)){
				datas.forEach(obj -> {
					try {
						pstmt.clearParameters();
						pstmt.setString(1, obj.getDoortablet());
						pstmt.setString(2, obj.getOwnerName());
						pstmt.setString(3, obj.getDoortabletCode());
						pstmt.setDouble(4, obj.getNumberfSquareMeters());
						pstmt.setDouble(5, obj.getBaseManagementFee());
						pstmt.setInt(6, obj.getCarSpace());
						pstmt.setInt(7, obj.getMotorcycleSpace());
						pstmt.setString(8, obj.getPaymentFrequency());
						pstmt.setDouble(9, obj.getMonthlyManagementFee());
						pstmt.setBoolean(10, obj.isPrintable());
						pstmt.addBatch();
					} catch (SQLException e) {
						LOG.error(e);
					}
				});
				pstmt.executeBatch();
				conn.commit();
			}
		} catch (SQLException e) {
			LOG.error(e);
		}
	}
	public static void saveManagementFeesReceivable(List<ManagementFeesReceivable> datas) {
		String sql = "INSERT INTO management_fees_receivable (doortablet,receiver,begin_date,end_date,car_num,motorcycle_num,payment_rmk) VALUES (?,?,?,?,?,?,?) ;";
		try(Connection conn = SqliteConnector.getInstance().getConnection()){
			conn.setAutoCommit(false);
			try(PreparedStatement pstmt = conn.prepareStatement(sql)){
				datas.forEach(obj -> {
					try {
						pstmt.clearParameters();
						pstmt.setString(1, obj.getDoortablet());
						pstmt.setString(2, obj.getReceiver());
						pstmt.setString(3, obj.getBeginDate());
						pstmt.setString(4, obj.getEndDate());
						pstmt.setInt(5, obj.getCarNum());
						pstmt.setInt(6, obj.getMotorcycleNum());
						pstmt.setString(7, obj.getPaymentRmk());
						pstmt.addBatch();
					} catch (SQLException e) {
						LOG.error(e);
					}
				});
				pstmt.executeBatch();
				conn.commit();
			}
		} catch (SQLException e) {
			LOG.error(e);
		}
	}
}
