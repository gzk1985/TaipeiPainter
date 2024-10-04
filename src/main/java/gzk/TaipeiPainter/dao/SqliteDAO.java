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
		String sql = "SELECT * FROM doortablet_info WHERE base_management_fee > 0 ;";
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
		String sql = "INSERT INTO doortablet_info (doortablet,owner_name,doortablet_code,number_of_square_meters,base_management_fee,car_space,motorcycle_space,monthly_management_fee) VALUES (?,?,?,?,?,?,?,?) ;";
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
						pstmt.setDouble(8, obj.getMonthlyManagementFee());
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
		String sql = "INSERT INTO management_fees_receivable (doortablet,begin_date,end_date,car_num,motorcycle_num,payment_rmk) VALUES (?,?,?,?,?,?) ;";
		try(Connection conn = SqliteConnector.getInstance().getConnection()){
			conn.setAutoCommit(false);
			try(PreparedStatement pstmt = conn.prepareStatement(sql)){
				datas.forEach(obj -> {
					try {
						pstmt.clearParameters();
						pstmt.setString(1, obj.getDoortablet());
						pstmt.setString(2, obj.getBeginDate());
						pstmt.setString(3, obj.getEndDate());
						pstmt.setInt(4, obj.getCarNum());
						pstmt.setInt(5, obj.getMotorcycleNum());
						pstmt.setString(6, obj.getPaymentRmk());
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
