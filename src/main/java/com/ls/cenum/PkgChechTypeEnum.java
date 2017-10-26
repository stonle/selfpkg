package com.ls.cenum;
/**
 * 0:验视成功；1：失败 2：未做处理；3：正在处理中
 * @author Administrator
 *
 */
public enum PkgChechTypeEnum {
    SUCCESS(0),
    FAIL(1),
    NO_HANDLE(2),
    HANDLED(3);
	private int index;
	private PkgChechTypeEnum(int index){
		this.setIndex(index);
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
