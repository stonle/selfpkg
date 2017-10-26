/**   
* 
*/
package com.ls.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @ClassName: FileController
 * @Description:
 * @author huanglei
 * @date 2017年9月15日 上午8:22:15
 * @version V1.0
 */
@RestController
@RequestMapping("pages")
public class FileControllerTest {

	private static Logger logger = LogManager.getLogger(FileControllerTest.class.getName());

	@RequestMapping(value = "/upload.do")
	public String upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,
			ModelMap model) {

		System.out.println("开始");
		String path = request.getSession().getServletContext().getRealPath("upload");
		String fileName = file.getOriginalFilename();
		System.out.println(path);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		model.addAttribute("fileUrl", request.getContextPath() + "/upload/" + fileName);

		return "result";
	}

	@RequestMapping("/upload2")
	public String upload2(HttpServletRequest request, HttpServletResponse response)
			throws IllegalStateException, IOException {
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				int pre = (int) System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						System.out.println(myFileName);
						// 重命名上传后的文件名
						String fileName = "demoUpload" + file.getOriginalFilename();
						// 定义上传路径
						String path = "H:/" + fileName;
						File localFile = new File(path);
						file.transferTo(localFile);
					}
				}
				// 记录上传该文件后的时间
				int finaltime = (int) System.currentTimeMillis();
				System.out.println(finaltime - pre);
			}

		}
		return "/success";
	}

	//
	@RequestMapping(value = "/downPhotoById")
	public static void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileName = "site.xml";
		String filePath = "E:\\svn\\site.xml";
		// 声明本次下载状态的记录对象
		DownloadRecordTest downloadRecord = new DownloadRecordTest(fileName, filePath, request);
		// 设置响应头和客户端保存文件名
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		// 用于记录以完成的下载的数据量，单位是byte
		long downloadedLength = 0l;
		try {
			// 打开本地文件流
			InputStream inputStream = new FileInputStream(filePath);
			// 激活下载操作
			OutputStream os = response.getOutputStream();

			// 循环写入输出流
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
				downloadedLength += b.length;
			}

			// 这里主要关闭。
			os.close();
			inputStream.close();
		} catch (Exception e) {
			downloadRecord.setStatus(DownloadRecordTest.STATUS_ERROR);
			throw e;
		}
		downloadRecord.setStatus(DownloadRecordTest.STATUS_SUCCESS);
		downloadRecord.setEndTime(new Timestamp(System.currentTimeMillis()));
		downloadRecord.setLength(downloadedLength);
		// 存储记录
	}

	// 下载文件
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/downOdex.do")
	public ResponseEntity<String> downFile(HttpServletResponse response,
			HttpServletRequest request) {
		InputStream inputStream = null;
		ServletOutputStream out = null;
		try {
			File file = new File("E:\\upload\\apache-tomcat-7.0.70.rar");
			int fSize = Integer.parseInt(String.valueOf(file.length()));
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/x-download");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-Length", String.valueOf(fSize));
			response.setHeader("Content-Disposition", "attachment;fileName=" + file.getName());
			inputStream = new FileInputStream(file);
			long pos = 0;
			if (null != request.getHeader("Range")) {
				// 断点续传
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
				try {
					pos = Long.parseLong(request.getHeader("Range").replaceAll("bytes=", "").replaceAll("-", ""));
				} catch (NumberFormatException e) {
					pos = 0;
				}
			}
			out = response.getOutputStream();
			String contentRange = new StringBuffer("bytes ").append(pos + "").append("-").append((fSize - 1) + "")
					.append("/").append(fSize + "").toString();
			response.setHeader("Content-Range", contentRange);
			inputStream.skip(pos);
			byte[] buffer = new byte[1024 * 10];
			int length = 0;
			while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, length);
				Thread.sleep(100);
			}
		} catch (Exception e) {
			logger.error("ODEX软件下载异常：" + e);
		} finally {
			try {
				if (null != out)
					out.flush();
				if (null != out)
					out.close();
				if (null != inputStream)
					inputStream.close();
			} catch (IOException e) {
			}
		}
		return new ResponseEntity(null, HttpStatus.OK);
	}

	// 获取已上传文件大小
	@RequestMapping("/getChunkedFileSize.do")
	public Map<String, Object> getChunkedFileSize(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			String currentFilePath = "E:\\upload\\";
			String fileName = new String(request.getParameter("fileName").getBytes("ISO-8859-1"), "UTF-8");
			String lastModifyTime = request.getParameter("lastModifyTime");
			File file = new File(currentFilePath + fileName + "." + lastModifyTime);
			if (file.exists()) {
				map.put("lenght", file.length());
			} else {
				map.put("lenght", -1);
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
	 * 
	 * 断点文件上传 1.先判断断点文件是否存在 2.存在直接流上传 3.不存在直接新创建一个文件 4.上传完成以后设置文件名称
	 *
	 */
	@RequestMapping("/appendUpload2Server.do")
	public void appendUpload2Server(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter print = null;
		try {
			request.setCharacterEncoding("utf-8");
			print = response.getWriter();
			String fileSize = request.getParameter("fileSize");
			long totalSize = Long.parseLong(fileSize);
			RandomAccessFile randomAccessfile = null;
			long currentFileLength = 0;// 记录当前文件大小，用于判断文件是否上传完成
			String currentFilePath = "E:\\upload\\";// 记录当前文件的绝对路径
			String fileName = new String(request.getParameter("fileName").getBytes("ISO-8859-1"), "UTF-8");
			String lastModifyTime = request.getParameter("lastModifyTime");
			File file = new File(currentFilePath + fileName + "." + lastModifyTime);
			// 存在文件
			if (file.exists()) {
				randomAccessfile = new RandomAccessFile(file, "rw");
			} else {
				// 不存在文件，根据文件标识创建文件
				randomAccessfile = new RandomAccessFile(currentFilePath + fileName + "." + lastModifyTime, "rw");
			}
			// 开始文件传输
			InputStream in = request.getInputStream();
			randomAccessfile.seek(randomAccessfile.length());
			byte b[] = new byte[1024];
			int n;
			while ((n = in.read(b)) != -1) {
				randomAccessfile.write(b, 0, n);
			}

			currentFileLength = randomAccessfile.length();

			// 关闭文件
			closeRandomAccessFile(randomAccessfile);
			randomAccessfile = null;
			// 整个文件上传完成,修改文件后缀
			if (currentFileLength == totalSize) {
				File oldFile = new File(currentFilePath + fileName + "." + lastModifyTime);
				File newFile = new File(currentFilePath + fileName);
				if (!oldFile.exists()) {
					return;// 重命名文件不存在
				}
				if (newFile.exists()) {// 如果存在形如test.txt的文件，则新的文件存储为test+当前时间戳.txt,
										// 没处理不带扩展名的文件
					String newName = fileName.substring(0, fileName.lastIndexOf(".")) + System.currentTimeMillis() + "."
							+ fileName.substring(fileName.lastIndexOf(".") + 1);
					newFile = new File(currentFilePath + newName);
				}
				if (!oldFile.renameTo(newFile)) {
					oldFile.delete();
				}

			}
			print.print(currentFileLength);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭随机访问文件
	 * 
	 * @param randomAccessfile
	 */
	public static void closeRandomAccessFile(RandomAccessFile rfile) {
		if (null != rfile) {
			try {
				rfile.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
