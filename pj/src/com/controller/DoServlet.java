package com.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils.Null;

import com.DAO.CommentDAO;
import com.DAO.GeoDAO;
import com.DAO.ImageDAO;
import com.DAO.RelationshipDAO;
import com.DAO.TravelUserDAO;
import com.Model.Geo;
import com.Model.Image;
import com.Model.TravelUser;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class DoServlet
 */
@WebServlet("*.do")
public class DoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("--> .do");

		String folderName = "PersonalSpace";
		String servletPath = request.getServletPath();
		if (servletPath.contains(folderName)) {
			servletPath = servletPath.substring(folderName.length()+2, servletPath.length() - 3);

		} else {
			servletPath = servletPath.substring(1, servletPath.length() - 3);
		}
		System.out.println("-->" + servletPath);

		try {

			// ���÷�������һ������
			Method method = getClass().getDeclaredMethod(servletPath, HttpServletRequest.class,
					HttpServletResponse.class);
			// ���ô�servelt�Ķ�Ӧ����
			method.invoke(this, request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param request
	 * @param response
	 * 
	 *                 out: [0] 0/ ����ʧ�� class���� ��1/ �����ɹ� �任class [1] agree number
	 *                 [2] 0/uid unagree 1/uid agree
	 */
	private void doAgree(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		int commentID = Integer.parseInt(request.getParameter("commentID"));
		int uid = Integer.parseInt(request.getParameter("uid"));

		CommentDAO commentDAO = CommentDAO.getCommentDAO();
		// default
		int doState = commentDAO.doAgreementByUidImageID(uid, commentID);
		int agreeNumber = commentDAO.getAgreeNumberByCommentID(commentID);
		int isLike = commentDAO.isAgreeByUidImageID(uid, commentID) ? 1 : 0;

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(doState);
		jsonArray.add(agreeNumber);
		jsonArray.add(isLike);

		System.out.println("jsonArray.toString()===========================");
		System.out.println(jsonArray.toString());
		System.out.println("==============================================");
		try {
			response.getWriter().print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * in: comment uid iid
	 * 
	 * @param request
	 * @param response out: [0]key -1/����ʧ�ܣ�>0 ��������ֵ [1]string html of ���������
	 *                 ����comment html
	 * 
	 * 
	 */
	private void sendComment(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String comment = request.getParameter("comment");
		int uid = Integer.parseInt(request.getParameter("uid"));
		int iid = Integer.parseInt(request.getParameter("iid"));

		String username = TravelUserDAO.getTravelUserDAO().getByUid(uid).getUsername();
		CommentDAO commentDAO = CommentDAO.getCommentDAO();
		int commentID = commentDAO.sendCommentAndGetKey(uid, iid, comment);
		Timestamp newDate = new Timestamp(new Date().getTime());
		String dateStr = newDate.toString().substring(0, newDate.toString().lastIndexOf("."));

		String commentHTML = commentHTML(uid, username, comment, dateStr, commentID);

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(commentID);
		jsonArray.add(commentHTML);

		try {
			response.getWriter().print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param request
	 * @param response out: 0/1 ��ʾstate
	 * 
	 */
	private void changeState(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");

		int uid = Integer.parseInt(request.getParameter("uid"));

		TravelUserDAO travelUserDAO = TravelUserDAO.getTravelUserDAO();
		int outData = travelUserDAO.changeStateByUid(uid);

		TravelUser localuser = travelUserDAO.getByUid(uid);
		request.getSession().setAttribute("localuser", null);
		request.getSession().setAttribute("localuser", localuser.getForSession());

		System.out.println(request.getSession().getAttribute("localuser").toString());

		try {
			response.getWriter().print(outData);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ���ͽ������� �� myfriends.jsp�б�����
	 * 
	 * @param request
	 * @param response
	 * 
	 *                 out: 0/ û�з��Ͳ������Ǻ��� 1/ ���Ͳ��������ұ�֤����֮�����ҽ�����һ����¼
	 */
	private void makeFriends(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		int fromUID = Integer.parseInt(request.getParameter("fromUID"));
		int toUID = Integer.parseInt(request.getParameter("toUID"));

		RelationshipDAO relationshipDAO = RelationshipDAO.getRelationshipDAO();
		int outData = relationshipDAO.sendRequestForMakeFriends(fromUID, toUID);

		try {
			response.getWriter().print(outData);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��send������������ ����һ�������from==to,��ʱ���뷵��0����Ϊ����receiveֻ��refuse
	 * 
	 * @param request
	 * @param response
	 * 
	 *                 out: 1/ �����ɹ� 0/ ����ʧ��
	 */
	private void receiver(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		int fromUID = Integer.parseInt(request.getParameter("fromUID"));
		int toUID = Integer.parseInt(request.getParameter("toUID"));

		RelationshipDAO relationshipDAO = RelationshipDAO.getRelationshipDAO();
		if (fromUID == toUID) {
			try {
				response.getWriter().print(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		int outData = relationshipDAO.receiveRequestOfMakingFriends(toUID, fromUID);

		try {
			response.getWriter().print(outData);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * �ܾ�����ɾ�����ݣ�������ȫ copy receive�ķ���
	 * 
	 * @param request
	 * @param response
	 * 
	 *                 0/û�в��� 1/�������
	 */
	private void refuser(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		int fromUID = Integer.parseInt(request.getParameter("fromUID"));
		int toUID = Integer.parseInt(request.getParameter("toUID"));

		RelationshipDAO relationshipDAO = RelationshipDAO.getRelationshipDAO();
		int outData = relationshipDAO.refuseRequestOfMakingFriends(toUID, fromUID);

		try {
			response.getWriter().print(outData);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ʵ�� search.jsp �ķ�ҳ
	 * 
	 * @param request
	 * @param response
	 * 
	 *                 in: filter_condi[title], input_content, order_way, order
	 *                 show_way, length, page , like,
	 * 
	 *                 �м����ݣ�images
	 * 
	 *                 out�� json[0]-json[length-2]Ϊlength-1����Ƭ��HTML String
	 *                 json[length-1] Ϊ str HTML of page number
	 */
	private void page(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		ArrayList<Image> images = new ArrayList<>();

		String filter_condi = request.getParameter("filter_condi");
		String input_content = request.getParameter("input_content");
		String order_way = request.getParameter("order_way");
		String order = request.getParameter("order");
		String show_way = request.getParameter("show_way");

		int length = show_way.equals("mess") ? 6 : 20;
		int page = Integer.parseInt(request.getParameter("page"));

		try {
			images = ImageDAO.getImageDAO().getSearchImagesByPages(filter_condi, input_content, order_way, order,
					length, page);

		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.sendRedirect("./search.nav");
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}

		}

		int totalPage = ImageDAO.getImageDAO().getSearchImagesTotalPage(filter_condi, input_content, order_way, order,
				length);
		int currentPage = page;
		int nextPage = ((page + 1) >= totalPage) ? totalPage : (page + 1);
		int previousPage = ((page - 1) <= 1) ? 1 : (page - 1);

		// ����array list to str
		// images.innerHTML
		String[] articles = new String[images.size()];
		if (images.size() == 0) {
			articles = new String[1];
			articles[0] = "<div class='block-center'>\r\n <p> û�з����ҵ�Ҫ���ͼƬ </p>\r\n </div>";
			System.out.println("meiyou");
		} else if (length == 6) {
			for (int i = 0; i < images.size(); i++)
				articles[i] = images.get(i).toSearchMessStr();
			System.out.println(" mess ");
		} else if (length == 20) {
			for (int i = 0; i < images.size(); i++)
				articles[i] = images.get(i).toSearchPicsStr();
			System.out.println("pics");
		}
		// pics
		JSONArray json = JSONArray.fromObject(articles);
		json.add(getPageNumberString(totalPage, currentPage, nextPage, previousPage));

		try {
			response.getWriter().print(json);
//			request.getRequestDispatcher(path).forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param totalPage
	 * @param currentPage
	 * @param nextPage
	 * @param previousPage
	 * @return
	 * 
	 * 		page_number.innerHTML
	 * 
	 */
	private String getPageNumberString(int totalPage, int currentPage, int nextPage, int previousPage) {
		StringBuffer stringBuffer = new StringBuffer();
		// ��һҳ <<
		stringBuffer.append("<a class=' btPage' onclick=doPage('1')>��һҳ</a>\n" + "<a class=' btPage' onclick=doPage('"
				+ previousPage + "')>&lt;&lt;</a>");

		if (totalPage <= 6) {
			for (int a = 1; a <= totalPage; a++) {
				if (a == currentPage)
					stringBuffer.append("\n<a class=' active btPage' onclick=doPage('" + a + "')>" + a + "</a>\n");
				else
					stringBuffer.append("\n<a  class=' btPage' onclick=doPage('" + a + "')>" + a + "</a>\n");
			}
		}
		if (totalPage > 6) {
			boolean hasFirstChara = false;
			boolean hasSecondChara = false;

			for (int a = 1; a <= totalPage; a++) {
				if (a == currentPage)
					stringBuffer.append("\n<a class=' active btPage' onclick=doPage('" + a + "')>" + a + "</a>\n");
				else if (a <= 5 || a == totalPage || a == (currentPage - 1) || a == (currentPage + 1))
					stringBuffer.append("\n<a class=' btPage' onclick=doPage('" + a + "')>" + a + "</a>\n");
				else if (a > 5 && a < (currentPage - 1) && !hasFirstChara) {
					stringBuffer.append("\n<a value='#'>... ...</a>\n");
					hasFirstChara = true;
				} else if (a > (currentPage + 1) && a < totalPage && !hasSecondChara) {
					stringBuffer.append("\n<a value='#'>... ...</a>\n");
					hasSecondChara = true;
				}

			}

		}

		// ���һҳ >>
		stringBuffer.append(" <a class=' btPage' onclick=doPage('" + nextPage + "')>&gt;&gt;</a>\n"
				+ "            <a class=' btPage' onclick=doPage('" + totalPage + "') >���һҳ</a>\n"
				+ "            <hr>");
		/// System.out.println(stringBuffer);
		return stringBuffer.toString();
	}

	// ɾ���ϴ���ͼƬ��
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");

		int iid = Integer.parseInt(request.getParameter("iid"));
		int uid = Integer.parseInt(request.getParameter("uid"));

		int deleteState = 0;
		ImageDAO imageDAO = ImageDAO.getImageDAO();
		Image image = imageDAO.getByImageId(iid);
		if (image != null) {
			imageDAO.deleteImageByImageID(iid, uid);
			image = imageDAO.getByImageId(iid);
			if (image == null)
				deleteState = 1;
			else
				deleteState = -1;
		}

		if (deleteState == 1)
			System.out.println("Delete successfully!");
		if (deleteState == 0)
			System.out.println("no such image!");
		if (deleteState == -1)
			System.out.println("delete failed!");

		try {
			response.getWriter().print(deleteState);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// details ��like
	// myfavourite��like
	// ����like �� like��
	private void like(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		int likeState = 0;

		int iid = Integer.parseInt(request.getParameter("iid"));
		int uid = Integer.parseInt(request.getParameter("uid"));

		System.out.println("-->iid : " + iid + "; uid: " + uid);

		ImageDAO imageDAO = ImageDAO.getImageDAO();
		imageDAO.doLike(iid, uid);

		int likeNumber = imageDAO.getLikeByImageID(iid);

		if (imageDAO.isLike(iid, uid)) {
			likeState = 1;
			System.out.println("like!");
		} else {
			likeState = -1;
			System.out.println("Unlike!");
		}

		String jsonStr = "[" + likeState + "," + likeNumber + "]";

		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		System.out.println("================::after like.do===============");
		try {
			response.getWriter().print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// modify ����ύ
	// ��checksubmit
	// ��ok֮�󣬵���ȷ�ϱ���?
	// ���������ת��modify��do
	// modify�����޸Ĳ�����Ȼ����תdetail��nav
	private void modify(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		int iid = Integer.parseInt(request.getParameter("iid"));

		String title = request.getParameter("title");
		String description = request.getParameter("description");
		int cityCode = Integer.parseInt(request.getParameter("cityCode"));
		String countryCodeISO = request.getParameter("countryCodeISO");
		String content = request.getParameter("content");

		ImageDAO.getImageDAO().modifyImage(title, description, cityCode, countryCodeISO, content, iid);
		;

		try {
			String path = "./details.nav?id=" + iid;
			response.sendRedirect(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void upload(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		Map<String, String> localuser = (Map<String, String>) request.getSession().getAttribute("localuser");
		int uid = Integer.parseInt(localuser.get("uid"));

		// int iid = Integer.parseInt(request.getParameter("iid"));
		String title = request.getParameter("title");
		String descripttion = request.getParameter("description");
		String path = request.getParameter("path");
		int cityCode = Integer.parseInt(request.getParameter("cityCode"));
		String countryCodeISO = request.getParameter("countryCodeISO");
		String content = request.getParameter("content");

		Timestamp newDate = new Timestamp(new Date().getTime());
		String sql = "INSERT INTO `travelimage`(`ImageID`, `Title`, `Description`, `Latitude`, `Longitude`,"
				+ " `CityCode`, `Country_RegionCodeISO`, `UID`, `PATH`, `Content`, `DateUpload`) "
				+ "VALUES (0, ?, ?, 0, 0, ?, ?, ?, ?, ? ,?)";
		int iid = ImageDAO.getImageDAO().getKeyAfterInsert(sql, title, descripttion, cityCode, countryCodeISO, uid,
				path, content, newDate);

		try {
			response.sendRedirect("./details.nav?id=" + iid);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cityQuery(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");

		String countryCodeISO = request.getParameter("countryCodeISO");
		List<Geo> geos = GeoDAO.getGeoDAO().getAllCitiesByCountryCodeISOForArrayList(countryCodeISO);
		JSONArray json = JSONArray.fromObject(geos);

		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * @param uid
	 * @param username
	 * @param comment
	 * @param dateCommentStr
	 * @param commentID
	 * @return
	 * 
	 * 		���к���sendcomment����
	 */
	private String commentHTML(int uid, String username, String comment, String dateCommentStr, int commentID) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" <div class=\"col-xs-10\">\r\n"
				+ "	            	        <div class=\"comment-div full-length \">");
		stringBuffer.append("<p>\r\n" + "	            	        	<a href=\"./PersonalSpace/userpics.pers?uid="
				+ uid + "\">");
		stringBuffer.append(username + " : \n</a>\n" + comment + "</p>\n");
		stringBuffer.append("<p class=\"footer-time\">" + dateCommentStr + "</p>");
		stringBuffer.append(" </div>\r\n" + "	            	        <div class=\"hr-div\"><hr></div>\r\n"
				+ "	            	        \r\n" + "                   		</div>");
		stringBuffer.append("<div class=\"col-xs-2\" " + "id=\"div-agree-for-" + commentID + "\">");
		stringBuffer.append(
				"<button class=\"btn btn-default btAgreement\"" + " onclick=doAgreement(\"" + commentID + "\") >");
		stringBuffer.append("<span class=\"glyphicon glyphicon-thumbs-up\"> </span> 0 ");
		stringBuffer.append("</button>\r\n" + "                    	</div>");

		return stringBuffer.toString();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
