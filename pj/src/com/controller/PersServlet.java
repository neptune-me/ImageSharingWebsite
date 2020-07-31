package com.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.GeoDAO;
import com.DAO.ImageDAO;
import com.DAO.RelationshipDAO;
import com.DAO.TravelUserDAO;
import com.Model.Image;
import com.Model.TravelUser;

import sun.security.util.Length;

/**
 * Servlet implementation class PersServlet
 */
@WebServlet("*.pers")
public class PersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String> localuser = (Map<String, String>) request.getSession().getAttribute("localuser");

		String folderName = "PersonalSpace";
		String servletPath = request.getServletPath();
		if (servletPath.contains(folderName)) {
			servletPath = servletPath.substring(folderName.length()+2, servletPath.length() - 5);
			
		}else
		servletPath = servletPath.substring(1, servletPath.length() - 5);
		System.out.println(folderName.length()+1);
		System.out.println("=========================================================> "+servletPath+".pers  <=================================================");
		System.out.println(servletPath);

		if (localuser == null) {
			response.sendRedirect("../login.jsp");
		}
		try {

			// 利用反射声明一个方法
			Method method = getClass().getDeclaredMethod(servletPath, HttpServletRequest.class,
					HttpServletResponse.class);
			// 调用此servelt的对应方法
			method.invoke(this, request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void mypics(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		Map<String, String> localuser = (Map<String, String>) request.getSession().getAttribute("localuser");
		int uid = Integer.parseInt(localuser.get("uid"));

		System.out.println("--uid: " + uid);

		int length = 6;
		int page = (request.getParameter("page") == null) ? 1 : Integer.parseInt(request.getParameter("page"));

		ArrayList<Image> images = ImageDAO.getImageDAO().getMyImagesByUid(uid, length, page);
		request.setAttribute("images", images);

		int totalNumber = ImageDAO.getImageDAO().getMyImagesNumberByUid(uid);
		int totalPage = ImageDAO.getImageDAO().getTotalPage(length, totalNumber);
		//totalPage = 0 is ok
		int currentPage = page;
		int nextPage = ((page + 1) >= totalPage) ? totalPage : (page + 1);
		int previousPage = ((page - 1) <= 1) ? 1 : (page - 1);

		request.setAttribute("totalPage", totalPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("nextPage", nextPage);
		request.setAttribute("previousPage", previousPage);

		try {
			String path = "./mypics.jsp";
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void myfavourite(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		Map<String, String> localuser = (Map<String, String>) request.getSession().getAttribute("localuser");
		int uid = Integer.parseInt(localuser.get("uid"));
		
		int state = TravelUserDAO.getTravelUserDAO().getByUid(uid).getState();
		request.setAttribute("state", state+"");

		int length = 6;
		int page = (request.getParameter("page") == null) ? 1 : Integer.parseInt(request.getParameter("page"));

		ArrayList<Image> images = ImageDAO.getImageDAO().getFavorImagesByUid(uid, length, page);
		request.setAttribute("images", images);

		int totalNumber = ImageDAO.getImageDAO().getFavorImagesNumberByUid(uid);
		int totalPage = ImageDAO.getImageDAO().getTotalPage(length, totalNumber);
		int currentPage = page;
		int nextPage = ((page + 1) >= totalPage) ? totalPage : (page + 1);
		int previousPage = ((page - 1) <= 1) ? 1 : (page - 1);

		request.setAttribute("totalPage", totalPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("nextPage", nextPage);
		request.setAttribute("previousPage", previousPage);

		try {
			String path = "./myfavourite.jsp";
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**上传页面
	 * @param request
	 * @param response
	 */
	private void upload(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		try {
			Map<String, String> localuser = (Map<String, String>) request.getSession().getAttribute("localuser");
			int uid = Integer.parseInt(localuser.get("uid"));

		} catch (Exception e) {
			try {
				response.sendRedirect("../login.jsp");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (request.getParameter("iid") != null) {

			int iid = Integer.parseInt(request.getParameter("iid"));
			Image image = ImageDAO.getImageDAO().getByImageId(iid);
			System.out.println(image.getDescription());
			request.setAttribute("image", image);
		}

		Map<String, String> countryMap = GeoDAO.getGeoDAO().getAllCountries();
		request.setAttribute("countryMap", countryMap);

		// Map<String, String> cityMap =

		try {
			String path = "./upload.jsp";
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 用户的收藏，注意state的改变会导致结果不同
	 * @param request
	 * @param response
	 * 
	 * set： pageUser state images
	 * 	翻页的四个变量
	 */
	private void userfavourite(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		int uid = Integer.parseInt(request.getParameter("uid"));
		TravelUser user = TravelUserDAO.getTravelUserDAO().getByUid(uid);
		System.out.println(user.toString());
		request.setAttribute("pageUser", user);

		int state = user.getState();
		request.setAttribute("state", state+"");
		int length = 6;
		int page = (request.getParameter("page") == null) ? 1 : Integer.parseInt(request.getParameter("page"));
		int totalPage = 1;
		ArrayList<Image> images = new ArrayList<>();
		
		int showState = user.getState();
		if (showState == 0) {
			page = 1;
			System.out.println("=====> 收藏顯示關閉");

		} else {
			System.out.println("=====> 收藏顯示開啓");
			
			if(page <= 0)
				page = 1;
			images = ImageDAO.getImageDAO().getFavorImagesByUid(uid, length, page);
			int totalNumber = ImageDAO.getImageDAO().getFavorImagesNumberByUid(uid);
			totalPage = ImageDAO.getImageDAO().getTotalPage(length, totalNumber);
		}

		int currentPage = page;
		int nextPage = ((page + 1) >= totalPage) ? totalPage : (page + 1);
		int previousPage = ((page - 1) <= 1) ? 1 : (page - 1);
		request.setAttribute("images", images);

		request.setAttribute("totalPage", totalPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("nextPage", nextPage);
		request.setAttribute("previousPage", previousPage);

		try {
			String path = "./userfavourite.jsp";
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 用户的照片
	 * 
	 * @param request
	 * @param response
	 * 
	 * set： pageUser state images
	 * 	翻页的四个变量
	 * 
	 */
	private void userpics(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		int uid = Integer.parseInt(request.getParameter("uid"));
		TravelUser user = TravelUserDAO.getTravelUserDAO().getByUid(uid);
		request.setAttribute("pageUser", user);

		int length = 6;
		int page = (request.getParameter("page") == null) ? 1 : Integer.parseInt(request.getParameter("page"));

		ArrayList<Image> images = ImageDAO.getImageDAO().getMyImagesByUid(uid, length, page);
		request.setAttribute("images", images);

		int totalNumber = ImageDAO.getImageDAO().getMyImagesNumberByUid(uid);
		int totalPage = ImageDAO.getImageDAO().getTotalPage(length, totalNumber);
		int currentPage = page;
		int nextPage = ((page + 1) >= totalPage) ? totalPage : (page + 1);
		int previousPage = ((page - 1) <= 1) ? 1 : (page - 1);

		request.setAttribute("totalPage", totalPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("nextPage", nextPage);
		request.setAttribute("previousPage", previousPage);

		try {
			String path = "./userpics.jsp";
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 我的朋友
	 * @param request
	 * @param response
	 * 
	 *                 common : uid 翻頁 page length = 10
	 * 
	 *                 out: arraylist < traveluser >
	 * 
	 */
	private void myfriends(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		Map<String, String> localuser = (Map<String, String>) request.getSession().getAttribute("localuser");
		int uid = Integer.parseInt(localuser.get("uid"));

		RelationshipDAO relationshipDAO = RelationshipDAO.getRelationshipDAO();
		ArrayList<TravelUser> myFriends = relationshipDAO.getMyFriendsByUid(uid);

		request.setAttribute("myfriendsList", myFriends);

		try {
			String path = "./myfriends.jsp";
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 我的朋友页面，显示的是user 查询
	 * 
	 * @param request
	 * @param response
	 * 
	 *  in: search : searchInfo 
	 *  out: arraylist < traveluser >
	 */
	private void searchUser(HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/html;charset=utf-8");
		String searchInfo = request.getParameter("searchInfo");

		RelationshipDAO relationshipDAO = RelationshipDAO.getRelationshipDAO();
		TravelUserDAO travelUserDAO = TravelUserDAO.getTravelUserDAO();
		ArrayList<TravelUser> travelUsers = travelUserDAO.getTravelUserSearchByUserNameOrEmail(searchInfo);

		request.setAttribute("userSearchList", travelUsers);
		System.out.println("======》 travelUsers:");
		System.out.println(travelUsers.toString());

		try {
			String path = "./myfriends.jsp";
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
			System.out.println("Servlet Opos");
		} catch (IOException e) {
			System.out.println("IO Opos");
			e.printStackTrace();
		}

	}
	
	/**
	 * 我的新消息
	 * 获取方式 [servlet]类似我的朋友；工作方式[jsp/ajax]类似用户查询
	 * 
	 * @param request
	 * @param response
	 * 
	 * uid
	 * out：arraylist user 和search user很像
	 * set:uncheckedMessageList
	 * 
	 * mymessage.jsp
	 * 绑定receive.do & refuse.do
	 */
	private void mymessage(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		Map<String, String> localuser = (Map<String, String>) request.getSession().getAttribute("localuser");
		int uid = Integer.parseInt(localuser.get("uid"));
		
		RelationshipDAO relationshipDAO = RelationshipDAO.getRelationshipDAO();
		ArrayList<TravelUser> uncheckedMessageList = relationshipDAO.getUncheckMessageByUid(uid);
		
		request.setAttribute("uncheckedMessageList", uncheckedMessageList);
		
		try {
			String path = "./mymessage.jsp";
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
