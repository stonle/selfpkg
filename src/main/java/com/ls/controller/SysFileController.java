/**   
* 
*/
package com.ls.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ls.domain.SysFileInfo;
import com.ls.domain.User;
import com.ls.service.SysFileService;
import com.ls.util.GetFileMD5;
import com.ls.util.MyFileUtil;
import com.ls.util.Page;
import com.ls.util.PageUtil;
import com.ls.util.PropUtil;
import com.ls.util.StringUtil;
import com.ls.util.UserContextUtil;
import com.ls.util.Utils;


/** 
* @ClassName: SysFileController 
* @Description: 
* @author huanglei
* @date 2017年9月18日 下午3:47:27 
* @version V1.0    
*/
@RestController
@RequestMapping("pages")
public class SysFileController {
	
	private static Logger logger = LogManager.getLogger(SysFileController.class.getName());
	
	@Autowired
	private SysFileService sysFileServiceImpl;
	/**
	 * 获取上传压缩包信息列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/queryFileResource.do",method=RequestMethod.GET)
	public Map<String,Object> queryFileResource(Page page){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			page = PageUtil.createPage(page.getEveryPage(), sysFileServiceImpl.getTotallCount(),
					page.getCurrentPage());
			List<SysFileInfo> list = sysFileServiceImpl.listSysFileByPages(page);
			map.put("msg", "1");
			map.put("list", list);
			map.put("totalCount",page.getTotalCount());
			map.put("currentPage",page.getCurrentPage());
			map.put("totalPage",page.getTotalPage());
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}
	@RequestMapping(value="/uploadFileResource.do",method=RequestMethod.POST)
	public Map<String,Object> uploadFileResource(@RequestParam("file")MultipartFile file,@RequestParam("versionNo")String versionNo,@RequestParam("remark")String remark){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file == null ||!StringUtil.isNotEmpty(versionNo)){
				map.put("error", "File error...");
				return map;
			}
			CommonsMultipartFile cf = (CommonsMultipartFile) file;
			DiskFileItem fi = (DiskFileItem) cf.getFileItem();
			File f = fi.getStoreLocation();
            //获取文件的MD5值
			String md5 = GetFileMD5.getMD5(f);
			//首先判断数据库是否存在相同MD5值的文件，MD5值不能相同；
			SysFileInfo sysFileInfo = sysFileServiceImpl.getSysFileInfoByMD5(md5);
			String fileName = file.getOriginalFilename();
			String ext = "";
			String name = "";
			if(fileName.endsWith("tar.gz")){
				ext ="tar.gz";
				name = fileName.replace("tar.gz", "");
			}else{
				
				 ext = fileName.substring(fileName.lastIndexOf(".") + 1);// 文件扩展名
				 name = fileName.substring(0, fileName.lastIndexOf("."));	
			}
			if(sysFileInfo!=null){
				map.put("error", "上传的该文件已存在!");
				return map;
			}else{
				//创建文件
				MyFileUtil.saveFile(file, PropUtil.getKeyValue(PropUtil.TER_UPLOAD_URL), name+versionNo+"."+ext);
			}
			User user = UserContextUtil.getUser();
			//
			SysFileInfo sInfo = new SysFileInfo();
			sInfo.setCreateUser(user.getUserName());
			sInfo.setCreateTime(Utils.getCurrentTime());
			sInfo.setFileUrl(PropUtil.getKeyValue(PropUtil.TER_DOWNLOAD_URL)+name+versionNo+"."+ext);
			sInfo.setFileMD5(md5);
			sInfo.setStatus(1);
			sInfo.setType(0);
			sInfo.setFileName(fileName);
			sInfo.setFileType(file.getContentType());
			sInfo.setFileVersion(versionNo);
			sInfo.setFileSize(file.getSize());
			sInfo.setRemark(remark);
			sysFileServiceImpl.saveSytFileInfo(sInfo);
			map.put("msg", "1");
			map.put("info", "sucess");
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}
	/**
	 * 检查版本号是否重名
	 * @return
	 */
	@RequestMapping(value="/checkSysFileInfoVersion.do",method=RequestMethod.GET)
	public Map<String,Object> checkSysFileInfoVersion(@RequestParam("versionNo")String versionNo){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			boolean flag = sysFileServiceImpl.checkSysyFileInfoVersion(versionNo);
			if(flag){
				map.put("msg", "0");
				map.put("info", "该版本名称已被使用");
			}else{
				map.put("msg", "1");
				map.put("info", "sucess");
			}
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}
	/**
	 * 删除上传的升级包
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delFileResource.do",method=RequestMethod.DELETE)
	public Map<String,Object> delFileResource( @RequestParam("items")String items){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//
			boolean boo = sysFileServiceImpl.updateSysFileInfoById(items);
			map.put("msg", "1");
			map.put("info", boo);
		} catch (Exception e) {
			map.put("msg", "-100");
			map.put("info", e.getMessage());
			logger.error(e.getMessage());
			return map;
		}
		return map;
	}
}
