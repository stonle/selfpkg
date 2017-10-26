package com.ls.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.cenum.PkgCheckTypeEnum;
import com.ls.dao.PkgCheckDao;
import com.ls.domain.PkgCheckInfo;
import com.ls.domain.User;
import com.ls.service.PkgCheckService;
import com.ls.service.UserService;

@Service
public class PkgCheckServiceImpl extends BaseServiceImpl<PkgCheckInfo,Integer> implements PkgCheckService{
    @Autowired
    private PkgCheckDao  pkgCheckDaoImpl;
    
    @Autowired
    private UserService userServiceImpl;
	@Override
	public List<PkgCheckInfo> getPkgCheckInfos() {
		return pkgCheckDaoImpl.getPkgCheckInfos();
	}

	@Override
	public void updatePkgCheckInfo(String userId,String message) {
		//根据userId获取userName
		User user = userServiceImpl.getObjectById(User.class, userId);
		String[] str = message.split("\\|");
		PkgCheckInfo pkgCheckInfo = pkgCheckDaoImpl.getPkgCheckInfoByPkgNumAndDevNum(str[0],str[1],true);
		if(pkgCheckInfo!=null){
			pkgCheckInfo.setType(PkgCheckTypeEnum.HANDLED.getIndex());
			pkgCheckInfo.setCheckUser(user.getUserName());
		}
	}
	@Override
	public void updatePkgCheckInfo(String devNum, String pkgNum, boolean flag) {
	  //根据devNum和pkgNum，查询数据，然后再设type
		PkgCheckInfo pkgCheckInfo = pkgCheckDaoImpl.getPkgCheckInfoByPkgNumAndDevNum(pkgNum,devNum,false);
		if(pkgCheckInfo!=null){
			if(flag){
				pkgCheckInfo.setType(PkgCheckTypeEnum.SUCCESS.getIndex());
			}else{
				pkgCheckInfo.setType(PkgCheckTypeEnum.FAIL.getIndex());
			}
		}
	}
    		
}
