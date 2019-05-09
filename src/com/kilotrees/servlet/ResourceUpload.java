package com.kilotrees.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kilotrees.core.Config;

@WebServlet("/ResourceUpload")
public class ResourceUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long fileLength = 0;
		String fileName = null;
		
		try {
			
			fileName = req.getHeader("fileName");
			if (fileName == null) {
				fileName = req.getParameter("fileName");
			}
			if (fileName != null) {
				
				fileName = Config.getWebServerStoragePath() + fileName;
				File file = new File(fileName);
				fileLength = file.length();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			String result = fileLength + "";
			resp.getWriter().write(result);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		
		String fileName = null;
		
		try {

			fileName = req.getHeader("fileName");
			if (fileName == null) {
				fileName = req.getParameter("fileName");
			}
			if (fileName != null) {
				
				InputStream inputStream = req.getInputStream();

				String append = req.getHeader("append");
				boolean isAppend = append != null && append.equals("true");

				fileName = Config.getWebServerStoragePath() + fileName;
				File file = new File(fileName);
				if (file.getParentFile() != null && !file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				if (!file.exists()) {
					file.createNewFile();
					
					FileOutputStream out = new FileOutputStream(file);
					
					// read & write -------------------------
					byte[] buffer = new byte[5 * 1024 * 1024];
					int length = -1;
					while ((length = inputStream.read(buffer)) != -1) {
						out.write(buffer, 0, length);
					}
					out.close();
					// read & write -------------------------
					
				} else {
					String fileNameTemp = fileName + ".tmp";
					File fileTemp = new File(fileNameTemp);
					fileTemp.delete();
					
					FileOutputStream out = new FileOutputStream(fileTemp);

					// read & write -------------------------
					byte[] buffer = new byte[1024 * 1024];
					int length = -1;
					while ((length = inputStream.read(buffer)) != -1) {
						out.write(buffer, 0, length);
					}
					out.close();
					// read & write -------------------------
					
					if (isAppend) {
						appendFileToFile(fileTemp, file);
						fileTemp.delete();
					} else {
						file.delete();
						fileTemp.renameTo(file);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			boolean isSuccess = new File(fileName).exists();
			resp.getWriter().println("{\"result\":\"" + isSuccess + "\"}");
			
		}

	}
	
	
	/*
	 * Private Methods
	 */
	
	private static void appendFileToFile(File source, File destination) {
		try {
			InputStream input = null;
			OutputStream output = null;
			try {
				input = new FileInputStream(source);
				output = new FileOutputStream(destination, true);
				byte[] buf = new byte[1024];
				int bytesRead;
				while ((bytesRead = input.read(buf)) != -1) {
					output.write(buf, 0, bytesRead);
				}
			} finally {
				if (input != null) {
					input.close();
				}
				if (output != null) {
					output.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
