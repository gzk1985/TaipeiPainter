package gzk.TaipeiPainter.entity;

import java.math.BigDecimal;

public class ManagementFeesReceivable {
	private String doortablet ;
	private String receiver ;
	private String beginDate ;
	private String endDate ;
	private int carNum = 0 ;
	private int motorcycleNum = 0 ;
	private String paymentRmk ;
	private BigDecimal otherAmount ;
	
	public String getDoortablet() {
		return doortablet;
	}
	public void setDoortablet(String doortablet) {
		this.doortablet = doortablet;
	}
	
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getCarNum() {
		return carNum;
	}
	public void setCarNum(int carNum) {
		this.carNum = carNum;
	}
	public int getMotorcycleNum() {
		return motorcycleNum;
	}
	public void setMotorcycleNum(int motorcycleNum) {
		this.motorcycleNum = motorcycleNum;
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
	
}
