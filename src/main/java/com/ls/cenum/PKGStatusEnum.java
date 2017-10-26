package com.ls.cenum;

/**
 * 包裹状态
 * 
 * @author Administrator
 *
 */
public enum PKGStatusEnum {
	/**
	 * 包裹状态描述； 
	 * 0:未投入,下单 (手机下单，还没有投入揽收机) 
	 * 1：已投入 (该状态只对于户外有效) 
	 * 2：已取消 (手机取消下单) 
	 * 3：未揽收（户外即快递员未将包裹取回投递部,或自助揽收机包裹未揽收）
	 * 4：已揽收（户外即是快递员将包裹取回投递部,或自助揽收机包裹已揽收） 
	 * 5：已邮寄
	 * 6：已退回 
	 * 7：已签收
	 */
	NO_INPUT(0),
	INPUT(1),
	CANCEL(2),
	NO_GATHER(3),
	CATHER(4),
	SENDBYPOST(5),
    RETURNED(6),
    SIGININ(7);
	private int index;
	private PKGStatusEnum(int index){
		this.setIndex(index);
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
