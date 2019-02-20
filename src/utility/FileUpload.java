package utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.json.JSONException;
import org.json.JSONObject;

public class FileUpload {
	public HttpSession session = null;
	String databaseName = "XM06";

	public void setSession(HttpSession session) throws Exception {
		try {
			this.session = session;
			if (session.getAttribute("unit_db_name") == null) {
				session.setAttribute("unit_db_name", databaseName);
				System.out.println("unit_db_name是空的，进行了缺省");
			} else {
			}
		} catch (Exception e) {
			System.out.println("初始化Bean出现错误！" + e.getMessage());
		}
	}

	private static final long serialVersionUID = 1L;
	File tmpDir = null; // 初始化上传文件的临时存放目录
	File fileDir = null; // 初始化上传文件后的保存目录
	String rootPath = null; // 初始化上传文件的根目录
	String filePath = null; // 初始化上传文件的保存目录
	String urlPath = null; // 初始化上传文件的访问URL

	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public void showDebug(String msg) {
		System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + "][utility/FileUpload]" + msg);
	}
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}


	public JSONObject upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
		initUploadDir(request, response); // 初始化目录
		String fileName = "";
		JSONObject jsonObj = new JSONObject();
		List jsonList = new ArrayList();
		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				DiskFileItemFactory dff = new DiskFileItemFactory();// 创建该对象
				dff.setRepository(tmpDir);// 指定上传文件的临时目录
				dff.setSizeThreshold(1024000);// 指定在内存中缓存数据大小,单位为byte
				ServletFileUpload sfu = new ServletFileUpload(dff);// 创建该对象
				sfu.setHeaderEncoding("UTF-8");
				sfu.setSizeMax(20000000);// 指定单个上传文件的最大尺寸
				sfu.setSizeMax(100000000);// 指定一次上传多个文件的总尺寸
				FileItemIterator fii = sfu.getItemIterator(request);// 解析request
				// 请求,并返回FileItemIterator集合
				while (fii.hasNext()) {
					FileItemStream fis = fii.next();// 从集合中获得一个文件流
					int fileSize = 0;
					if (!fis.isFormField() && fis.getName().length() > 0) {// 过滤掉表单中非文件域
						if (fis.getName().lastIndexOf("//") > 0) {
							fileName = fis.getName().substring(fis.getName().lastIndexOf("//"));// 获得上传文件的文件名
						} else {
							fileName = fis.getName();// 获得上传文件的文件名
						}
						BufferedInputStream in = new BufferedInputStream(fis.openStream());// 获得文件输入流
						BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(fileDir + "\\" + fileName)));// 获得文件输出流
						fileSize = (int) Streams.copy(in, out, true);// 开始把文件写到你指定的上传文件夹
					}
					// 构造返回结果的json
					HashMap map = new HashMap();
					map.put("file_name", fileName);
					map.put("file_path", filePath);
					map.put("file_size", fileSize);
					map.put("url_path", urlPath);
					//showDebug("fileName="+fileName+",filePath="+filePath+",fileSize="+fileSize+",urlPath="+urlPath);
					jsonList.add(map);
				}
				// 返回json的结果
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonObj.put("files", jsonList);
		jsonObj.put("version", "1.0");
		String json = jsonObj.toString();
		return jsonObj;
	}

	/*
	 * 对上传文件夹和临时文件夹进行初始化 找到根目录，创建该目录下的临时路径和用户路径 用户上传的文件放在对应的用户路径下 前面应该定义几个公共变量
	 * File tmpDir = null; // 初始化上传文件的临时存放目录 File fileDir = null; //
	 * 初始化上传文件后的保存目录 String rootPath=null; // 初始化上传文件的根目录
	 */
	public void initUploadDir(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String userId = null;
		if (session.getAttribute("user_id") == null) {
			userId = "public";
		} else {
			userId = (String) session.getAttribute("user_id");
		}
		if (rootPath == null || rootPath.isEmpty())
			rootPath = "C:\\";
		String tmpPath = rootPath + "temp\\"; // 临时路径
		if ((filePath == null) || filePath.isEmpty()) {
			filePath = rootPath + "upload\\" + userId + "\\"; // 用户路径
			urlPath = "/upload/" + userId + "/"; // 用户路径
		}
		tmpDir = new File(tmpPath);
		fileDir = new File(filePath);
		if (!tmpDir.exists() && !tmpDir.isDirectory())
			tmpDir.mkdirs();
		if (!fileDir.exists() && !fileDir.isDirectory())
			fileDir.mkdirs();
	}

	/**
	 * 支持中文,文件名长度无限制 不支持国际化
	 */
	public void download(HttpServletRequest request, HttpServletResponse response,String filePath,String fileName) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		String filePathName=filePath+fileName;
		try {
			long fileLength = new File(filePathName).length();

			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));

			bis = new BufferedInputStream(new FileInputStream(filePathName));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
}
