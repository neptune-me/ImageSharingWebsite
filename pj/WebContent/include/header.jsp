<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#navbar-001">
				<span class="sr-only">Toggle navigation</span> 
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
		</div>

		<div class="collapse navbar-collapse" id="navbar-001">
			
			<!--left nav bar-->
			<ul class="nav navbar-nav nav_menu">
				<li>
					<a class="navbar-brand" href="#">
						<span class="website_title" title="Pics Store">Unsplash</span>
					</a>
				</li>
				<li><a href="index.nav" class="navbar-brand" id="this_page"> 首页 </a></li>
				<li><a href="search.nav" class="navbar-brand"> 搜索 </a></li>
			</ul>

			<!--right nav account-->
			<% if (session.getAttribute("localuser") != null) { %>
			<ul class="nav navbar-nav nav-pills navbar-right" id="navbar-1">
				<li> 
				 
			  	<form class="bs-example bs-example-form" role="form" method="get" style="margin-top:10px;width:200px" action="./searchUser.pers">
						<div class="input-group input-group-sm">
							<span class="input-group-addon"><span class="glyphicon glyphicon-search"></span></span>
							<input type="text" name="searchInfo" class="form-control" placeholder="Search User">
						</div>
				</form>
				
			 	</li>
				<li class="dropdown"><a class="dropdown-toggle nav-title"
					data-toggle="dropdown" href="#" role="button"> </span> ${localuser.get("username")}<b class="caret"> </b>
				</a> <!--下拉菜单-->
					<ul class="dropdown-menu" id="login_menu">
						<li><a class="green" href="#"><span
								class="glyphicon glyphicon-user"> </span> ${localuser.get("username")}</a></li>
						<li class="divider"></li>
						<li><a href="./upload.pers"><span
								class="glyphicon glyphicon-cloud-upload"> </span> 上传 </a></li>
						<li><a href="./mypics.pers"> <span
								class="glyphicon glyphicon-picture"> </span> 我的图片
						</a></li>
						<li><a href="./myfavourite.pers"> <span
								class="glyphicon glyphicon-star"> </span> 我的收藏
						</a></li>
						<li><a href="./myfriends.pers"> <span
								class="glyphicon glyphicon-comment"> </span> 我的好友
						</a></li>
						<li class="divider"></li>
						<li><a href="./logoutServlet"> <span
								class="glyphicon glyphicon-user"> </span> 登出
						</a></li>
					</ul></li>
			</ul>
			<% } else { %>
			<!--登录图标-->
			<ul class="nav navbar-nav navbar-right" id="navbar-2">
				<li><a href="./login.jsp" role="button"> <span
						class="glyphicon glyphicon-user"> 登录 </span></a></li>
			</ul>
			<% } %>
		</div>

	</nav>