package com.ls.dao;

import java.util.List;

import com.ls.domain.PkgCheckInfo;
import com.ls.http.BaseTerminal;

public interface PkgCheckDao extends BaseDAO<PkgCheckInfo,Integer> {
	
    PkgCheckInfo getPkgCheckInfo(BaseTerminal baseTerminal);
    
    PkgCheckInfo getCheckInfoByPkgNum(String pkgNum);
    
    PkgCheckInfo getPkgCheckInfoByPkgNumAndDevNum(String pkgNum,String devNum,boolean flag);
    
    List<PkgCheckInfo> getPkgCheckInfos();
}
