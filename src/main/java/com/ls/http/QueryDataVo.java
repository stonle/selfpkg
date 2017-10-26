/**   
* 
*/
package com.ls.http;

import java.util.List;

/** 
* @ClassName: QueryDataList 
* @Description: 查询列表返回数据
* @author huanglei
* @date 2017年9月12日 上午8:44:10 
* @version V1.0    
*/
public class QueryDataVo {
    private String terminalNum;
	private int count;
	private List<OrderStatus> dataList;
	private int totalCount;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getTerminalNum() {
		return terminalNum;
	}
	public void setTerminalNum(String terminalNum) {
		this.terminalNum = terminalNum;
	}
	public List<OrderStatus> getDataList() {
		return dataList;
	}
	public void setDataList(List<OrderStatus> dataList) {
		this.dataList = dataList;
	}
	
}
