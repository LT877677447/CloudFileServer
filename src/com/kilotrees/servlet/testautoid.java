package com.kilotrees.servlet;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kilotrees.util.DateUtil;

/**
 * Servlet implementation class testautoid
 */
@WebServlet("/testautoid")
public class testautoid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public testautoid() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int allfileLen = 0;		
		String content_len = request.getHeader("Content-Length");
		if (content_len != null && content_len.length() > 0)
			allfileLen = Integer.parseInt(content_len);
		String autoid = request.getParameter("autoid");
		String saveFile = "d:/yunfilesctrl_poslog/" + autoid + ".txt";
		saveFile += DateUtil.getDateString(new Date()) + ".txt";
		byte[] buf = new byte[allfileLen];
		FileOutputStream fos = new FileOutputStream(saveFile);
		InputStream ins = request.getInputStream();
		DataInputStream dins = new DataInputStream(ins);
		dins.readFully(buf);
		fos.write(buf);
		ins.close();
		fos.close();
	}

}
