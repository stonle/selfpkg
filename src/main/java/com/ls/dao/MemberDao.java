package com.ls.dao;

import java.util.List;

import com.ls.domain.MemberInfo;
import com.ls.util.Page;

public interface MemberDao extends BaseDAO<MemberInfo, String> {

	/**
	 * 分页查询会员信息
	 * @param page
	 * @param personel
	 * @return
	 */
	List<MemberInfo> listPersonelByPage(Page page, MemberInfo personel);

	/**
	 * 获取会员信息总条数
	 * @param personel
	 * @return
	 */
	int getTotalCount(MemberInfo personel);
	
	/**
	 * 根据手机号查询会员
	 * @param cellPhone
	 * @return
	 */
	MemberInfo getMemberyInfoByMemberPhone(String memberPhone);
}
