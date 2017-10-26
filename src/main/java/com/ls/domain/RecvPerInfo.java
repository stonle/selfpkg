/**   
* 
*/
package com.ls.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** 
* @ClassName: RecvPerInfo 
* @Description: 收件人信息表
* @author huanglei
* @date 2017年8月30日 下午2:44:16 
* @version V1.0    
*/
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class RecvPerInfo {
    private String recvId;
    private String recvName;
    private String recvAddr;
    private String recvAddrFull;
    private String recvPhone;
    private String createDate;
    private Integer status;
    private String memberId;
    private City city;
    private Province province;
    private District district;
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
		this.district = district;
	}
	public String getRecvId() {
		return recvId;
	}
	public void setRecvId(String recvId) {
		this.recvId = recvId;
	}
	public String getRecvName() {
		return recvName;
	}
	public void setRecvName(String recvName) {
		this.recvName = recvName;
	}
	public String getRecvAddr() {
		return recvAddr;
	}
	public void setRecvAddr(String recvAddr) {
		this.recvAddr = recvAddr;
	}
	public String getRecvPhone() {
		return recvPhone;
	}
	public void setRecvPhone(String recvPhone) {
		this.recvPhone = recvPhone;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getRecvAddrFull() {
		return recvAddrFull;
	}
	public void setRecvAddrFull(String recvAddrFull) {
		this.recvAddrFull = recvAddrFull;
	}
   
}
