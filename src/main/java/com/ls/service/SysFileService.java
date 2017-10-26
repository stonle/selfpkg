/**   
* 
*/
package com.ls.service;

import java.util.List;

import com.ls.domain.SysFileInfo;
import com.ls.util.Page;

/** 
* @ClassName: SysFileService 
* @Description: 
* @author huanglei
* @date 2017年9月19日 上午8:57:30 
* @version V1.0    
*/
public interface SysFileService extends BaseService<SysFileInfo,Integer>{
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
	 * 版本号的唯一性校验
	 * @param sysVersion
	 * @return
	 */
	boolean checkSysyFileInfoVersion(String sysVersion);
	/**
	 * 判断是否已有该升级包
	 * @param md5
	 * @return
	 */
    SysFileInfo getSysFileInfoByMD5(String md5);
    
    /**
     * 保存上传升级包信息
     * @param sInfo
     */
    void saveSytFileInfo(SysFileInfo sInfo);
    
    
    /**
     * 删除，更改状态并且获取SysFileInfo对象
     * @param id
     * @return
     */
    boolean updateSysFileInfoById(String id);
    }
