package com.ls.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.dao.PackageDao;
import com.ls.domain.PackageInfo;
import com.ls.service.PackageService;
import com.ls.util.Page;

@Service
public class PackageServiceImpl extends BaseServiceImpl<PackageInfo, Integer> implements PackageService  {
    
	@Autowired
	private PackageDao packageDaoImpl;
	@Override
	public List<PackageInfo> listPackageByPage(Page page, String pvo,String deviceNum) {
		return packageDaoImpl.listPackageByPage(page, pvo,deviceNum);
	}

	@Override
	public int getTotalCount(String pvo,String deviceNum) {
		return packageDaoImpl.getTotalCount(pvo,deviceNum);
	}

	@Override
	public PackageInfo getPackageInfoByPkgNum(String pkgNum) {
		return packageDaoImpl.getPckInfoByPkgNum(pkgNum);
	}
}
