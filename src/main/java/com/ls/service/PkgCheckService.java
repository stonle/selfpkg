package com.ls.service;

import java.util.List;

import com.ls.domain.PkgCheckInfo;

public interface PkgCheckService extends BaseService<PkgCheckInfo,Integer> {
	
   List<PkgCheckInfo> getPkgCheckInfos();
   
   void updatePkgCheckInfo(String userId,String message);
   
   void updatePkgCheckInfo(String devNum,String pkgNum,boolean flag);
}
