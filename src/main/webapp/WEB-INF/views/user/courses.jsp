<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div style="float: right;"><a href="home">Home</a></div>
<sec:authorize access="hasRole('FACULTY')">
	<div class="row col-lg-12">
		<div class="col-lg-3">
			<center>
				<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Registered
					Courses</h4>
			</center>

			<a href="courses/register">Register
				Course</a><br /> <br />
			<c:if test="${not empty mycourses}">			
					<c:forEach var="course" items="${mycourses}">
					   <div class="list-box">
					      <span>${course.course.courseName}</span>
					      <span><a href="courses/${course.id}/edit"
							style="color: #000;padding: 5px;">edit</a></span>
						  <span><a href="courses/${course.id}/students"
							style="color: #000;padding: 5px;">students</a></span>					       
					   </div>
					</c:forEach>				
			</c:if>
			<br /> <a href="courses/register">Register
				Course</a>
		</div>
		<div class="col-lg-6">
			<c:if test="${not empty coursestudents}">
				<center>
						<h4 style="border-bottom: 2px solid green; padding: 10px 10px">List of Students enrolled for ${course.course.courseName}</h4>
				</center>
				<table class="table table-striped" id="table">
				<thead>
					<tr>
						<td>S/N</td>
						<td>Name</td>
						<td>Email address</td>
						<td></td>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty coursestudents}">
						<% int sn = 0; %>
						<c:forEach var="student" items="${coursestudents}">
							<tr>
								<td><%= ++sn %></td>
								<td>${student.firstname} ${student.lastname}</td>
								<td>${student.emailaddress}</td>								
							</tr>
						</c:forEach>
						
				    </c:if>
				</tbody>
				</table>
			</c:if>
			<c:if test="${not empty coursenew}">
				<form:form action="courses/add" method="post"
					modelAttribute="course" role="form" id="courseForm"
					class="form-horizontal">
					<c:if test="${not empty error}">
						<div class="form-group">
							<label class="alert alert-warning col-sm-offset-2">${error}</label>
						</div>
					</c:if>
					<center>
						<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Register
							New Course</h4>
					</center>

					<div class="form-group">
						<label class="control-label col-sm-4" for="courseCode">Course
							Code <span class="req">*</span>
						</label>
						<div class="col-sm-6">
							<form:input class="form-control" name="courseCode"
								id="courseCodeInput" path="courseCode" tabindex="1" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="courseName">Course
							Name <span class="req">*</span>
						</label>
						<div class="col-sm-6">
							<form:input class="form-control" name="courseName"
								id="courseNameInput" path="courseName" tabindex="2" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8">
							<button type="submit" class="btn btn-primary col-sm-6"
								name="save">Save</button>
						</div>
						<input type="hidden" name="userId" value="${user.id}">
					</div>
				</form:form>
			</c:if>
			<c:if test="${not empty coursedit}">
				<form:form action="courses/update" method="post"
					modelAttribute="course" role="form" id="courseEditForm"
					class="form-horizontal">
					<form:hidden path="id" />
					<c:if test="${not empty error}">
						<div class="form-group">
							<label class="alert alert-warning col-sm-offset-4">${error}</label>
						</div>
					</c:if>
					<center>
						<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Edit
							${course.course.courseName}</h4>
					</center>

					<div class="form-group">
						<label class="control-label col-sm-4" for="courseCode">Course
							Code </label>
						<div class="col-sm-6">
							<form:input class="form-control" name="courseCode"
								id="courseCodeInput" path="course.courseCode" tabindex="1" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="courseName">Course
							Name </label>
						<div class="col-sm-6">
							<form:input class="form-control" name="courseName"
								id="courseNameInput" path="course.courseName" tabindex="2" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8">
							<button type="submit" class="btn btn-primary col-sm-6"
								name="update">Update</button>
						</div>
					</div>
				</form:form>
			</c:if>
			<c:if test="${not empty message}">
				<div class="form-group">
					<label class="alert alert-success col-sm-offset-2">${message}</label>
				</div>
			</c:if>
		</div>
		<div class="col-lg-3">
			<center>
				<h4 style="border-bottom: 2px solid green; padding: 10px 10px">List
					of Courses</h4>
			</center>
			<c:if test="${not empty courses}">			
					<c:forEach var="cours" items="${courses}">
					    <div class="list-box">
					       <span>${cours.courseName}</span>
					       <span><a>[${cours.courseCode}]</a></span>
					    </div>
					</c:forEach>				
			</c:if>
		</div>
	</div>
</sec:authorize>
<sec:authorize access="hasRole('STUDENT')">
	<div class="row col-lg-12">
		<div class="col-lg-3 col-offset-2">
			<center>
				<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Enrolled
					Courses</h4>
			</center>
			<c:if test="${not empty studentcourses}">			
					<c:forEach var="scourse" items="${studentcourses}">
					    <div class="list-box">
					       <span>${scourse.course.courseName}</span>
					       <span><a href="courses/enroll/${scourse.id}/delete" onclick="return window.confirm('Are you sure you want to delete this course?');"
							style="color: #000;">del</a></span>
					    </div>
					</c:forEach>			
			</c:if>

		</div>
		<div class="col-lg-9">
			<center>
				<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Courses</h4>
			</center>
			<form:form action="courses/enrolls" method="post"
					role="form" id="courseForm"
					class="form-horizontal">
			<table class="table table-striped" id="table">
				<thead>
					<tr>
						<td></td>
						<td>Course Code</td>
						<td>Course Name</td>
						<td></td>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty courses}">
						<c:forEach var="regc" items="${courses}">
							<tr>
								<td><input type="checkbox" name="courseId"
									value="${regc.id}"></td>
								<td>${regc.courseCode}</td>
								<td>${regc.courseName}</td>
								<td><a href="courses/enroll/${regc.id}"><span
										class="label label-success">Enroll</span></a></td>
							</tr>
						</c:forEach>
						
				    </c:if>
				</tbody>
				<tr>
							<td colspan="2"><select name="action">
									<option value="none">Bulk action</option>
									<option value="enroll">Enroll</option>
							</select> <input name="enrollCourse" type="submit" value="Apply"
								class="btn btn-default"></td>
							<td colspan="2"></td>
						</tr>
			</table>
		</form:form>
		</div>
	</div>
	<script>
	$(document).ready(function() {
		$('#table').dataTable({
	        "pagingType": "simple_numbers"
	    });
	} );
</script>
</sec:authorize>
