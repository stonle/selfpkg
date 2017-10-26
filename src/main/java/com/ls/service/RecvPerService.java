/**   
* 
*/
package com.ls.service;

import java.util.List;

import com.ls.domain.RecvPerInfo;
import com.ls.util.Page;

/** 
* @ClassName: RecvPerService 
* @Description: 
* @author huanglei
* @date 2017年9月1日 下午5:12:23 
* @version V1.0    
*/
public interface RecvPerService extends BaseService<RecvPerInfo, String>{
	
    int getRecvPreTotalCount(RecvPerInfo personel);
    
    List<RecvPerInfo> listRecvPerByPage(Page page, RecvPerInfo personel);
    
    
    void addRecvPerInfo(RecvPerInfo recvPerInfo);
    
    int getTotalCount(String memberId);
    
    List<RecvPerInfo> listRecvPerInfo(String memberId,Page page);
    
    void updateRecvPerInfo(RecvPerInfo recvPerInfo);
    
    void deleteRecvPerInfo(String recvId);
} 
