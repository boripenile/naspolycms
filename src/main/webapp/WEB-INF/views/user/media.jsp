<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div style="float: right;">
	<a href="home">Home</a>
</div>
<sec:authorize access="hasRole('FACULTY')">
	<div class="row col-lg-12">
		<div class="col-lg-3">
			<center>
				<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Registered
					Courses</h4>
			</center>

			<c:if test="${not empty mycourses}">
				<c:forEach var="course" items="${mycourses}">
					<div class="list-box" style="width: 200px">
						<span>${course.course.courseName}</span> <span><a
							href="media/view/${course.course.id}" style="color: #000;">media</a></span>
					</div>
				</c:forEach>
			</c:if>

		</div>
		<div class="col-lg-9">
			<c:if test="${not empty message}">
				<div class="form-group">
					<label class="alert alert-warning col-sm-offset-4">${message}</label>
				</div>
			</c:if>
			<c:if test="${not empty course}">
			    <center>
				<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Media for ${course.courseName}</h4>
			    </center>
				<table class="table table-striped">
					<tr>
						<th width="4%">No</th>
						<th width="30%">Filename</th>
						<th width="30%">Notes</th>
						<th width="16%">Type</th>
						<th width="20%">&nbsp;</th>
					</tr>
					<c:choose>
						<c:when test="${allmedia != null}">
							<c:forEach var="file" items="${allmedia}" varStatus="counter">
								<tr>
									<td>${counter.index + 1}</td>
									<td>${file.filename}</td>
									<td>${file.notes}</td>
									<td>${file.type}</td>
									<td><div align="center">
											<a href="media/download/${file.id}">Download</a> / <a
												href="media/delete/${file.id}/${file.course.id}">Delete</a>
										</div></td>
								</tr>
							</c:forEach>
						</c:when>
					</c:choose>
				</table>

				<h4>Add New Media</h4>
				<form action="media/upload" method="post"
					enctype="multipart/form-data">
					<table class="table table-striped">
						<tr>
							<td width="35%"><strong>File to upload</strong></td>
							<td width="65%"><input type="file" name="media" /></td>
						</tr>
						<tr>
							<td><strong>Notes</strong></td>
							<td><input type="text" name="notes" width="100px" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><input type="submit" name="submit"
								class="btn btn-default" value="Add" /> <input type="hidden"
								name="courseId" value="${course.id}" />
								
								</td>
						</tr>
					</table>
				</form>

			</c:if>

		</div>
	</div>
</sec:authorize>
<sec:authorize access="hasRole('STUDENT')">
	<div class="row col-lg-12">
		<div class="col-lg-3">
			<center>
				<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Enrolled
					Courses</h4>
			</center>
			<c:if test="${not empty studentcourses}">
				<c:forEach var="scourse" items="${studentcourses}">
					<div class="list-box" style="width: 200px">
						<span>${scourse.course.courseName}</span> <span><a
							href="media/view/${scourse.course.id}" style="color: #000;">media</a></span>
					</div>
				</c:forEach>
			</c:if>

		</div>
		<div class="col-lg-9">
		    <c:if test="${not empty message}">
				<div class="form-group">
					<label class="alert alert-warning col-sm-offset-4">${message}</label>
				</div>
			</c:if>
			<c:if test="${not empty course}">
			    <center>
				<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Media for ${course.courseName}</h4>
			    </center>
				<table class="table table-striped">
					<tr>
						<th width="4%">No</th>
						<th width="30%">Filename</th>
						<th width="30%">Notes</th>
						<th width="16%">Type</th>
						<th width="20%">&nbsp;</th>
					</tr>
					<c:choose>
						<c:when test="${allmedia != null}">
							<c:forEach var="file" items="${allmedia}" varStatus="counter">
								<tr>
									<td>${counter.index + 1}</td>
									<td>${file.filename}</td>
									<td>${file.notes}</td>
									<td>${file.type}</td>
									<td><div align="center">
											<a href="media/download/${file.id}">Download</a> 
										</div></td>
								</tr>
							</c:forEach>
						</c:when>
					</c:choose>
				</table>				
			</c:if>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			$('#table').dataTable({
				"pagingType" : "simple_numbers"
			});
		});
	</script>
</sec:authorize>
