<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div style="float: right;">
	<a href="home">Home</a>
</div>
<sec:authorize access="hasAnyRole('FACULTY','STUDENT')">
	<div class="row col-lg-12">
		<div class="col-lg-2"></div>
		<div class="col-lg-8">
			<c:if test="${not empty user}">
				<form:form action="users/update" method="post" modelAttribute="user"
					role="form" id="userForm" class="form-horizontal">
					<form:hidden path="id" />
					<div class="form-group">
						<label class="control-label col-sm-4" for="emailaddress">Email
							Address </label>
						<div class="col-sm-6">
							<label class="control-label col-sm-6" >${user.emailaddress}</label>
						</div>
					</div>					
					<div class="form-group">
						<label class="control-label col-sm-4" for="firstname">First
							Name <span class="req">*</span>
						</label>
						<div class="col-sm-6">
							<form:input class="form-control" name="firstname"
								id="firstnameInput" path="firstname" tabindex="1" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="lastname">Last
							Name <span class="req">*</span>
						</label>
						<div class="col-sm-6">
							<form:input class="form-control" name="lastname"
								id="lastnameInput" path="lastname" tabindex="2" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="gender">Gender</label>
						<div class="col-sm-6">
							<form:select class="form-control" path="gender" name="gender"
								id="gender">
								<form:option value="M">MALE</form:option>
								<form:option value="F">FEMALE</form:option>
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="mobileNumber">Mobile
							Number <span class="req">*</span>
						</label>
						<div class="col-sm-6">
							<form:input class="form-control" name="mobileNumber"
								id="mobileNumberInput" path="mobileNumber" tabindex="3" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-6">
							<button type="submit" class="btn btn-primary col-sm-6"
								name="save">Update</button>
						</div>
					</div>
				</form:form>
				<script src="resources/js/register.js" type="text/javascript"></script>
			</c:if>
		</div>
		<div class="col-lg-2"></div>
	</div>
</sec:authorize>

