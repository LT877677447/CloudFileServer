package com.kilotrees.servlet;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kilotrees.core.Config;
import com.kilotrees.core.main_service;

/**
 * ���մ����������������autoid��ɾ����Ӧ�ı����ļ�
 */
@WebServlet("/delautoid")
public class delautoid extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public delautoid() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String sautoids = request.getParameter("autoids");
		main_service.log.info("del sautoids=" + sautoids);
		if (sautoids == null) {
			response.getWriter().print("autoids = null");
			return;
		}
		String[] autoids = sautoids.split(";");
		String path = Config.getZipFilePath();//D:\\WebServer\\files\\zips\\

		File fpath = new File(path);
		File[] fs = fpath.listFiles(new FileFilter() {

			@Override
			public boolean accept(File arg0) {
				// TODO Auto-generated method stub
				String fileName = arg0.getName();
				for (String aid : autoids) {
					if (fileName.endsWith("_" + aid + ".zip"))
						return true;
				}
				return false;
			}

		});
		for (File f : fs) {
			main_service.log.info("delete file:" + f.getName());
			//2018-12-29,把文件拷到另一个目录下,以后方便跟踪
			cpDelFile(f);
			f.delete();
		}
		response.getWriter().print("ok");
	}
	
	static void cpDelFile(File f) throws IOException
	{
		String delPath = "d:/yunfilesctrl/delfiles/";
		new File(delPath).mkdirs();
		String sFileName = f.getAbsolutePath();
		String dFileName = delPath + f.getName();
		zipupload.copyfile(sFileName, dFileName);
	}
	
	public static void main(String[] argv) throws IOException
	{
		File f = new File("e:/src.rar");
		cpDelFile(f);
	}

}
