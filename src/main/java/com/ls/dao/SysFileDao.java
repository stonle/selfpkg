/**   
* 
*/
package com.ls.dao;

import java.util.List;

import com.ls.domain.SysFileInfo;
import com.ls.util.Page;

/** 
* @ClassName: SysFileDao 
* @Description: 
* @author huanglei
* @date 2017年9月19日 上午9:28:55 
* @version V1.0    
*/
public interface SysFileDao extends BaseDAO<SysFileInfo, Integer> {
	/**
	 * 获取总条数
	 * @return
	 */
	int getTotallCount();
	/**
	 * 分页获取升级包信息
	 * @param page
	 * @return
	 */
	List<SysFileInfo> listSysFileByPages(Page page);
	/**
	/**
	 * 获取
	 * @param sysVersion
	 * @return
	 */
	SysFileInfo getsysFileInfoByVersion(String sysVersion);
	/**
	 * 判断是否已有该文件
	 * @param md5
	 * @return
	 */
    SysFileInfo getSysFileInfoByMD5(String md5);
    
    
    List<SysFileInfo> querySysFileInfoByIds(String[] strs);
    
    
    SysFileInfo getNweSysFileInfo();
}
