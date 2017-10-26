package com.ls.service;

import java.util.List;

import com.ls.domain.PackageInfo;
import com.ls.util.Page;

public interface PackageService extends BaseService<PackageInfo, Integer>{
	// 分页条件查询
	List<PackageInfo> listPackageByPage(Page page, String pvo,String deviceNum);

	// 获取分页条件查询记录总数，辅助方法listPackageByPage
	int getTotalCount(String pvo,String deviceNum);
	
	
	PackageInfo getPackageInfoByPkgNum(String pkgNum);
}
