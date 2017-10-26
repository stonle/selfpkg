package com.ls.service;

import java.util.List;

import com.ls.domain.MemberInfo;
import com.ls.util.Page;

public interface MemberService extends BaseService<MemberInfo, String> {
	 
	/**
	 * 分页查询会员
	 * @param page
	 * @param pesonle
	 * @return
	 */
	 List<MemberInfo> listPersonelByPage(Page page, MemberInfo pesonle);

	/**
	 * 获取会员总条数 status状态为1
	 * @param pesonle
	 * @return
	 */
	int getTotalCount(MemberInfo pesonle);
	
	/**
	 * 添加会员信息
	 * @param pesonle
	 */
    void saveMemberInfo(MemberInfo pesonle);
	/**
	 * 用户登录
	 * @param member
	 * @return
	 */
	 String checkMemberLogin(MemberInfo member);
		
	/**
	 * 根据memberId获取用户信息
	 * @param memberId
	 * @return
	 */
	MemberInfo queryMemberInfoById(String memberId);
	
	
	/**
	 * 根据memberId修改用户信息
	 * @param member
	 */
	void updateMemberInfo(MemberInfo member);
	
	/**
	 * 完善会员信息
	 * @param member
	 */
	void updateMemberInfoByMemberId(MemberInfo member);
	
	/**
	 * 修改密码
	 * @param cellPhone
	 * @param newPwd
	 */
	void modifyMemberPwd(String cellPhone,String newPwd);
}
