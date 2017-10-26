/**   
* 
*/
package com.ls.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** 
* @ClassName: SendPerInfo 
* @Description: 寄件人信息
* @author huanglei
* @date 2017年8月30日 下午2:21:29 
* @version V1.0    
*/
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class SendPerInfo {
    private String sendId;
    private String sendName;
    private String sendAddr;
    private String sendAddrFull;
    private String sendPhone;
    private Integer status;
	private String createDate;
    private String memberId;
	private City city;
    private Province province;
    private District district;

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
	public String getSendPhone() {
  		return sendPhone;
  	}
  	public void setSendPhone(String sendPhone) {
  		this.sendPhone = sendPhone;
  	}
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getSendAddr() {
		return sendAddr;
	}
	public void setSendAddr(String sendAddr) {
		this.sendAddr = sendAddr;
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
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public String getSendAddrFull() {
		return sendAddrFull;
	}
	public void setSendAddrFull(String sendAddrFull) {
		this.sendAddrFull = sendAddrFull;
	}
}
