<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div style="float: right;">
	<a href="home">Home</a>
</div>
<sec:authorize access="hasAnyRole('FACULTY','STUDENT')">
	<div class="row col-lg-12">
		<div class="col-lg-2">
		</div>
		<div class="col-lg-8">
			
			<c:if test="${not empty user}">
						    
				<table class="table table-striped">
					<tr><td>First Name</td><td></td><td>${user.firstname}</td></tr>
					<tr><td>Last Name</td><td></td><td>${user.lastname}</td></tr>
					<tr><td>Gender</td><td></td><td>${user.gender}</td></tr>
					<tr><td>Email Address</td><td></td><td>${user.emailaddress}</td></tr>
					<tr><td>Mobile Number</td><td></td><td>${user.mobileNumber}</td></tr>
					<tr><td></td><td></td><td><a href="users/${user.id}/edit" class="btn btn-default">Edit</a></td></tr>
				</table>
				
			</c:if>
		</div>
		<div class="col-lg-2">
		</div>
	</div>
</sec:authorize>

