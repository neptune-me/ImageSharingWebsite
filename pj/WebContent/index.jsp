<%@page import="com.Model.Image"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import=" com.DAO.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Unsplash | 主页</title>
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
if(request.getAttribute("carousel") == null){
	response.sendRedirect("./index.nav");
	
}else{
%>

	

	<section id="pageup">

   		<% ArrayList<Image> carouselImages = (ArrayList<Image>)request.getAttribute("carousel"); %>
   		
   		
	    <!--carousel-->
	    <% //div id="header-pic"%>
	    	<div id="carousel-example-generic" class="carousel slide header-pic" data-ride="carousel">
	    		<!-- Indicators -->
    			<ol class="carousel-indicators">
			        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
				<% for(int i = 1 ; i<carouselImages.size(); i++){ %>
			        <li data-target="#carousel-example-generic" data-slide-to="<%= i %>"></li>
				<% } %>    			
    			</ol>
	    	    <!--轮播项目-->
    		    <div class="carousel-inner" role="listbox">
    		    <% for( int i = 0 ; i<carouselImages.size();i++) { %>
    		    	<div class="item <% if(i==0) {%> active <% } %>">
    		    		<a href="./details.nav?id=<%= carouselImages.get(i).getImageID() %>" data-toggle="tooltip" 
			               data-placement="top" title="ImageID: <%= carouselImages.get(i).getImageID() %> ">
    		    			<img src="travel-images/large/<%= carouselImages.get(i).getPath() %>" alt="00<%= (i+1) %>">
    		    		</a>
    		    		<div class="carousel-caption">
    		    			<%= carouselImages.get(i).getTitle()+": <br>" %>
    		    			
    		    			<%= carouselImages.get(i).getDescription() %>
    		    		</div>
   		    		</div>
    		    <% } %>
    		    
    		    </div>
	    		
	    		<!-- Controls -->
    			<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
			        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
			        <span class="sr-only">Previous</span>
    			</a>	
			    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
			        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			        <span class="sr-only">Next</span>
			    </a>
	    	
	    	</div>
	    <%//div> %>
	
	    <!--images-->
	    <div class="pics_windows">
	        <!-- 1pic/article; 3pics/row; 2rows-->
	        <div class="row">
	        
 			<% List<Image> showImages= (List<Image>)request.getAttribute("show"); %>
	    	  
	     	<% for(Image i : showImages){ %>
	      		<div class="col-lg-3 col-sm-4 col-xs-6">
			    	<article>
			        <div class="image_container">
			            <a href="./details.nav?id=<%= i.getImageID() %>" data-toggle="tooltip" 
			            	data-placement="top" title="ImageID: <%= i.getImageID() %> ">
			                <img src="travel-images/square-medium/<%= i.getPath() %>" class="image_self"></a>
			        </div>
			        <header class='image_description'>
			            <p class='h3 index-latest-img-title' data-toggle='tooltip' data-placement="top" title="<%= i.getTitle() %>">
			                <%= i.getTitle() %>
			            </p>
			            <p class='hei' data-toggle='tooltip' data-placement='top' title="<%= i.getUser().getUsername() %>">
				               	作者： <%= i.getUser().getUsername() %>
				               <span class="footer-time"><%= i.getDateUploadStr() %></span>
			            </p>
			        </header>
			
			    	</article>
				</div>
	      		
	      		
	      	<% } %>
			</div>
	    </div>
	</section>





<script>$(function () {
        $("[data-toggle='tooltip']").tooltip();
    });
</script>
<% } %>
</body>
</html>