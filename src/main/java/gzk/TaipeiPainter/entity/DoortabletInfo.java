package gzk.TaipeiPainter.entity;

public class DoortabletInfo {

	/**
	 * 門牌
	 */
	private String doortablet ;
	/**
	 * 區權人
	 */
	private String ownerName ;
	/**
	 * 收款對象
	 */
	private String receiverName;
	/**
	 * 門牌代碼
	 */
	private String doortabletCode ;
	/**
	 * 建物坪數
	 */
	private double numberfSquareMeters ;
	/**
	 * 車位坪數
	 */
	private double parkingSpaceSquareMeters ;
	/**
	 * 管理費坪數
	 */
	private double numberOfSquareMetersForManagementFee ;
	/**
	 * 每坪管理費
	 */
	private double baseManagementFee ;
	/**
	 * 管理費
	 */
	private double managementFee ;
	/**
	 * 車位數量
	 */
	private int carNum;
	/**
	 * 機車位數量
	 */
	private int motorcycleNum;
	/**
	 * 減免管理費比例
	 */
	private double reductionRate ;

	/**
	 * 扣除減免後管理費
	 */
	private double adjustedManagementFee ;
	/**
	 * 汽車清潔費
	 */
	private double carCleaningFee ;
	/**
	 * 機車清潔費
	 */
	private double motorcycleCleaningFee ;
	/**
	 * 每月應收管理費
	 */
	private double monthlyManagementFee ;
	/**
	 * 是否列印
	 */
	private boolean printable ;



	public String getDoortablet() {
		return doortablet;
	}
	public void setDoortablet(String doortablet) {
		this.doortablet = doortablet;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getDoortabletCode() {
		return doortabletCode;
	}
	public void setDoortabletCode(String doortabletCode) {
		this.doortabletCode = doortabletCode;
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
	public double getBaseManagementFee() {
		return baseManagementFee;
	}
	public void setBaseManagementFee(double baseManagementFee) {
		this.baseManagementFee = baseManagementFee;
	}
	public double getNumberfSquareMeters() {
		return numberfSquareMeters;
	}
	public void setNumberfSquareMeters(double numberfSquareMeters) {
		this.numberfSquareMeters = numberfSquareMeters;
	}
	public double getMonthlyManagementFee() {
		return monthlyManagementFee;
	}
	public void setMonthlyManagementFee(double monthlyManagementFee) {
		this.monthlyManagementFee = monthlyManagementFee;
	}
	public boolean isPrintable() {
		return printable;
	}
	public void setPrintable(boolean printable) {
		this.printable = printable;
	}

	public double getNumberOfSquareMetersForManagementFee() {
		return numberOfSquareMetersForManagementFee;
	}

	public void setNumberOfSquareMetersForManagementFee(double numberOfSquareMetersForManagementFee) {
		this.numberOfSquareMetersForManagementFee = numberOfSquareMetersForManagementFee;
	}

	public double getParkingSpaceSquareMeters() {
		return parkingSpaceSquareMeters;
	}

	public void setParkingSpaceSquareMeters(double parkingSpaceSquareMeters) {
		this.parkingSpaceSquareMeters = parkingSpaceSquareMeters;
	}

	public double getManagementFee() {
		return managementFee;
	}

	public void setManagementFee(double managementFee) {
		this.managementFee = managementFee;
	}

	public double getReductionRate() {
		return reductionRate;
	}

	public void setReductionRate(double reductionRate) {
		this.reductionRate = reductionRate;
	}

	public double getAdjustedManagementFee() {
		return adjustedManagementFee;
	}

	public void setAdjustedManagementFee(double adjustedManagementFee) {
		this.adjustedManagementFee = adjustedManagementFee;
	}

	public double getCarCleaningFee() {
		return carCleaningFee;
	}

	public void setCarCleaningFee(double carCleaningFee) {
		this.carCleaningFee = carCleaningFee;
	}

	public double getMotorcycleCleaningFee() {
		return motorcycleCleaningFee;
	}

	public void setMotorcycleCleaningFee(double motorcycleCleaningFee) {
		this.motorcycleCleaningFee = motorcycleCleaningFee;
	}

	@Override
	public String toString() {
		return "DoortabletInfo{" +
				"doortablet='" + doortablet + '\'' +
				", ownerName='" + ownerName + '\'' +
				", receiverName='" + receiverName + '\'' +
				", doortabletCode='" + doortabletCode + '\'' +
				", numberfSquareMeters=" + numberfSquareMeters +
				", parkingSpaceSquareMeters=" + parkingSpaceSquareMeters +
				", numberOfSquareMetersForManagementFee=" + numberOfSquareMetersForManagementFee +
				", baseManagementFee=" + baseManagementFee +
				", managementFee=" + managementFee +
				", carNum=" + carNum +
				", motorcycleNum=" + motorcycleNum +
				", reductionRate=" + reductionRate +
				", adjustedManagementFee=" + adjustedManagementFee +
				", carCleaningFee=" + carCleaningFee +
				", motorcycleCleaningFee=" + motorcycleCleaningFee +
				", monthlyManagementFee=" + monthlyManagementFee +
				", printable=" + printable +
				'}';
	}
}
