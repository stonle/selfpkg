/**   
* 
*/
package com.ls.dao;

import java.util.List;

import com.ls.domain.RecvPerInfo;
import com.ls.util.Page;

/** 
* @ClassName: RecvPerDao 
* @Description: 
* @author huanglei
* @date 2017年9月1日 下午5:18:50 
* @version V1.0    
*/
public interface RecvPerDao extends BaseDAO<RecvPerInfo, String>{
	
   int getRecvPreTotalCount(RecvPerInfo recvInfo);
   
   List<RecvPerInfo> listRecvPerByPage(Page page, RecvPerInfo personel);
   
   List<RecvPerInfo> listRecvPerInfo(String memberId,Page page);
   
   int getTotalCount(String memberId);
}
