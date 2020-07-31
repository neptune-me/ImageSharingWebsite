package com.Servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.AESEncrypt;
import com.DAO.TravelUserDAO;
import com.Model.TravelUser;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取变量
		String username = request.getParameter("username");// patrick.gray@aol.com or patrick.gray
		String pass = request.getParameter("password1");// abcd1234
		String validCode = request.getParameter("validCode");

		boolean isLegal = true;
		String strResponse = "User name or password wrong! <br>pass: " + pass + " user: " + username;

		System.out.println("===================validCode===============");
		System.out.println(validCode);

		//验证码
		if (validCode == null || "".equals(validCode.trim())) {
			System.out.println("===================validCode empty===============");
			strResponse = "Empty Valid Code!";
			isLegal = false;
		} else {
			String text = ValidImageServlet.text;
			String t1 = text.toLowerCase();
			String t2 = text.toUpperCase();

			if (!validCode.matches(t1) && !validCode.matches(t2) && !validCode.matches(text)) {
				strResponse = "Wrong Valid Code!";

				isLegal = false;
			}
		}

		if (isLegal) {

			// 連接數據庫
			TravelUserDAO travelUserDAO = TravelUserDAO.getTravelUserDAO();

			// 获取username对应的keyMap
			Map<Integer, String> keyMap = travelUserDAO.getKeysByUsername(username);

			AESEncrypt.loadKey(keyMap.get(0), keyMap.get(1));

			// 对已知的pass进行加密
			String passEncrypt = AESEncrypt.getEncrypt(pass);

			// 准备sql语句查询加密后的pass
			String sql = "SELECT pass FROM traveluser WHERE username=? OR email=?";
			String passInDB = travelUserDAO.getForValue(String.class, sql, username, username);

			// 比对 相同则为正确
			isLegal = passInDB != null && passInDB.equals(passEncrypt);
		}

		if (isLegal) {
			// 查找username/email对应的TravelUser对象
			TravelUser travelUser = TravelUserDAO.getTravelUserDAO().getByUsernameOrEmail(username);

			// session设置对象
			HttpSession session = request.getSession();
			session.setAttribute("localuser", travelUser.getForSession());

			// 重定向到index
			String path = "./index.jsp";
			response.sendRedirect(path);
		} else {
			// 不合法 显示提示信息 username/email或密码错误

			request.setAttribute("responseStr", strResponse);

			request.getRequestDispatcher("./login.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("> Login's doPost");
		doGet(request, response);
	}

}
