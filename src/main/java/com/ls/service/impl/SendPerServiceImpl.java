/**   
* 
*/
package com.ls.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.cenum.DateEnum;
import com.ls.dao.SendPerDao;
import com.ls.domain.SendPerInfo;
import com.ls.service.SendPerService;
import com.ls.util.DateTimeUtil;
import com.ls.util.GuidCreatorUtil;
import com.ls.util.Page;

/** 
* @ClassName: SendPerServiceImpl 
* @Description: 
* @author huanglei
* @date 2017年9月1日 上午9:25:46 
* @version V1.0    
*/
@Service
public class SendPerServiceImpl extends BaseServiceImpl<SendPerInfo, String> implements SendPerService{
    
	@Autowired
	private SendPerDao sendPerDaoImpl;
	
	/**
	 * 接口暂定
	 */
	@Override
	public void addSendInfo(SendPerInfo sendPerInfo) {
		//生成唯一主键
		sendPerInfo.setSendId(new GuidCreatorUtil().toString());
		//设置状态
		sendPerInfo.setStatus(DateEnum.NO_DELETE.getIndex());
		String dateStr = DateTimeUtil.getFormatDate();
		//设置系统时间
		sendPerInfo.setCreateDate(dateStr);
		sendPerDaoImpl.saveObject(sendPerInfo);
	}

	@Override
	public List<SendPerInfo> querySendPerInfo(String remberId,Page page) {
		return sendPerDaoImpl.querySendPerAndRecvInfo(remberId,page);
	}
	@Override
	public void updateSendPerInfo(SendPerInfo sendPerInfo) {
		SendPerInfo sendPerInfo2 = sendPerDaoImpl.getObjectById(SendPerInfo.class, sendPerInfo.getSendId());
		sendPerInfo.setCreateDate(sendPerInfo2.getCreateDate());
		sendPerInfo.setStatus(DateEnum.NO_DELETE.getIndex());
		sendPerDaoImpl.megreObject(sendPerInfo);
	}

	@Override
	public int getSendPreTotalCount(SendPerInfo sendPerInfo) {
		return sendPerDaoImpl.getSendPreTotalCount(sendPerInfo);
	}

	@Override
	public List<SendPerInfo> listSendPerByPage(SendPerInfo personel, Page page) {
		return sendPerDaoImpl.listSendPerByPage(personel,page);
	}
	/**
	 * 删除：把status设置为0
	 */
	@Override
	public void deleteSendPerAndRecvInfo(String sendId) {
       
	    SendPerInfo sendPerInfo = sendPerDaoImpl.getObjectById(SendPerInfo.class, sendId);
	    sendPerInfo.setStatus(DateEnum.DELETE.getIndex());
	}
	@Override
	public int getTotalCount(String memberId) {
		return sendPerDaoImpl.getTotalCount(memberId);
	}

}
