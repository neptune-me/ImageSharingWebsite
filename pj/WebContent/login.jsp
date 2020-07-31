<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Unsplash | 登录</title>
<link href="bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
<link href="css/index_css.css" rel="stylesheet">
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<style>
#pageup_login{
margin:auto;
overflow:hidden;
width:100%;
height:100vh;
background-color:rgb(255, 192, 203);

}
.panel{
width:500px;
margin: auto;
margin-top:120px;
border-color: #e8e8e8;


}
#header_login{
background-color: #e8e8e8;
color:black;
}
.panel .panel-body{


}
</style>
</head>
<body>
<% 
request.setCharacterEncoding("UTF-8");

if(session.getAttribute("localuser") != null){
	String path="./index.jsp";
	response.sendRedirect(path);
}
%>

	
<section id="pageup_login">

<div class="panel panel-default">
		<div class="panel-heading block-center" id="header_login">登录</div>
		<div class="panel-body">
			<form method="post" action="./loginServlet" id="form-register">
			
			<div class="col-xs-12 col-sm-12">
				用户名/电子邮箱：<span id="usernameSpan"> </span><br>
				<input autofocus class="form-control" type="text" name="username" id="username" 
				value ="<%= (request.getParameter("username")==null)? "": request.getParameter("username")%>"
				placeholder="2-20 个字符/address@email.com" required>
			</div>
			
			<div class="col-xs-12 col-sm-12">
				<hr>
			</div>
				
			<div class="col-xs-12 col-sm-12">
				密码：<span id="password1Span"> </span><br>
                <input class="form-control" type="password" id="password1" name="password1"  		
                placeholder="6-20 个字符" required>
			</div>

			<div class="col-xs-12 col-sm-12">
				<hr>
			</div>
				
			<div class="col-xs-12 col-sm-6">
				验证码： <span id="validSpan"> </span><br>
				<input class="form-control" type="text" name="validCode" id="validCode" 	
					value ="<%= (request.getParameter("validCode")==null)? "": request.getParameter("validCode")%>"
				>
			</div>
			
			<div class="col-xs-12 col-sm-6">
				<span></span><br>
				<img src="./ValidImageServlet">
			</div>
			
			<div class="col-xs-12 col-sm-12">
				<hr>
			</div>
				
			<div class="row">
                    <div class="col-xs-3"></div>
                    <div class="col-xs-3 block-center"><a href="./index.nav">回到首页</a></div>
                    <div class="col-xs-3 block-center"><a href="./register.jsp" class="bt2">注册账号</a></div>
                    <div class="col-xs-3"></div>
            </div>	
                <br>
			 <input class="btn btn-default half-length" type="submit" value="登录">
			 <br>
			 <div class="block-center register-false-message"> ${ responseStr } </div>
			</form>
		
		</div>
</div>
</section>
              
</body>
</html>