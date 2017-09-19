<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="col-lg-12 col-sm-12">
		<div class="omb_login">
			<div class="row col-lg-12 col-sm-6 omb_socialButtons">
				<div class="col-xs-12 col-sm-6">
					<a href="#" class="btn btn-lg btn-block omb_btn-facebook"> <i
						class="fa fa-facebook visible-xs"></i> <span class="hidden-xs">Facebook
							Login</span>
					</a>
				</div>

				<div class="col-xs-12 col-sm-6">
					<a href="#" class="btn btn-lg btn-block omb_btn-google"> <i
						class="fa fa-google-plus visible-xs"></i> <span class="hidden-xs">Google
							Login</span>
					</a>
				</div>
			</div>
            <span class="help-block"></span>
			<div class="row col-lg-12 col-sm-12 omb_loginOr">
				<div class="col-xs-12 col-sm-12">
					<hr class="omb_hrOr">
					<span class="omb_spanOr">or</span>
				</div>
			</div>
            <span class="help-block"></span>
			<div class="row col-lg-12 col-sm-12">
				<div class="col-xs-12 col-sm-12">
					<form class="omb_loginForm" action="" autocomplete="off"
						method="POST">
						<div class="form-group">
							<input type="text" class="form-control col-xs-12 col-sm-12" name="username"
								placeholder="Email">
						</div>
						<span class="help-block"></span>

						<div class="form-group">
							<input type="password" class="form-control col-xs-12 col-sm-12" name="password"
								placeholder="Password">
						</div>
						<span class="help-block"></span>
						<button class="btn btn-lg btn-primary btn-block col-xs-12 col-sm-12" type="submit">SIGN
							IN</button>
					</form>
				</div>
				<div class="row col-lg-12 col-sm-12">
					<div class="col-xs-6 col-sm-6">
						<p class="omb_forgotPwd">
							<a href="#">Forgot password?</a>
						</p>
					</div>
					<div class="col-xs-6 col-sm-6">
						<a href="http://tailord2me.com/register" class="btn btn-default">Create
							New Account</a>
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>