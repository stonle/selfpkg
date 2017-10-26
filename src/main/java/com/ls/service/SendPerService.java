/**   
* 
*/
package com.ls.service;

import java.util.List;

import com.ls.domain.SendPerInfo;
import com.ls.util.Page;

/** 
* @ClassName: SendPerService 
* @Description: 
* @author huanglei
* @date 2017年9月1日 上午9:25:19 
* @version V1.0    
*/
public interface SendPerService {
	/**
	 * 添加寄件人信息接口
	 * @param sendPerInfo
	 */
	void addSendInfo(SendPerInfo sendPerInfo);
	
	int getTotalCount(String memberId);
	/**
	 * 查询寄件人信息接口
	 * @param remberId
	 * @return
	 */
	List<SendPerInfo> querySendPerInfo(String remberId,Page page);
	
	/**
	 * 修改寄件人信息表
	 * @param sendPerInfo
	 */
	void updateSendPerInfo(SendPerInfo sendPerInfo);
	
	/**
	 * 删除寄件人
	 * @param sendId
	 */
	void deleteSendPerAndRecvInfo(String sendId);
	/**
	 * 根据条件查询总数量
	 * @param sendPerInfo
	 * @return
	 */
	int getSendPreTotalCount(SendPerInfo sendPerInfo);
	
	/**
	 * 分页查询收件人信息
	 * @param personel
	 * @param page
	 * @return
	 */
	List<SendPerInfo> listSendPerByPage(SendPerInfo personel, Page page);
}
