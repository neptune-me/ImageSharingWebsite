<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Unsplash | 注册成功</title>
<link href="bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
<link href="css/index_css.css" rel="stylesheet">
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

</head>
<body>

	<section id="pageup">
		<div class="panel panel-default">
			<div class="panel-heading">Pics Store | Register Successfully</div>
			<div class="panel-body">
				<form method="post" action="register-submit.jsp" id="form-register">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel">注册成功！</h4>
					</div>
					<div class="modal-body">
						Your User ID :  ${ uidForSubmit }<br>
						Your User Name :  ${ usernameForSubmit }<br>
						Your Password :  ${ passForSubmit }<br> 
						Your Email :  ${ emailForSubmit }<br>
						Your Register Time :  ${ dateJoinedForSubmit }<br>
					</div>
					
	
					<br>
					<hr>
					<a href="login.jsp"> <input class="btn btn-default half-length"
						type="button" id="goToLogin" value="用该账号登录">
					</a>
				</form>
			</div>
		</div>
	</section>

	
</body>
</html>