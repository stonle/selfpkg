package com.ls.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ls.util.DateTimeUtil;
import com.ls.util.StringUtil;

/**
 * 
* @ClassName: Member 
* @Description: 会员信息表
* @author huanglei
* @date 2017年8月30日 上午10:40:46 
* @version V1.0
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class MemberInfo {
	/**
	 * 会员id
	 */
    private String memberId;
    /**
     * 会员姓名
     */
    private String memberName;
    /**
     * 会员手机号
     */
    private String memberPhone;
    /**
     * 身份证号
     */
    private String memberCard;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 密匙
     */
    private String salt;
    /**
     * 类型
     */
    private Integer memberType;
    /**
     * 注册时间
     */
    private String createDate;
    /**
     * 最后登录时间
     */
    private String lostLoginDate;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态：0：冻结，删除  1：活跃
     */
    private Integer status;
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getMemberCard() {
		return memberCard;
	}
	public void setMemberCard(String memberCard) {
		this.memberCard = memberCard;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
   
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public String getCreateDate() {
		if(StringUtil.isNotEmpty(createDate)){
			return DateTimeUtil.convertFormatDate(createDate);
		}
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLostLoginDate() {
		if(StringUtil.isNotEmpty(lostLoginDate)){
			return DateTimeUtil.convertFormatDate(lostLoginDate);
		}
		return lostLoginDate;
	}
	public void setLostLoginDate(String lostLoginDate) {
		this.lostLoginDate = lostLoginDate;
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
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	@Override
	public String toString() {
		return "MemberInfo [memberId=" + memberId + ", memberName=" + memberName + ", memberPhone=" + memberPhone
				+ ", memberCard=" + memberCard + ", passWord=" + passWord + ", salt=" + salt + ", memberType="
				+ memberType + ", createDate=" + createDate + ", lostLoginDate=" + lostLoginDate + ", remark=" + remark
				+ ", status=" + status + "]";
	}
   
    
}
