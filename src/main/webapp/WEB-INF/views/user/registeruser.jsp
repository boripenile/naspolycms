<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div style="float: right;"><a href="home">Home</a></div>
<form:form action="users/add"
	method="post" modelAttribute="user" role="form" id="userForm" class="form-horizontal">
	<h3 style="border-bottom: 2px solid green; padding: 10px 10px">User Registration</h3>
 
    <c:if test="${not empty error}">
       <p style="text-align: center;padding: 20px 20px;"><em class="alert alert-danger">${error}</em></p>
    </c:if>
    <div class="form-group col-sm-12">
        <label class="control-label col-sm-2" for="firstname">First Name
			<span class="req">*</span>
		</label>
		<div class="col-sm-4">
			<form:input class="form-control" name="firstname" id="firstnameInput"
				path="firstname" tabindex="1" />
		</div>
		<label class="control-label col-sm-2" for="lastname">Last Name
			<span class="req">*</span>
		</label>
		<div class="col-sm-4">
			<form:input class="form-control" name="lastname" id="lastnameInput"
				path="lastname" tabindex="2" />
		</div>
    </div>  
    <div class="form-group col-sm-12">
        <label class="control-label col-sm-2" for="gender">Gender</label>
		<div class="col-sm-4">
			<form:select class="form-control" path="gender" name="gender"  id="gender">			
				<form:option value="M">MALE</form:option>
				<form:option value="F">FEMALE</form:option>
			</form:select>
		</div>
        <label class="control-label col-sm-2" for="mobileNumber">Mobile Number
			<span class="req">*</span>
		</label>
		<div class="col-sm-4">
			<form:input class="form-control" name="mobileNumber" id="mobileNumberInput"
				path="mobileNumber" tabindex="3" />
		</div>	
    </div> 
    <div class="form-group col-sm-12">
        <label class="control-label col-sm-2" for="emailaddress">Email Address
			<span class="req">*</span>
		</label>
		<div class="col-sm-4">
			<form:input class="form-control" name="emailaddress" id="emailaddressInput"
				path="emailaddress" tabindex="4" />
		</div>
		<label class="control-label col-sm-2" for="userrole">Who are you?</label>
		<div class="col-sm-4">
		    <select class="form-control" name="userrol" id="userrol">
		         <c:if test="${not empty roles}">
					<c:forEach var="role" items="${roles}">
					   <option value="${role.id}">${role.rolename}</option>
					</c:forEach>
				</c:if>
		    </select>
		</div>
    </div>
    
    <div class="form-group col-sm-12">
		
	</div>
	<div class="form-group col-sm-12">
		<label class="control-label col-sm-2" for="password">Password<span
			class="req">*</span></label>
		<div class="col-sm-4">
			<form:input class="form-control" name="password" id="passwordInput" path="password"
				tabindex="5" type="password" />			
		</div>
		<label class="control-label col-sm-2" for="emailaddress">Retype password
			<span class="req">*</span>
		</label>
		<div class="col-sm-4">
		    <input type="password" class="form-control" name="confirmPassword" id="confirmPasswordInput" tabindex="6"
                                   required data-fv-notempty-message="The confirm password is required and cannot be empty"
                                   data-fv-identical="true" data-fv-identical-field="password" data-fv-identical-message="The password and its confirm are not the same" />
		</div>
    </div>
	<div class="form-group col-sm-12">
		<div class="col-sm-offset-2 col-sm-4">
			<button type="submit" class="btn btn-primary col-sm-6" name="save">
				Register Now
			</button>
		</div>
	</div>
</form:form>
<script src="resources/js/register.js" type="text/javascript"></script>