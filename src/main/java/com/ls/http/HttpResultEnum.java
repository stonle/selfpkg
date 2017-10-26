package com.ls.http;

public enum HttpResultEnum {
	//0:支付成 1：默认值，或者支付失败，未支付
    SUCCESS(0),
    FAIL(1);
	private int index;
    private HttpResultEnum(int index){
	   this.setIndex(index);
    }
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
