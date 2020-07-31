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
<title>Pics Store | 搜索</title>
<link href="bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
<link href="css/index_css.css" rel="stylesheet">
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<%@include file="include/header.jsp"%>
<%@include file="include/float_button.jsp"%>
</head>
<body>

<% 

request.setCharacterEncoding("UTF-8");
if(request.getAttribute("images") == null){
	response.sendRedirect("./search.nav");
	
}else{
	try{
		
	
%>

	
<section class="main_window" id="pageup">
    <div class="row">
        <!--左侧边栏，包括一个搜索栏和三个热门栏-->
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
		</aside><!-- end 左邊邊欄 -->
		
		
	 	<!--右侧内容区，包括filter和图片结果显示-->
        <div class="col-sm-9 col-xs-12">
            <div class="row">
                <div class="col-xs-12">
                    <!--筛选器-->
                    <div class="panel panel-default">
                        <div class="panel-heading"><span class="glyphicon glyphicon-filter"></span> Filter</div>
                        <div class="panel-body">
                            <form method="get" action="./search.nav">
                                <div class="row">
                       	<% 
                    	String filter_condi = (request.getParameter("filter-condi") == null)?"title":request.getParameter("filter-condi").toLowerCase();
                       	String input_cont = (request.getParameter(filter_condi)== null)?"":request.getParameter(filter_condi);

                       	String order_way = (request.getParameter("order-way") == null)?"DateUpload":request.getParameter("order-way");
                       	String order = (request.getParameter("order")== null)?"ASC":request.getParameter("order");

                       	String show_way = (request.getParameter("show-way")==null)?"mess":request.getParameter("show-way");
                    	
               			
               			String f_condi_1 = filter_condi.equals("title")?"selected":"";
               			String f_condi_2 = filter_condi.equals("description")?"selected":"";
               			String f_condi_3 = filter_condi.equals("content")?"selected":"";
               			
               			String o_way_1 = order_way.equals("DateUpload")?"checked":"";
               			String o_way_2 = order_way.equals("Likes")?"checked":"";
               			
               			String l_t_h = order.equals("ASC")?"checked":"";
               			String h_t_l = order.equals("DESC")?"checked":"";
               			
               			String s_way_1 = show_way.equals("mess")?"checked":"";
               			String s_way_2 = show_way.equals("pics")?"checked":"";
                       	%>

                                    <!--筛选条件-->
                                    <div class="col-xs-12">
                                   	<label for="filter-condi">筛选条件：</label>
                                    </div>
                                    <div class="col-xs-3">
                                        <select class="full-length form-control" name="filter-condi" id="filter-condi">
                                            <option value="Title" <%= f_condi_1 %>>根据标题搜索</option>
                                            <option value="Description" <%= f_condi_2 %>>根据介绍搜索</option>
                                            <option value="Content" <%= f_condi_3 %>>根据内容搜索</option>
                                        </select>
                                    </div>
                                
                             
                                    
                                    <div class="col-xs-6">
                                    	<input class="full-length form-control" type="text" name="title" id="search_text"
                                           placeholder="Search by Title">
                                    </div>
                                    <script defer>
                                    var condi = document.getElementById("filter-condi");
                                    condi.onchange = function(){
                                    	var inputBlock = document.getElementById("search_text");
                                    	var condiStr = condi.value;
                                    	inputBlock.setAttribute("placeholder","Search By "+condiStr);
                                    	inputBlock.setAttribute("name",condiStr.toLowerCase());
                                    }
                                    window.onload = function(){
                                    	var inputBlock = document.getElementById("search_text");
                                    	var condiStr = condi.value;
                                    	inputBlock.setAttribute("placeholder","Search By "+condiStr);
                                    	inputBlock.setAttribute("name",condiStr.toLowerCase());
                                    }

                                    
                                    document.getElementById("search_text").value = "<%= input_cont %>";
                                    </script>

                                    <div class="col-xs-3">
                                    	 <input class="full-length btn btn-default" type="submit" id="go" value="Go">
                                    </div>
									<div class="col-xs-12"><hr/></div>

                                    
									<!-- 排序方式 -->
									<div class="col-xs-6"> <label for="order-way">排序方式：</label> </div>
									<div class="col-xs-6"> <label for="show-way">显示方式：</label> </div>

                                    <div class="col-xs-3">
                                    	<div type="button" class="btn btn-default" id="for_DateUpload">
											<span class="glyphicon glyphicon-time"></span>
	                                    	<input class="" id="DateUpload" name="order-way" type="radio"  <%= o_way_1 %> value="DateUpload" style="display:none">
                                    	按时间排序
										</div>
                                    	<div type="button" class="btn btn-default" id="for_Likes">
                                    		<span class="glyphicon glyphicon-heart"></span>
	                                    	<input class="" id="Likes" name="order-way"  type="radio" <%= o_way_2 %> value="Likes" style="display:none">
                                    	按热度排序
										</div>
                                    	
                                        
                                    </div>
                                   
                                    <div class="col-xs-3">
                                    	<div type="button" class="btn btn-default" id="for_ASC">
											<span class="glyphicon glyphicon-sort-by-attributes"></span>
	                                    	<input class="" id="ASC" name="order" type="radio" <%= l_t_h %> value="ASC" style="display:none">
	                                    	由低到高
										</div>
                                    	<div type="button" class="btn btn-default" id="for_DESC">
                                    		<span class="glyphicon glyphicon-sort-by-attributes-alt"></span>
	                                    	<input class="" id="DESC" name="order"  type="radio" <%= h_t_l %> value="DESC" style="display:none">
	                                    	由高到低
										</div>
                                    		
                                    </div>
                                   
                                   <div class="col-xs-3">
                                   		<div type="button" class="btn btn-default" id="for_mess">
											<span class="glyphicon glyphicon-th-list"></span>
											<input class="" id="mess" name="show-way" type="radio" <%= s_way_1 %> value="mess" style="display:none">
                                    	信息列表
										</div>
                                    	<div type="button" class="btn btn-default" id="for_pics">
                                    		<span class="glyphicon glyphicon-th"></span>
                                    		<input class="" id="pics" name="show-way"  type="radio" <%= s_way_2 %> value="pics" style="display:none">
                                    	图片平铺
										</div>
                                        
                                    </div>

                                      

                                </div>
                            </form>

                        </div>
                    </div>
                </div>

                <div class="col-xs-12">
                    <!--选项结果显示区-->
                    <div class="panel panel-default">
                        <div class="panel-heading">
			                            搜索结果
                        </div>
                        <div class="panel-body">
                            <div id="images" class="row">
                            	
                    <%  
                    ArrayList<Image> images = (ArrayList<Image>)request.getAttribute("images");
                   	for(Image i:images){
                    	if(show_way.equals("mess")){ 
                    %>
										<div class="col-xs-12">
										    <article class="left">
										        <div class="image_container">
										        	<a title="" href="./details.nav?id=<%= i.getImageID() %>" data-toggle="tooltip" data-placement="right" 
										        	data-original-title="ImageID: <%= i.getImageID() %> | via <%= i.getUser().getUsername() %>">
											             <img class="image_self" src="travel-images/square-medium/<%= i.getPath() %>">
										            </a>
										        </div>
										        
										        <div class="image_description_search col-xs-6">
										            <h2><%= i.getTitle() %></h2>
										            <p><%= i.getDescription() %></p>
										            <p style="text-align:right" class="red"><span class="glyphicon glyphicon-thumbs-up"></span> <%= i.getLikes() %></p>
											         <p class='hei' style="text-align:right"> <span style="text-align:right" class="footer-time-left"><%= i.getDateUploadStr() %></span> </p>
										        </div>
										        
										    </article>
										</div>	
								
								
					<%	}else if(show_way.equals("pics")){ 
					%>
									<div class="col-xs-3 col-sm-3 images-container-square">
									    <a title="" href="./details.nav?id=<%= i.getImageID() %>" 
									    data-original-title="<%= i.getTitle() %> | via <%= i.getUser().getUsername() %>" data-toggle="tooltip">
									        <img src="./travel-images/square-medium/<%= i.getPath() %>">
									    </a>
									</div>
					<%  }
					} if(images.size() == 0){%>
                               		<div class="block-center">
                               		<p> 没有符合找到要求的图片 </p>
                               		</div>
                  	<% } 
					String paramStr = "filter-condi="+filter_condi+"&"+filter_condi.toLowerCase()+"="+input_cont
                     				+"&order-way="+order_way+"&order="+order+"&show-way="+show_way;
                  	int i = (Integer)(request.getAttribute("totalPage")); 
                  	%>
                            </div><!-- end images -->
                            
                            
                            <!-- 页码 -->
                   <% 
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
							    
							</div><!--  end page number -->

                        </div>
                    </div>
                </div>
                
                
            </div><!-- end row -->
        </div><!-- end pane -->
	</div><!-- end 全局row -->
</section>
	

</body>
<script>
var filter_condi =  "<%= filter_condi%>";
var input_content = "<%= input_cont %>";
var order_way = "<%= order_way %>";
var order = "<%= order %>";
var show_way = "<%= show_way %>";

</script>
<script src="./js/doPage.js"></script>


<% 
	}catch(Exception e){
		System.out.println("error");
		e.printStackTrace();
		response.sendRedirect("./search.nav");
		
	}
} %>
 <script type="text/javascript" src="./js/search.js"></script>
</body>
</html>