package com.kilotrees.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.kilotrees.core.Config;
import com.kilotrees.util.StringUtil;

/**
 * Servlet implementation class zipupload
 */
@WebServlet("/zipupload")
public class zipupload extends HttpServlet {
	public static Logger log = Logger.getLogger(zipupload.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public zipupload() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		long allfileLen = 0;
		int bytes_start = 0;
		String content_len = request.getHeader("Content-Length");
		if (content_len != null && content_len.length() > 0)
			allfileLen = Long.parseLong(content_len);
		String strBytes = request.getHeader("bytes");
		if (strBytes != null && strBytes.length() > 0)
			bytes_start = Integer.parseInt(strBytes);
		log.info("fileLen=" + allfileLen + ";bytes_start=" + bytes_start);

		long fileSize = 0;
		String delfirst = request.getParameter("delfirst");
		String autoid = request.getParameter("autoid");
		String packagename = request.getParameter("packageName");
		String strJson = request.getParameter("file_server_parameters");
		if(!StringUtil.isStringEmpty(strJson)) {
			try {
				JSONObject json = new JSONObject(strJson);
				String custom_zipDirectory = json.optString("");  //从ad_task_logic中添加的QQ任务额外的file_server地址
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		//2019-1-24
		String fileName = "data_" + autoid + ".zip.temp";
		
		String zipDirectory = Config.getZipFilePath();
		String fileFullPath = zipDirectory + "/" + packagename + "/" + fileName;
		
		File file = new File(fileFullPath);
		FileOutputStream fos = null;
		if (delfirst != null) {
			if (delfirst.equals("true")) {
				log.info("delfirst = true,delete " + fileFullPath);
				file.delete();
			} else if (file.exists())
				fileSize = file.length();
		} else {
			if (bytes_start == 0) {
				log.info("bytes_start = 0,delete " + fileFullPath);
				file.delete();
			}
			if (file.exists())
				fileSize = file.length();
			if (bytes_start != fileSize) {
				response.setStatus(904);
			} else {
				int readLen = 0;
				if(file.getParentFile() != null && !file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				fos = new FileOutputStream(file, true); 
				byte[] buf = new byte[1024 * 1024];
				InputStream ins = request.getInputStream();
				while (fileSize < allfileLen && readLen != -1) {
					readLen = ins.read(buf);
					if (readLen > 0) {
						fos.write(buf, 0, readLen);
						fileSize += readLen;
					}
				}
				if (fos != null) {
					fos.close();
				}
				//2019-1-24
				if(fileSize >= allfileLen) {
					String name = fileFullPath.replace(".temp", "");
					file.renameTo(new File(name));
				}
			}
		}
		response.addHeader("bytes", fileSize+"");
		response.getWriter().print("ok");
	}

	public static void copyfile(String source, String des) throws IOException {
		source = source.replace('/', File.separatorChar);
		des = des.replace('/', File.separatorChar);
		String cmd = "cmd /c copy " + source + " " + des;
		System.out.println(cmd);
		Runtime.getRuntime().exec(cmd);
	}

	public static void main(String[] argv) throws IOException {
		String s = "D:/Tools/android/ADT-23.0.6.zip";
		String d = "D:/yunfilesctrl_poslog/ADT-23.0.6.zip";
		copyfile(s, d);
	}
}
