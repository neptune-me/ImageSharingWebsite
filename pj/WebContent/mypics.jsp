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
<title>Unsplash | </title>
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
}
if(request.getAttribute("images") == null){
	System.out.println("null images");
	response.sendRedirect("./myfavourite.pers");
}
%>

	<section id="pageup">

	<!-- left -->
		<aside class="col-sm-3 col-xs-12 left_aside" id="left_aside">
				 
				 
				 <!-- 左邊邊欄的row佈局 -->
            <div class="row">
            	<!-- 左上角用戶框 -->
                <div class="col-xs-12">
           		    <table class="table table-bordered table-hover">
	                    <thead><tr><th>Local User</th></tr></thead>
	                    <tbody>
	                        <tr><td>
	                            <div  class="block-center">
	                        	<span class="glyphicon glyphicon-user" style="font-size:50px"> </span> 
            	<% 
            	Map<String,String> localuser = null;
            	if(session.getAttribute("localuser") != null){
            		localuser = (Map<String,String>)session.getAttribute("localuser"); 
           		%>
           						<span class="username-search">
	                        	${ localuser.get("username") }
	                        	</span>
	                            <hr/>
	                            </div><!-- end center block -->
	                            </td></tr>
	                            <div><!-- end 多余的div -->
	                            <tr><td>
	                            Email: ${ localuser.get("email") }
	                            </td></tr>
	                            <tr><td>
	                            UserId:  ${ localuser.get("uid") }
	                            </td></tr>
	                            
	                            <tr><td>
	                            State: ${ localuser.get("state") }
	                            </td></tr>
	          
	                            <tr><td><!-- end 多余的 -->
	                           <a href="./myfavourite.pers" class="full-length btn btn-default">
	                            		我的收藏
	                            </a>
	                            <a href="./mypics.pers" class="full-length btn btn-default">
	                            		我的图片
	                            </a>
	                            <a href="./myfriends.pers"  class="full-length btn btn-default">
	                            		我的好友	
	                            </a>
	                            
	                             <a href="./mymessage.pers"  class="full-length btn btn-default">
	                            		我的消息	
	                            </a>
	                            
            	<% }else{ %>
	                        	<p> <a href='./login.jsp' class=''>未登录</a></p>
	                            <hr/>
	                            <h6><a href='./login.jsp' class=''>点击登录</a></h6>
								

				<% } %>
	                            </div><!-- d多余的div 为了end block-center -->
	                        </td></tr><!-- 多余的 end第一个trtd -->
	                    </tbody>
                    </table>
                    </div><!-- end table外的col-12 -->
            		
            		
            		
            		
<% 
if(session.getAttribute("myTrack") != null){
	System.out.println("==================================my Strack========================================");
MyTrack myTrack = (MyTrack)session.getAttribute("myTrack");
StringBuffer str = new StringBuffer();
for(int  i =0 ; i < myTrack.getLengthOfId();i++){
    str.append(myTrack.byIndex(i));
}
%>
					<div class="col-xs-12">
						<table class="table table-bordered table-hover">
							<thead><tr>
                				<th> <sapn class="glyphicon glyphicon-transfer"></sapn> User Track</th>
            				</tr></thead>
							<tbody>
					                <%
					               out.println(str);
					               %>
					        </tbody>
						
						</table>
					</div>
					                <%
}
					               %>

			</div><!-- end 左邊邊欄内部row -->
		</aside>
			
	
	
	
	
	
	<!-- 右边 -->
	<div class="col-sm-9 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-heading">我的上传</div>
			<div class="panel-body">
				<div class="row">
	               <input type="text" id="localuser_uid" value="${ localuser.get("uid") }" disabled style="display:none">
					<%
					 ArrayList<Image> images = (ArrayList<Image>)request.getAttribute("images");
					if(images != null){
						for(Image image:images){
					%>

					<div class="col-xs-12">
					    <article class="left" id="article_<%= image.getImageID() %>">
					        <div class="image_container">
					            <a title="ImageID: <%= image.getImageID() %> | via <%= image.getUser().getUsername() %>" 
					            href="./details.nav?id=<%=  image.getImageID() %>" data-toggle="tooltip" data-placement="right">
					                <img class="image_self" src="travel-images/square-medium/<%= image.getPath() %>">
					            </a>
					        </div>
					        <div class="col-xs-6">
					        <div class="image_description_search">
					            <h2><%= image.getTitle() %></h2>
					            <p><%= image.getDescription() %></p>
					               <p class='hei'> 
					               <span  class="footer-time-left"><%= image.getDateUploadStr() %></span> </p>
					                <a href="./upload.pers?iid=<%= image.getImageID() %>" class="btn btn-default">
			                			<span class="glyphicon glyphicon-edit"></span> <span> Modify</span>
					                </a>
  
					                <!-- ->a href="./delete.do?iid=<-%= image.getImageID() %>"-->
						                <button class="white-button btn btn-default" name="" value="<%= image.getImageID() %>"
						                data-toggle="modal" data-target="#myModal_<%= image.getImageID() %>">
					                		<span class="glyphicon glyphicon-trash"></span> <span> Delete</span>
					                	</button>
					                <!--  ->/a-->
					        </div>
					        </div><!-- end col6 -->
					    </article>
					</div><!-- end col 12 -->
					
					
					
					
					
				
<div class="modal fade" id="myModal_<%= image.getImageID() %>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				
			</div>
			<div class="modal-body">
				确定要删除照片吗？
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" 
						data-dismiss="modal">关闭
				</button>
				 <button class="btDelete white-button btn btn-default" name="deleteUpload" value="<%= image.getImageID() %>"
						                data-toggle="modal" data-target="#myModal_<%= image.getImageID() %>">
					                		<span class="glyphicon glyphicon-trash"></span> <span> Delete</span>
					                	</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
					
					
					
					<% }
					if(images.size() == 0){ %>
        	       			<div class="block-center">
	            	   		<p> 没有符合找到要求的图片 </p>
    	           			</div>
					<%	}
               		 } %>
					
					
					<!-- 页码 -->
                            <% 
                            String paramStr ="=";
                            String currentPath = "./mypics.pers?"+paramStr;
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
	</div><!-- 右边 -->
		</section>
		
</body>
<script src="./js/delete_upload.js"></script>
</html>