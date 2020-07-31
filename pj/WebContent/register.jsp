<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pics Store | Register</title>
<link href="bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
<link href="css/index_css.css" rel="stylesheet">
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<style>
#pageup_register{
margin:auto;
overflow:hidden;
width:100%;
height:100vh;
background-color:rgb(42, 59, 66);

}
.panel{
width:800px;
margin: auto;
margin-top:40px;
border-color: #e8e8e8;


}
#header_login{
background-color: #e8e8e8;
color:black;
}
.panel .panel-body div{
margin:5px auto;

}
</style>


</head>
<body >
<% 
request.setCharacterEncoding("UTF-8");

%>
	<section id="pageup_register">
	<div class="panel panel-default">
		<div class="panel-heading block-center" id="header_login">Pics Store | Register</div>
		<div class="panel-body">
	
	
			<form method="post" action="./registerServlet" id="form-register">
			<div class="col-xs-12 col-sm-10">
				User name:<span id="usernameSpan"> </span><br>
				<input autofocus class="form-control" type="text" name="username" id="username" onblur="checkuser();" 
				value ="${param.username }"
				placeholder="4-15 characters">
			</div>


			<div class="col-xs-12 col-sm-10">
				Password:<span id="password1Span"> </span><br>
                <input class="form-control" type="password" id="password1" name="password1" onblur="checkpassword1();" 
                	value ="<%= (request.getParameter("password1")==null)? "": request.getParameter("password1")%>"
                placeholder="6-12 characters">
				<div class="left" style="margin-top:-5px">
				<div class="progress progress-bar" id="passStrength" >
	    			
				</div>
				</div>
				
			</div>
				
			
			<div class="col-xs-12 col-sm-10">
				Confirm Password:<span id="password2Span"> </span><br>
                <input class="form-control" type="password" id="password2" name="password2" onblur="checkpassword2();" 
                	value ="<%= (request.getParameter("password2")==null)? "": request.getParameter("password2")%>"
                placeholder="6-12 characters">
			</div>
			
			

			<div class="col-xs-12 col-sm-10">
				Email Address:<span id="mailSpan"> </span><br>
				<input class="form-control" type="text" name="mail" id="mail" onblur="checkmail();"	
					value ="<%= (request.getParameter("mail")==null)? "": request.getParameter("mail")%>"
				placeholder="address@email.com">
			</div>
			
                <br>
                               
			<div class="col-xs-12 col-sm-6">
				Valid Code: <span id="validCodeSpan"> </span><br>
				<input class="form-control" type="text" name="validCode" id="validCode" onblur="checkValidCode();"
					value ="<%= (request.getParameter("validCode")==null)? "": request.getParameter("validCode")%>"
				placeholder="Input valid code">
			</div>
			<div class="col-xs-12 col-sm-4">
			<span></span><br>
				<img src="./ValidImageServlet">
			</div>
				
			
				<br>	
			<div class="col-xs-12">
			<hr>
			</div>

			<div class="col-xs-12">
                <div class="row">
                    <div class="col-xs-3"></div>
                    <div class="col-xs-3 block-center"><a href="./index.nav">Home</a></div>
                    <div class="col-xs-3 block-center"><a href="./login.jsp" class="bt2">Login</a></div>
                    <div class="col-xs-3"></div>
                </div>	
                <br>
                <input class="btn btn-default half-length" type="submit" value="Register"/>
                <br>
                <div class="block-center register-false-message">
                	${ responseStr }
                </div>
               </div>
			</form>
	
	
		
		
		</div><!-- end panel-body -->
	</div><!-- end panel -->
	</section>



var form = document.getElementById("form-register");
form.onsubmit = function() {

	return checkSubmit();
}




</script>
<script src="js/register-ver2.js"></script>
</html>