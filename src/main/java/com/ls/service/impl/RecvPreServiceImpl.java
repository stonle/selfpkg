/**   
* 
*/
package com.ls.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.cenum.DateEnum;
import com.ls.dao.RecvPerDao;
import com.ls.domain.RecvPerInfo;
import com.ls.service.RecvPerService;
import com.ls.util.GuidCreatorUtil;
import com.ls.util.Page;
import com.ls.util.Utils;

/** 
* @ClassName: RecvPreServiceImpl 
* @Description: 
* @author huanglei
* @date 2017年9月1日 下午5:13:01 
* @version V1.0    
*/
@Service
public class RecvPreServiceImpl extends BaseServiceImpl<RecvPerInfo, String> implements RecvPerService{

    @Autowired
    private RecvPerDao recvPerDaoImpl;
	@Override
	public int getRecvPreTotalCount(RecvPerInfo personel) {
		
		return recvPerDaoImpl.getRecvPreTotalCount(personel);
	}

	@Override
	public List<RecvPerInfo> listRecvPerByPage(Page page, RecvPerInfo personel) {
		return recvPerDaoImpl.listRecvPerByPage(page,personel);
	}
     /**
      * 添加
      */
	@Override
	public void addRecvPerInfo(RecvPerInfo recvPerInfo) {
		//生成唯一主键
		recvPerInfo.setRecvId(new GuidCreatorUtil().toString());
		//设置状态
		recvPerInfo.setStatus(DateEnum.NO_DELETE.getIndex());
		//设置系统时间
		recvPerInfo.setCreateDate(Utils.getCurrentTime());
		recvPerDaoImpl.saveObject(recvPerInfo);
	}

	@Override
	public List<RecvPerInfo> listRecvPerInfo(String memberId,Page page) {
		return recvPerDaoImpl.listRecvPerInfo(memberId,page);
	}

	@Override
	public void updateRecvPerInfo(RecvPerInfo recvPerInfo) {
		RecvPerInfo rPerInfo = recvPerDaoImpl.getObjectById(RecvPerInfo.class, recvPerInfo.getRecvId());
		recvPerInfo.setStatus(DateEnum.NO_DELETE.getIndex());
		recvPerInfo.setCreateDate(rPerInfo.getCreateDate());
		recvPerDaoImpl.megreObject(recvPerInfo);
	}

	@Override
	public void deleteRecvPerInfo(String recvId) {
		RecvPerInfo rPerInfo = recvPerDaoImpl.getObjectById(RecvPerInfo.class, recvId);
		rPerInfo.setStatus(DateEnum.DELETE.getIndex());
	}

	@Override
	public int getTotalCount(String memberId) {
		return recvPerDaoImpl.getTotalCount(memberId);
	}

}
