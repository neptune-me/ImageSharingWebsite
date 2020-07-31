package com.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Valid.ValidCode;


/**
 * Servlet implementation class ValidCodeServlet
 */
@WebServlet("/ValidImageServlet")
public class ValidImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String text = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ValidCode verifyCode = new ValidCode();

		verifyCode.drawImage(response.getOutputStream());

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-control", "no-cache");
		text = verifyCode.getCode();
		System.out.println("text" + text);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
