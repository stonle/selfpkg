package com.ls.cenum;

public enum PkgPaymentEnum {
	NO_PAYMENT(1),
	PAYMENT(0);
	private int index;
	private PkgPaymentEnum(int index){
		this.setIndex(index);
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
