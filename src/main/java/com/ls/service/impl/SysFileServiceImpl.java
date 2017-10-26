/**   
* 
*/
package com.ls.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ls.cenum.DateEnum;
import com.ls.dao.SysFileDao;
import com.ls.domain.SysFileInfo;
import com.ls.service.SysFileService;
import com.ls.util.GetFileMD5;
import com.ls.util.MyFileUtil;
import com.ls.util.Page;
import com.ls.util.PropUtil;

/** 
* @ClassName: SysFileServiceImpl 
* @Description: 
* @author huanglei
* @date 2017年9月19日 上午8:57:51 
* @version V1.0    
*/
@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFileInfo, Integer> implements SysFileService{
    
	@Autowired
	private SysFileDao sysFileDaoImpl;
	/**
	 *根据MD5值查询数据库是否存在该文件
	 */
	@Override
	public SysFileInfo getSysFileInfoByMD5(String md5) {

		return sysFileDaoImpl.getSysFileInfoByMD5(md5);
	}

	@Override
	public boolean checkSysyFileInfoVersion(String sysVersion) {
		SysFileInfo sysFileInfo = sysFileDaoImpl.getsysFileInfoByVersion(sysVersion);
		if(sysFileInfo !=null){
			 return true;
		}
		return false;
	}
	@Override
	public int getTotallCount() {
		return sysFileDaoImpl.getTotallCount();
	}

	@Override
	public List<SysFileInfo> listSysFileByPages(Page page) {

		return sysFileDaoImpl.listSysFileByPages(page);
	}
	@Override
	public void saveSytFileInfo(SysFileInfo sInfo) {
		sysFileDaoImpl.saveObject(sInfo);
	}
    
	@Override
	public boolean updateSysFileInfoById(String id) {
		
		String[] strs = id.split(",");
		boolean flag = true;
		List<SysFileInfo> sysFileInfo = sysFileDaoImpl.querySysFileInfoByIds(strs);
		//需要删除的文件路径
	    List<String>  fileUrls = new ArrayList<String>();
		//删除指定路径的
		//获取指定目录下的所有文件
		File dir  = new File(PropUtil.getKeyValue(PropUtil.TER_UPLOAD_URL));
		File[] files = dir.listFiles();
		for(SysFileInfo str:sysFileInfo)
		{  
			str.setStatus(DateEnum.DELETE.getIndex());
		    for(File file:files){
		    	if(str.getFileMD5().equals(GetFileMD5.getMD5(file))){
		    		fileUrls.add(file.getPath());
		    	}
		    }	
		}
		for(String fileUrl:fileUrls){
			   flag = MyFileUtil.deleteFileByUrl(fileUrl);
				if(!flag){
					throw new RuntimeException(flag+"");
				}
		}
	   return flag;
	}
  
}
