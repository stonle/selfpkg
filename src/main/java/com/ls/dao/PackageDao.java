package com.ls.dao;

import java.util.List;

import com.ls.domain.PackageInfo;
import com.ls.util.Page;

public interface PackageDao extends BaseDAO<PackageInfo, Integer>{
	// 分页条件查询
	List<PackageInfo> listPackageByPage(Page page, String pkgNum,String deviceNum);

	// 获取分页条件查询记录总数，辅助方法listPackageByPage
	int getTotalCount(String pkgNum,String deviceNum);
	
	/**
	 * 根据包裹编号查询包裹信息
	 * @param pkuNum
	 * @return
	 */
	PackageInfo getPckInfoByPkgNum(String pkuNum);
	
	/**
	 * 根据终端编号获取最新的包裹信息
	 * @param devNum
	 * @return
	 */
	PackageInfo getPackageInfoByDevNum(String devNum);

}
