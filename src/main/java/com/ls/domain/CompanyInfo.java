/**   
* 
*/
package com.ls.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ls.util.DateTimeUtil;

/** 
* @ClassName: Company 
* @Description: 快递公司信息表
* @author huanglei
* @date 2017年9月4日 上午9:30:42 
* @version V1.0    
*/
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class CompanyInfo {
    private Integer copId;
    private String copName;
    private String createDate;
    private String remark;
    private Integer status;
	public Integer getCopId() {
		return copId;
	}
	public void setCopId(Integer copId) {
		this.copId = copId;
	}
	public String getCopName() {
		return copName;
	}
	public void setCopName(String copName) {
		this.copName = copName;
	}
	public String getCreateDate() {
		return DateTimeUtil.convertFormatDate(createDate);
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "CompanyInfo [copId=" + copId + ", copName=" + copName + ", createDate=" + createDate + ", remark="
				+ remark + ", status=" + status + "]";
	}
   
   
}
