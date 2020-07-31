package com.Servlets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DAO.TravelUserDAO;
import com.Model.TravelUser;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String strResponse = "";
		// "You must Enter All the Blank!";
		boolean isLegal = true;

		Enumeration<String> namesEnum = request.getParameterNames();

		Map<String, String> values = new HashMap<String, String>();
		while (namesEnum.hasMoreElements()) {
			String key = namesEnum.nextElement();
			String value = request.getParameter(key);
			values.put(key, value);
			System.out.println(key);
			if (value == null || "".equals(value)) {
				// ���Ϸ�,�п�ֵ
				System.out.println("empty "+key);
				strResponse = "You must Enter All the Blank!";
				isLegal = false;
			}
		}

		// ������֤�룺
		String validCode = values.get("validCode");
		System.out.println("===================validCode===============");
		System.out.println(validCode);

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
			} else {

				// ���ݿ�
				TravelUserDAO travelUserDAO = TravelUserDAO.getTravelUserDAO();
				// ��ѯ�Ƿ�username����
				System.out.println("===================searching user or email===============");
				String sql = "SELECT * FROM traveluser WHERE username=? OR email=?";
				int usernameCount = travelUserDAO.getRowCount(sql, values.get("username"), values.get("mail"));

				// ���ע���Ƿ�Ϸ��������ڼ�Ϊ�Ϸ�
				if (usernameCount == 0) {
					// isLegal = true;
				} else {
					isLegal = false;
					strResponse = "Username Or Email Address is already existed!";
				}
			}

		}

		// ���Ϸ�
		if (isLegal) {
			System.out.println("===================is  Leagal==============");
			// ִ�в������
			TravelUserDAO travelUserDAO = TravelUserDAO.getTravelUserDAO();
			travelUserDAO.addTraveluser(values.get("username"), values.get("password1"), values.get("mail"));

			// ���� request ��attri��islegal����
			HttpSession session = request.getSession();
			request.setAttribute("isLegal", isLegal);

			// ����traveluser��������response����
			// ���� UserID, Username, Email, Pass, DateJoined
			TravelUser traveluser = travelUserDAO.getByUsername(values.get("username"));
			session.setAttribute("localuser", traveluser.getForSession());

			int uid = traveluser.getUserID();
			String username = traveluser.getUsername();
			String email = traveluser.getEmail();
			String pass = traveluser.getPass();

			java.sql.Timestamp dateJoined = traveluser.getDateJoined();

			request.setAttribute("uidForSubmit", uid);
			request.setAttribute("usernameForSubmit", username);
			request.setAttribute("emailForSubmit", email);
			request.setAttribute("passForSubmit", values.get("password1"));
			request.setAttribute("dateJoinedForSubmit", dateJoined);

			System.out.println("uid" + uid);
			System.out.println("ע��ɹ�");
			// �ض���ص�ԭregister��ʾ
			// �൱��ˢ�£����ظ��ύ
			request.getRequestDispatcher("./register-submit.jsp").forward(request, response);
		} else {
			System.out.println("===================! leagal ===============");
			request.setAttribute("responseStr", strResponse);
			request.getRequestDispatcher("./register.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("Register's doPost");
		doGet(request, response);
	}
}
