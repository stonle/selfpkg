package com.ls.cenum;

public enum PkgPaymentTypeEnum {
	// 0:现金支付；1：微信支付 ；2 ：扫码支付
	XJ_PAY(0),
	WX_PAY(1),
	SM_PAY(2);
	private int index;
	private PkgPaymentTypeEnum(int index){
		this.setIndex(index);
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
