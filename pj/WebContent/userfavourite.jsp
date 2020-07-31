<%@page import="com.Model.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import=" com.DAO.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Unsplash | 好友的收藏夹</title>
  <link href="./bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="./css/index_css.css" rel="stylesheet">
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <script src="./bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<%@include file="include/header.jsp"%>
<%@include file="include/float_button.jsp"%>
</head>
<body>

<% 
request.setCharacterEncoding("UTF-8");

if(session.getAttribute("localuser") == null){
	String path="./login.jsp";
	response.sendRedirect(path);
}else{
	
}
if(request.getAttribute("images") == null){
	System.out.println("null images");
	ArrayList<Image> newImages = new ArrayList<>();
	request.setAttribute("images",newImages); 
}

TravelUser pageUser = (TravelUser)request.getAttribute("pageUser");
%>

	
	<section id="pageup">
		<div class="panel panel-default">
			
			<div class="panel-heading">
				<header> <span class="glyphicon glyphicon-star-empty" > </span> <%= pageUser.getUsername() %> 的收藏</header>
			</div>
			
			<div class="panel-body">
				<div class="row">
					<input type="text" id="pageuser_uid" value="<%= pageUser.getUserID() %>" disabled style="display:none">
					<%
					 ArrayList<Image> images = (ArrayList<Image>)request.getAttribute("images");
					if(request.getAttribute("images") != null){
						for(Image image:images){
							
					%>

					<div class="col-xs-12">
					    <article class="left"  id="article_<%= image.getImageID() %>">
					    
					        <header class="image_container">
					            <a title="ImageID: <%= image.getImageID() %> | via <%= image.getUser().getUsername() %>" 
					            href="./details.nav?id=<%=  image.getImageID() %>" data-toggle="tooltip" data-placement="right">
					                <img class="image_self" src="travel-images/square-medium/<%= image.getPath() %>">
					            </a>
					        </header>
					        
					        <div class="image_description">
					            <h2><%= image.getTitle() %></h2>
					            <p><%= image.getDescription() %></p>
					               <p class='hei'> 
					               		<span  class="footer-time-left"><%= image.getDateUploadStr() %></span> 
					               </p>
					                
					             
					        </div>
					    </article>
					</div><!-- end col 12 -->
					<% }
					if(images.size() == 0 || request.getAttribute("state").equals("0")){ %>
        	       			<div class="block-center">
	            	   		<p> 该用户未收藏任何图片或该用户已关闭分享收藏功能 </p>
    	           			</div>
					<%	}
               		 } %>
					
					
					<!-- 页码 -->
                            <% 
                            String paramStr ="uid="+request.getParameter("uid");
                            String currentPath = "./userfavourite.pers?"+paramStr;
                            int totalPage = (int)request.getAttribute("totalPage");
                            int currentPage = (int)request.getAttribute("currentPage");

                            int nextPage = (int)request.getAttribute("nextPage");
                            int previousPage = (int)request.getAttribute("previousPage");
                            	System.out.println(totalPage);
                            	System.out.println(currentPage);
                            	System.out.println(nextPage);
                            	System.out.println(nextPage);
                            %>
                            <div class="page_number">
							    <a href="<%= currentPath %>&page=1">第一页</a>
							    <a href="<%= currentPath %>&page=<%= previousPage %>">&lt;&lt;</a>
							    
                            <% if(totalPage <= 6){  //1, 2, 3, 4, 5, 6 
                            	for(int a = 1 ; a <= totalPage; a++){
	                            		if(a == currentPage) {
	                         %>
									    	<a class="active" href="<%= currentPath %>&page=<%= a %>"><%= a %></a>
								<% 		}else{ %>
									    	<a href="<%= currentPath %>&page=<%= a %>"><%= a %></a>
	                            <% 		} //end if else
	                            	} // end for
	                            }// end if totalpage <= 6 
	                           if(totalPage > 6){
		                            boolean hasFirstChara = false;
		                            boolean hasSecondChara = false;
		                            for(int a = 1 ; a <= totalPage; a++){ 
		                            		if(a == currentPage){%>
									    		<a class="active" href="<%= currentPath %>&page=<%= a %>"><%= a %></a>
		                            <%		}else if(a <= 5 || a == totalPage || a == (currentPage-1) || a == (currentPage+1)){ %>
										    	<a href="<%= currentPath %>&page=<%= a %>"><%= a %></a>
		                            <% 		}else if(a > 5 && a < (currentPage-1) && !hasFirstChara){ %>
		                            			<a href="#">.. ..</a>
		                            <%			hasFirstChara = true;
		                            		}else if(a > (currentPage+1) && a < totalPage && !hasSecondChara){ %>
		                            			<a href="#">.. ..</a>
		                            <%		hasSecondChara = true;
		                            		} //end if else
		                            } // end for
	                           } 	%>
								    <a href="<%= currentPath %>&page=<%= nextPage %>">&gt;&gt;</a>
								    <a href="<%= currentPath %>&page=<%= totalPage %>">最后一页</a>
							    <hr>
							</div><!--  end page number -->
					
					
					
				</div><!-- end row -->
			</div>
		</div>

		</section>
		

<script>
$(function () {
    $("[data-toggle='tooltip']").tooltip();
  });
  </script>

</body>
</html>