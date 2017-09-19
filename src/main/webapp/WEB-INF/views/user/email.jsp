<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div style="float: right;">
	<a href="home">Home</a>
</div>
<sec:authorize access="hasRole('FACULTY')">
	<div class="row col-lg-12">
		<div class="col-lg-4">
			<center>
				<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Registered
					Courses</h4>
			</center>

			<c:if test="${not empty mycourses}">
				<c:forEach var="course" items="${mycourses}">
					<div class="list-box">
						<span>${course.course.courseName}</span> <span><a
							href="mailing/courses/${course.course.id}/mail"
							style="color: #000;">mail</a></span>
					</div>
				</c:forEach>
			</c:if>
		</div>
		<div class="col-lg-8">
			<c:if test="${not empty newmail}">
				<center>
					<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Send
						mail to all Students enrolled for ${course.courseName}</h4>
				</center>
				<form method="post" action="mailing/send"
					enctype="multipart/form-data">
					<c:if test="${not empty error}">
						<div class="form-group">
							<label class="alert alert-warning col-sm-offset-4">${error}</label>
						</div>
					</c:if>
					<c:if test="${not empty message}">
						<div class="form-group">
							<label class="alert alert-success col-sm-offset-4">${message}</label>
						</div>
					</c:if>
					<div class="form-group">
						<label class="control-label col-lg-3" for="subject">Subject
						</label>
						<div class="col-lg-9">
							<input class="form-control" name="subject" id="subjectInput"
								tabindex="1" />
						</div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-lg-3" for="message">Message
						</label>
						<div class="col-lg-9">
							<textarea class="form-control" tabindex="2" cols="50" rows="10"
								name="message"></textarea>
						</div>
					</div>
					
					<div class="form-group" >
						<label class="control-label col-sm-3" for="topic">Attach
							File </label>
						<div class="col-lg-9">
							<input class="form-control" type="file" name="attachFile"
								size="60" />
						</div>
					</div>
					<div class="form-group">
						<input type="submit" value="Send" class="btn btn-primary" />
						<input type="hidden" name="userId" value="${user.id}" /> <input
							type="hidden" name="courseId" value="${course.id}" />
					</div>
				</form>
			</c:if>
			<c:if test="${newmail == null}">
				<c:if test="${not empty message}">
					<div class="form-group">
						<label class="alert alert-success col-sm-offset-2">${message}</label>
					</div>
				</c:if>

			</c:if>
		</div>
	</div>
</sec:authorize>