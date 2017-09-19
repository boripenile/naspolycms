<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<spring:url var="authUrl" value="/static/j_spring_security_check" />
<script type="text/javascript">
	$(function() {
		$(window).resize(
				function() {
					$('.login_container').css(
							{
								position : 'absolute',
								left : ($(window).width() - $(
										'.login_container').outerWidth()) / 2,
								top : ($(window).height() - $(
										'.login_container').outerHeight()) / 2
							});
				});
		// To initially run the function:
		$(window).resize();
	});
</script>
<div class="omb_login">
	<h3 class="omb_authTitle">Login</h3>
	<div class="row omb_row-sm-offset-3">
		<div class="col-xs-12 col-sm-6">
		    <c:if test="${not empty message}">
				<font color="green">${message}</font>
			</c:if>
			<c:if test="${not empty param.error}">
				<font color="red"> Login error. <br />
					Invalid username or password. Try again
				</font>
			</c:if>
			<form class="omb_loginForm" action="${authUrl}" autocomplete="off"
				method="POST">
				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-user"></i></span> <input type="text"
						class="form-control" name="j_username"
						placeholder="Email Address">
				</div>
				<span class="help-block"></span>

				<div class="input-group">
					<span class="input-group-addon"><i
						class="glyphicon glyphicon-lock"></i></span> <input type="password"
						class="form-control" name="j_password"
						placeholder="Password">
				</div>
				<span class="help-block"></span>		
				<span class="help-block"></span>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
			</form>
		</div>
	</div>
</div>
