/**
 * @author Administrator
 * 2019年1月22日 下午4:13:24 
 */
package com.kilotrees.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kilotrees.core.Config;

@WebServlet("/ResourceDownload")
public class ResourceDownload extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			
			String fileName = req.getHeader("fileName");
			if (fileName == null) {
				fileName = req.getParameter("fileName");
			}
			if (fileName != null) {

				fileName = Config.getWebServerStoragePath() + fileName;
				File file = new File(fileName);
				
				resp.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

				FileInputStream inputStream = new FileInputStream(file);
				byte[] buffer = new byte[inputStream.available()];
				inputStream.read(buffer);
				inputStream.close();

				resp.setContentLength(buffer.length);
				resp.getOutputStream().write(buffer);
				resp.getOutputStream().flush();
				resp.getOutputStream().close();

				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.setContentLength(0);
		resp.getOutputStream().write(new byte[]{});
		resp.getOutputStream().flush();
		resp.getOutputStream().close();

	}

}
