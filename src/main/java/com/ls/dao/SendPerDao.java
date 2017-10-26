/**   
* 
*/
package com.ls.dao;

import java.util.List;

import com.ls.domain.SendPerInfo;
import com.ls.util.Page;

/** 
* @ClassName: SendPerDao 
* @Description: 
* @author huanglei
* @date 2017年9月1日 上午9:21:18 
* @version V1.0    
*/
public interface SendPerDao extends BaseDAO<SendPerInfo, String> {
	
	int getTotalCount(String memberId);
	
	List<SendPerInfo> querySendPerAndRecvInfo(String remberId,Page page);
	
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
