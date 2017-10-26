package com.ls.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class MyFileUtil {
	private final static String encoding = "UTF-8";
	// log4j日志记录
	private static Logger logger = LogManager.getLogger(MyFileUtil.class
			.getName());

	/**
	 * 生成文件
	 * 
	 * @param filePath
	 *            ：路径 /
	 * @param fileName
	 *            : 名称
	 * @param fileContent
	 *            : 内容
	 * @return void
	 * */
	public static boolean writeIntoFile(String filePath, String fileName,
			String fileContent) {
		try {
			if (filePath == null || fileName == null || fileContent == null) {
				return false;
			}
			if (filePath.equals("") || fileName.equals("")) {
				return false;
			}
			filePath = filePath.replaceAll("\\\\", "/");
			if (filePath.lastIndexOf("/") == -1) {
				return false;
			}
			if (filePath.length() - filePath.lastIndexOf("/") != 1) {
				filePath += "/";
			}
			File file = new File(filePath + fileName);
			FileUtils.writeStringToFile(file, fileContent, encoding);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();

		}
		return true;
	}

	/**
	 * 保存文件,不存在的文件夹则自动创建
	 * 
	 * @param fis
	 * @param path
	 * @param filename
	 * */
	public static void saveFile(InputStream is, String path, String filename) {
		try {
			if (is == null) {
				return;
			}
			File file = new File(path);
			if (!file.exists()) {// 创建不存在的文件夹
				file.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(path + "/" + filename);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			is.close();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 保存文件,不存在的文件夹则自动创建
	 * 
	 * @param fis
	 * @param path
	 * @param filename
	 * */
	public static void saveFile(MultipartFile file, String path, String filename) {
		try {
			// 文件保存路径
			String filePath = path + "/" + filename;
			// 转存文件 ,此方法不会自动创建文件夹
			// file.transferTo(new File(filePath));
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 读取文件信息
	 *
	 * @param filePath
	 *            ：路径 /
	 * @param fileName
	 *            : 名称
	 * */

	public static String readFile(String filePath, String fileName) {
		String fileStr = "";
		try {
			File file = new File(filePath + fileName);
			fileStr = FileUtils.readFileToString(file,"UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return fileStr;
	}
	
	// 只解决项目中问题，一些东西不用考虑
	public static String replaceStr(String lable,String str,String replace) {
		String wantMsg = "";
		try {
			String[] strArr = str.split("lable");
			if(strArr.length > 0){
				if(strArr.length == 1){
					wantMsg =  strArr[0] + replace;
				}else{
					wantMsg =  strArr[0] + replace + strArr[1];
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return wantMsg;
	}
	
	/**
	 * 删除指定路径下的文件
	 */
	public static boolean deleteFileByUrl(String url){
	         File file = new File(url);
	        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
	        if (file.exists() && file.isFile()) {
	            if (file.delete()) {
	                System.out.println("删除单个文件" + url + "成功！");
	                return true;
	            } else {
	                System.out.println("删除单个文件" + url + "失败！");
	                return false;
	            }
	        } else {
	            System.out.println("删除单个文件失败：" + url + "不存在！");
	            return false;
	        }
	}
}
