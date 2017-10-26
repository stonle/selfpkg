package com.ls.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.cenum.DateEnum;
import com.ls.dao.MemberDao;
import com.ls.domain.MemberInfo;
import com.ls.service.MemberService;
import com.ls.util.DateTimeUtil;
import com.ls.util.EncrypMD5;
import com.ls.util.GuidCreatorUtil;
import com.ls.util.Page;
import com.ls.util.StringUtil;
@Service
public class MemberServiceImpl extends BaseServiceImpl<MemberInfo, String> implements MemberService {
    
	@Autowired
	private MemberDao memberDaoImpl;
	@Override
	public List<MemberInfo> listPersonelByPage(Page page, MemberInfo pesonle) {
		return memberDaoImpl.listPersonelByPage(page, pesonle);
	}

	@Override
	public int getTotalCount(MemberInfo pesonle) {
		return memberDaoImpl.getTotalCount(pesonle);
	}
    
	/**
	 *添加android会员注册的用户
	 */
	@Override
	public void saveMemberInfo(MemberInfo pesonle) {
		try {
			if (pesonle == null) {
				throw new RuntimeException("Object_Null_Error");
			}
			String cellPhone = StringUtil.filterString(pesonle.getMemberPhone());
			if (cellPhone.equals("")) {
				throw new RuntimeException("CellPhone is not empty!");
			}
			if(!StringUtil.isNotEmpty(pesonle.getPassWord())){
				throw new RuntimeException("Password is not empty!");
			}
			
			//手机号的合法性验证
			if(!StringUtil.isMobiPhoneNum(cellPhone)){
				throw new RuntimeException("Invalid phone number!");
			}
			//手机号的唯一性校验
			MemberInfo u = memberDaoImpl.getMemberyInfoByMemberPhone(cellPhone);
			if(u != null){
				throw new RuntimeException("cellPhone already exists, please re-enter...");
			}
			pesonle.setMemberId(new GuidCreatorUtil().toString());
			pesonle.setCreateDate(DateTimeUtil.getFormatDate());
			//pesonle.setPassWord(EncrypMD5.getMD5Code(StringUtil.filterString(pesonle.getPassWord())));
			//用户状态  0:表示删除    1：未删除
			pesonle.setStatus(DateEnum.NO_DELETE.getIndex());
			memberDaoImpl.saveObject(pesonle);
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public String checkMemberLogin(MemberInfo member) {
		MemberInfo memberInfo = memberDaoImpl.getMemberyInfoByMemberPhone(member.getMemberPhone());
		if(memberInfo==null){
			throw new RuntimeException("LoginName not exists, please re-enter...") ;
		}else{
			if(!member.getPassWord().equals(memberInfo.getPassWord())){
				throw new RuntimeException("password error, please re-enter...");
			}
		}
		//设置登录时间
		memberInfo.setLostLoginDate(DateTimeUtil.getFormatDate());
	   return memberInfo.getMemberId();
	}


	@Override
	public MemberInfo queryMemberInfoById(String memberId) {
		return memberDaoImpl.getObjectById(MemberInfo.class, memberId);
	}

	@Override
	public void updateMemberInfo(MemberInfo member) {
		MemberInfo memberInfo = memberDaoImpl.getObjectById(MemberInfo.class, member.getMemberId());
		memberInfo.setMemberPhone(member.getMemberPhone());
		memberInfo.setMemberCard(member.getMemberCard());
		memberInfo.setMemberName(member.getMemberName());
		//密码修改
		if(StringUtil.isNotEmpty(member.getPassWord())){
			memberInfo.setPassWord(EncrypMD5.getMD5Code(member.getPassWord()));
		}
	}
	/**
	 * 会员信息完善
	 * 身份证信息必填，用于终端校验
	 */
	@Override
	public void updateMemberInfoByMemberId(MemberInfo member) {
		MemberInfo memberInfo = memberDaoImpl.getObjectById(MemberInfo.class, member.getMemberId());
		//身份证null的校验
		if(!StringUtil.isNotEmpty(member.getMemberCard())){
			throw new RuntimeException("身份证号不能为空");
		}
		memberInfo.setMemberCard(member.getMemberCard());
		memberInfo.setMemberName(member.getMemberName());
		memberInfo.setMemberPhone(member.getMemberPhone());
		memberInfo.setRemark(member.getRemark());
	}

	@Override
	public void modifyMemberPwd(String cellPhone, String newPwd) {
		MemberInfo memberInfo = memberDaoImpl.getMemberyInfoByMemberPhone(cellPhone);
		memberInfo.setPassWord(newPwd);
	}

}
