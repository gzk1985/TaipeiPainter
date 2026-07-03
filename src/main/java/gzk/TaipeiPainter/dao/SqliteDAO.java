package gzk.TaipeiPainter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import gzk.TaipeiPainter.entity.ManagementFeesReceivable;
import gzk.TaipeiPainter.entity.DoortabletInfo;


public class SqliteDAO {
	private static final Logger LOG = LogManager.getLogger(SqliteDAO.class);

	/**
	 * 取得門牌資訊列表
	 * @return
	 */
	public static List<DoortabletInfo> getDoortabletInfoList(){
		String sql = "SELECT i.doortablet,i.doortablet_code,i.owner_name,i.printable,i.receiver_name\n"
				+ "FROM doortablet_info AS i\n"
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
						o.setReceiverName(res.getString("receiver_name"));
						o.setPrintable(res.getBoolean("printable"));
						list.add(o);
					}
				}
			}
		} catch (SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		}
		return list ;
	}

	/**
	 * 儲存門牌資訊列表
	 * @param datas
	 */
	public static void saveOwnerDoortabletInfo(List<DoortabletInfo> datas) {
		String sql = "INSERT INTO doortablet_info (doortablet" +
				",owner_name" +
				",receiver_name" +
				",doortablet_code" +
				",number_of_square_meters" +
				",parking_space_square_meters" +
				",number_of_square_meters_for_management_fee" +
				",base_management_fee" +
				",management_fee" +
				",car_num" +
				",motorcycle_num" +
				",reduction_rate" +
				",adjusted_management_fee" +
				",car_cleaning_fee" +
				",motorcycle_cleaning_fee" +
				",monthly_management_fee" +
				",printable) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ;";
		try(Connection conn = SqliteConnector.getInstance().getConnection()){
			conn.setAutoCommit(false);
			try(PreparedStatement pstmt = conn.prepareStatement(sql)){
				datas.forEach(obj -> {
					try {
						pstmt.clearParameters();
						pstmt.setString(1, obj.getDoortablet());
						pstmt.setString(2, obj.getOwnerName());
						pstmt.setString(3, obj.getReceiverName());
						pstmt.setString(4, obj.getDoortabletCode());
						pstmt.setDouble(5, obj.getNumberfSquareMeters());
						pstmt.setDouble(6, obj.getParkingSpaceSquareMeters());
						pstmt.setDouble(7, obj.getNumberOfSquareMetersForManagementFee());
						pstmt.setDouble(8, obj.getBaseManagementFee());
						pstmt.setDouble(9 ,obj.getManagementFee());
						pstmt.setInt(10, obj.getCarNum());
						pstmt.setInt(11, obj.getMotorcycleNum());
						pstmt.setDouble(12, obj.getReductionRate());
						pstmt.setDouble(13, obj.getAdjustedManagementFee());
						pstmt.setDouble(14, obj.getCarCleaningFee());
						pstmt.setDouble(15, obj.getMotorcycleCleaningFee());
						pstmt.setDouble(16, obj.getMonthlyManagementFee());
						pstmt.setBoolean(17, obj.isPrintable());
						pstmt.addBatch();
					} catch (SQLException e) {
						LOG.error(ExceptionUtils.getStackTrace(e));
					}
				});
				pstmt.executeBatch();
				conn.commit();
			}
		} catch (SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		}
	}
	public static void saveManagementFeesReceivable(List<ManagementFeesReceivable> datas) {
		String sql = "INSERT INTO management_fees_receivable (doortablet" +
				",zyymm" +
				",payment_rmk" +
				",other_amount" +
				",reversed) VALUES (?,?,?,?,?) ;";
		try(Connection conn = SqliteConnector.getInstance().getConnection()){
			conn.setAutoCommit(false);
			try(PreparedStatement pstmt = conn.prepareStatement(sql)){
				datas.forEach(obj -> {
					try {
						pstmt.clearParameters();
						pstmt.setString(1, obj.getDoortablet());
						pstmt.setString(2, obj.getZyymm());
						pstmt.setString(3, obj.getPaymentRmk());
						pstmt.setBigDecimal(4, obj.getOtherAmount());
						pstmt.setInt(5, obj.getReversed());
						pstmt.addBatch();
					} catch (SQLException e) {
						LOG.error(ExceptionUtils.getStackTrace(e));
					}
				});
				pstmt.executeBatch();
				conn.commit();
			}
		} catch (SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		}
	}
	//初始化本期應收管理費
	public static void initManagementFeesReceivable(String thisDate){
		String sql = "INSERT INTO management_fees_receivable (doortablet,zyymm,payment_rmk,other_amount,reversed) \n" +
				"SELECT doortablet,date(?) AS zyymm,'本期管理費',NULL,0 \n" +
				"FROM doortablet_info " ;
		try(Connection conn = SqliteConnector.getInstance().getConnection()){
			conn.setAutoCommit(false);
			try(PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.clearParameters();
				pstmt.setString(1, thisDate);
				pstmt.executeUpdate();
				conn.commit();
			}
		} catch (SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		}
	}
}
