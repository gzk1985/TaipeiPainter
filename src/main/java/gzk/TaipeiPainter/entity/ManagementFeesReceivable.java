package gzk.TaipeiPainter.entity;

import java.math.BigDecimal;

public class ManagementFeesReceivable {
	/**
	 * 門牌
	 */
	private String doortablet ;
	/**
	 * 年月
	 */
	private String zyymm;
	/**
	 * 前置說明
	 */
	private String paymentRmk ;
	/**
	 * 應收款項
	 */
	private BigDecimal otherAmount ;

	/**
	 * 已沖銷
	 */
	private int reversed ;
	public String getDoortablet() {
		return doortablet;
	}
	public void setDoortablet(String doortablet) {
		this.doortablet = doortablet;
	}
	public String getZyymm() {
		return zyymm;
	}
	public void setZyymm(String zyymm) {
		this.zyymm = zyymm;
	}
	public String getPaymentRmk() {
		return paymentRmk;
	}
	public void setPaymentRmk(String paymentRmk) {
		this.paymentRmk = paymentRmk;
	}
	public BigDecimal getOtherAmount() {
		return otherAmount;
	}
	public void setOtherAmount(BigDecimal otherAmount) {
		this.otherAmount = otherAmount;
	}

	public int getReversed() {
		return reversed;
	}

	public void setReversed(int reversed) {
		this.reversed = reversed;
	}
}
