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
<title>Unsplash | 上传图片</title>
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

if(request.getParameter("iid")!= null && request.getAttribute("image") == null){
	System.out.println("null images");
	response.sendRedirect("./upload.pers?iid="+request.getParameter("iid"));
}else if(request.getParameter("iid")!= null && request.getAttribute("image") == null){
	
}
if(request.getAttribute("countryMap") == null){
	request.getRequestDispatcher("./upload.pers").forward(request, response);
}
%>


	
<section id="pageup">

	 <div class="panel panel-default">
	 
	 	<div class="panel-heading">上传</div>
	 	 <div class="panel-body">
	 	 	<div class="row">
	 	 		
	 	 		  <div class="col-xs-12">
	 	 		  <% 
	 	 		  if(request.getParameter("iid")!= null){
	 	 			  int iid = Integer.parseInt(request.getParameter("iid"));
	 	 			  Image image = (Image)request.getAttribute("image");
	 	 			  Map<String, String> localuser = (Map<String,String>)session.getAttribute("localuser");
	 	 			  //if(image.getUser().getUserID() != Integer.parseInt(localuser.get("uid")))
	 	 				//  response.sendRedirect("./upload.pers");
	 	 			  
	 	 			  %>
	 	 			  
	 	 			  
	 	 			  
	 	 			  <form method="get" id="upload_form" class="upload-form" enctype="multipart/form-data" action="./modify.do?iid=<%= request.getParameter("iid") %>">
	                        <div class="upload_file">
								<input type="text" style="display:none" name="iid" value="<%= image.getImageID() %>">
								
	                            <input type="file" style="display: none" id="upload_file" name="path" accept="image/*" disabled><br>
	                            
	                            <div id="image_div">
	                            	<img src="./travel-images/large/<%= image.getPath() %>" class="image-show">
	                            </div>
	                            <span id="pathSpan"> </span>
	                           
	
	                        </div>
	                        <hr/>
	                        <label for="title">Image title</label> <span id="titleSpan"> </span> <br>
	                        <input class="full-length form-control" type="text" class="normal" id="title" name="title"
	                                value="<%= image.getTitle() %>" onblur=checkTitle()><br>
	
	                        <label for="textarea">Image description</label> <span id="descriptionSpan"> </span> <br>
	                        <textarea class="full-length form-control" id="description" name="description" class="normal"
	                                  rows="5"  onblur=checkDescription()><%= image.getDescription() %></textarea><br>
	
	                        <div class="row">
	                            <div class="col-xs-4"><label for="country">Country</label> <span id="countrySpan"> </span> <br>
	                                <select class="form-control" name="countryCodeISO" id="select_country"
	                                         onblur=checkCountry()>
	                                    <option value="<%= image.getCountryCodeISO() %>" id="default_country"><%= image.getiGeo().getCountry() %></option>
	                                    
	                                    <% Map<String,String> countryMap = (Map<String,String>)request.getAttribute("countryMap");
	                            		
	                                    	for(Map.Entry<String, String> entry : countryMap.entrySet()){
	                                    		String key = entry.getKey();
	                                    		String value = entry.getValue();
	                                    		
	                                    	%><option value="<%= key %>"> <%= value %></option><%
	                                    	}
	                                    %>
	                                  
	
	                                </select>
	                            </div>
	
	                            <div class="col-xs-4">
	                                <label for="city">City</label> <span id="citySpan"> </span> <br>
	                                <!--选择城市，并与国家二级联动-->
	                                <select class="form-control" name="cityCode" id="select_city" onblur=checkCity()>
	                                    <option value="<%= image.getCityCode() %>" id="default_city"><%= image.getiGeo().getCity() %></option>
	                                </select>
	                            </div>
	
	                            <div class="col-xs-4">
	                                <!--内容选择 Scenery City People Animal Building  Wonder Other -->
	                                <label for="content">Content</label> <span id="contentSpan"> </span> <br>
	                                <select class="select_content form-control" name="content" id="select_content" onblur=checkContent()>
	                                    <option value="<%= image.getContent() %>" id="default_content" selected><%= image.getContent() %></option>
	                                    <option id="0" value="Scenery">Scenery</option>
	                                    <option id="1" value="City">City</option>
	                                    <option id="2" value="People">People</option>
	                                    <option id="3" value="Animal">Animal</option>
	                                    <option id="4" value="Building">Building</option>
	                                    <option id="5" value="Wonder">Wonder</option>
	                                    <option id="6" value="Other">Other</option>
	                                </select>
	                            </div>
	                        </div>
	
	                        <!--input class="full-length form-control" type="text" class="normal" name="country"><br-->
	                        <!--input class="full-length form-control" type="text" class="normal" name="city"><br-->
	                        <!--input class="full-length form-control" type="text" class="normal" name="content"><br-->
	                        <hr/>
	                        <button type="submit" class="btn btn-default" >
	                        	<span class="glyphicon glyphicon-save"></span> 保存修改
	                        </button>
	                    </form>
	 	 			 
	 	 		  <% } else{ %>
	 	 				<form method="get" id="upload_form" class="upload-form" enctype="multipart/form-data"  action="./upload.do">
	                        <div class="upload_file">
	
	                            <input type="file" style="display: none" id="upload_file" name="path" accept="image/*"><br>
	                            <div id="image_div">图片未上传</div>
	                            <input type="button" class="btn btn-default" id="upload_button" onclick="input.click();"
	                                   value="上传图片" onblur=checkPath() ><span id="pathSpan"> </span>
	
	                        </div>
	                        <hr/>
	                        <label for="title">Image title</label> <span id="titleSpan"> </span> <br>
	                        <input class="full-length form-control" type="text" class="normal" id="title" name="title"
	                                onblur=checkTitle()><br>
	
	                        <label for="textarea">Image description</label> <span id="descriptionSpan"> </span> <br>
	                        <textarea class="full-length form-control" id="description" name="description" class="normal"
	                                  rows="5"  onblur=checkDescription()></textarea><br>
	
	                        <div class="row">
	                            <div class="col-xs-4"><label for="country">Country</label> <span id="countrySpan"> </span> <br>
	                                <select class="form-control" name="countryCodeISO" id="select_country"
	                                         onblur=checkCountry()>
	                                    <option value="" id="default_country">---</option>
	                                    
	                                    
	                                     <% Map<String,String> countryMap = (Map<String,String>)request.getAttribute("countryMap");
	                            		
	                                    	for(Map.Entry<String, String> entry : countryMap.entrySet()){
	                                    		String key = entry.getKey();
	                                    		String value = entry.getValue();
	                                    		
	                                    	%><option value="<%= key %>"> <%= value %></option><%
	                                    	}
	                                    %>
	                                    
	
	                                </select>
	                            </div>
	
	                            <div class="col-xs-4">
	                                <label for="city">City</label> <span id="citySpan"> </span> <br>
	                                <!--选择城市，并与国家二级联动-->
	                                <select class="form-control" name="cityCode" id="select_city" onblur=checkCity()>
	                                    <option value="" id="default_city" selected>---</option>
	                                </select>
	                            </div>
	
	                            <div class="col-xs-4">
	                                <!--内容选择 Scenery City People Animal Building  Wonder Other -->
	                                <label for="content">Content</label> <span id="contentSpan"> </span> <br>
	                                <select class="select_content form-control" name="content" id="select_content" onblur=checkContent()>
	                                    <option value="" id="default_content" selected>---</option>
	                                    <option id="0" value="Scenery">Scenery</option>
	                                    <option id="1" value="City">City</option>
	                                    <option id="2" value="People">People</option>
	                                    <option id="3" value="Animal">Animal</option>
	                                    <option id="4" value="Building">Building</option>
	                                    <option id="5" value="Wonder">Wonder</option>
	                                    <option id="6" value="Other">Other</option>
	                                </select>
	                            </div>
	                        </div>
	
	                        <!--input class="full-length form-control" type="text" class="normal" name="country"><br-->
	                        <!--input class="full-length form-control" type="text" class="normal" name="city"><br-->
	                        <!--input class="full-length form-control" type="text" class="normal" name="content"><br-->
	                        <hr/>
	                    </form>
	 	 			
	 	 			
	 	 			
	 	 			
	 	 			
	 	 		  <% } %>
		 	 		
	 	 			</div>
	 	 
	 	 	</div><!-- end row -->
	 	 </div><!-- emd pane body -->
	 
	 </div><!-- end pane -->


</section>
<script src="./js/upload.js"></script>
</body>
</html>