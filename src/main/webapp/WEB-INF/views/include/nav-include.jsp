<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav"
	role="banner">
	<div class="container" >
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".bs-navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#"><span class="glyphicon glyphicon-home"></span></a>
		</div>
		<div class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
			<!--Sidebar main page navigation list here-->
			<ul class="nav navbar-nav">				
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false"><span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" role="menuitem"
								tabindex="-1"></a></li>
							<li class="divider"></li>
											
						</ul></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">				
				<c:if test="${pageContext.request.userPrincipal.name != null}">
						<li class="dropdown pull-right"><a href="#"
							data-toggle="dropdown" id="dropdownMenu4"><span
								class="glyphicon glyphicon-user"></span>&nbsp;Welcome&nbsp;${pageContext.request.userPrincipal.name}&nbsp;<span
								class="caret"></span></a>
							<ul class="dropdown-menu" role="menu"
								aria-labelledby="dropdownMenu4">
								
								<li><a href="users/view" tabindex="-1">My Profile</a></li>				 					      					
								<li><a href="<c:url value='/static/j_spring_security_logout'/>" tabindex="-1">Log Out&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="glyphicon glyphicon-off"></span></a></li>
							</ul></li>
					</c:if>
					<c:if test="${pageContext.request.userPrincipal.name == null}">
						<li><a href="users/new" role="menuitem"
							>Register&nbsp;&nbsp;</a>
							</li>
						<li><a href="login" role="menuitem"
							>Login&nbsp;&nbsp;</a>
							</li>
					</c:if>		
			</ul>
		</div>
	</div>
</header>