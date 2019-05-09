package com.kilotrees.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class textproxy
 */
@WebServlet("/textproxy")
public class textproxy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public textproxy() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		PrintWriter out = response.getWriter();
		 response.setContentType("text/plain");

	        Enumeration<String> headerNames = request.getHeaderNames();

	        while (headerNames.hasMoreElements()) {

	            String headerName = headerNames.nextElement();
	            out.write(headerName);
	            out.write("\n");

	            Enumeration<String> headers = request.getHeaders(headerName);
	            while (headers.hasMoreElements()) {
	                String headerValue = headers.nextElement();
	                out.write("\t" + headerValue);
	                out.write("\n");
	            }

	        }

	        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
