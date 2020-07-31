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

import com.DAO.CommentDAO;
import com.DAO.ImageDAO;
import com.Model.Comment;
import com.Model.Image;
import com.Model.MyTrack;

/**
 * Servlet implementation class NavServlet
 */
@WebServlet("*.nav")
public class NavServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String folderName = "PersonalSpace";
		
		String servletPath = request.getServletPath();
		if (servletPath.contains(folderName)) {
			servletPath = servletPath.substring(folderName.length()+2, servletPath.length() - 4);

		} else
		servletPath = servletPath.substring(1, servletPath.length() - 4);
		System.out.println("=========================================================> "+servletPath+".nav  <=================================================");
		System.out.println(servletPath);

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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// !!!!!!!!!!!!!oh my
		response.setContentType("text/html;charset=utf-8");

		// 設置輪播圖片
		ImageDAO imageDAO = ImageDAO.getImageDAO();
		ArrayList<Image> carouselImages = imageDAO.getHotImages(4);
		request.setAttribute("carousel", carouselImages);

		// 設置12張隨機圖片
		ArrayList<Image> showImages = imageDAO.getLatestImages(12);
		request.setAttribute("show", showImages);

		try {
			String path = "./index.jsp";
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param request
	 * @param response
	 * 
	 * in: filter_condi[title]  input_content  order_way  order  show_way
	 * length page paramStr
	 * 
	 * out：images totalPage currentPage  nextPage  previousPage
	 */
	private void search(HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/html;charset=utf-8");
		String path = "./search.jsp";

		ArrayList<Image> images = new ArrayList<>();

		String filter_condi = (request.getParameter("filter-condi") == null) ? "Title"
				: request.getParameter("filter-condi");// title description content
		String input_content = (request.getParameter(filter_condi.toLowerCase()) == null) ? ""
				: request.getParameter(filter_condi.toLowerCase()); // like '% %
		String order_way = (request.getParameter("order-way") == null) ? "DateUpload"
				: request.getParameter("order-way"); // like / time
		String order = (request.getParameter("order") == null) ? "ASC" : request.getParameter("order"); // low:desc
																										// high:asc
		String show_way = (request.getParameter("show-way") == null) ? "mess" : request.getParameter("show-way"); // mess/pics

		int length = show_way.equals("mess") ? 6 : 20;
		int page = (request.getParameter("page") == null) ? 1 : Integer.parseInt(request.getParameter("page"));

		String paramStr = "?filter-condi=" + filter_condi + "&" + filter_condi.toLowerCase() + "=" + input_content
				+ "&order-way=" + order_way + "&order=" + order + "&show-way=" + show_way;

		try {
			images = ImageDAO.getImageDAO().getSearchImagesByPages(filter_condi, input_content, order_way, order, length,
					page);
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.sendRedirect("./search.nav");
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
			
		}
		request.setAttribute("images", images);

		int totalPage = ImageDAO.getImageDAO().getSearchImagesTotalPage(filter_condi, input_content, order_way, order,
				length);
		int currentPage = page;
		int nextPage = ((page + 1) >= totalPage) ? totalPage : (page + 1);
		int previousPage = ((page - 1) <= 1) ? 1 : (page - 1);

		request.setAttribute("totalPage", totalPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("nextPage", nextPage);
		request.setAttribute("previousPage", previousPage);

		try {
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param request
	 * @param response
	 * 
	 * set:
	 * image
	 * 
	 * local user
	 * my track: user/session
	 * is like: user & image
	 * comments: image
	 * myCommentsAgreement: user & image 
	 * 
	 */
	private void details(HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/html;charset=utf-8");
		// 從？后取得iid的值，轉換為int
		int iid = Integer.parseInt(request.getParameter("id"));

		ImageDAO imageDAO = ImageDAO.getImageDAO();
		Image image = imageDAO.getByImageId(iid);

		request.setAttribute("image", image);

		// 登陆：设置like按钮样式isLike、评论comments属性
		if (request.getSession().getAttribute("localuser") != null) {
			Map<String, String> localuser = (Map<String, String>) request.getSession().getAttribute("localuser");
			int uid = Integer.parseInt(localuser.get("uid"));

			// 界面變化
			if (imageDAO.isLike(iid, uid)) {
				request.setAttribute("isLike", true);
			}
			
			String orderWay = request.getParameter("order-way");
			ArrayList<Comment> comments = new ArrayList<>();
			if(orderWay == null || orderWay.equals("DateUpload")) {
				comments = CommentDAO.getCommentDAO().getCommentsByImageID(iid);
			}else {
				comments = CommentDAO.getCommentDAO().getCommentsByImageIDOrderByLike(iid);
			}
			
			System.out.println("nav details ===================== comments");
			System.out.println(comments.toString());
			request.setAttribute("comments", comments);
			
			
			ArrayList<Integer> myAgreementOfCommentsOfThisImage = CommentDAO.getCommentDAO().getMyAgreementByImageIDAndUserID(iid, uid);
			request.setAttribute("myCommentsAgreement", myAgreementOfCommentsOfThisImage);
		}
		
		
		
		if(request.getSession().getAttribute("myTrack") == null) {
			MyTrack myTrack = new MyTrack();
			myTrack.newImageVisit(iid);
			request.getSession().setAttribute("myTrack", myTrack);
			//System.out.println("nav details ===================== myTrack.toString()");
			//System.out.println(myTrack.toString());
		}else {
			MyTrack myTrack = (MyTrack)request.getSession().getAttribute("myTrack");
			myTrack.newImageVisit(iid);
			//System.out.println("nav details ===================== myTrack.toString()");
			//System.out.println(myTrack.toString());			
			
		}

		String path = "./details.jsp";
		try {
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
