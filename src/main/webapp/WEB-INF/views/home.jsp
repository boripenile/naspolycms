<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div class="page-header">
	<h2 style="text-align: center; margin-top: -50px;">
		Design and Implementation of An Effective Academic Web-based Content Management System
	</h2>
	</div>
<c:if test="${not empty message}">
	<p style="text-align: center; padding: 20px 20px;">
		<em class="alert alert-success">${message}</em>
	</p>
</c:if>
<c:if test="${not empty error}">
	<div class="form-group">
		<label class="alert alert-warning col-sm-offset-2">${error}</label>
	</div>
</c:if>
<div class="row col-md-12">
	<div class="col-md-3">
		<a href="courses/list" class="thumbnail"> <img alt="" src="${pageContext.servletContext.contextPath}/resources/images/course-icon.png"
			style="width: 150px; height: 150px;">
			<p style="text-align: center">Courses</p>
		</a>
	</div>
	<div class="col-md-3">
		<a href="discussions/list" class="thumbnail"> <img alt="" src="${pageContext.servletContext.contextPath}/resources/images/discussion-icon.png"
			style="width: 150px; height: 150px;">
			<p style="text-align: center">Discussion</p>
		</a>
	</div>
	<div class="col-md-3">
		<a href="assignments/courseslist" class="thumbnail"> <img alt="" src="${pageContext.servletContext.contextPath}/resources/images/assessment-icon.png"
			style="width: 150px; height: 150px;">
			<p style="text-align: center">Assessment</p>
		</a>
	</div>
	<div class="col-md-3">
		<a href="mailing/courses" class="thumbnail"> <img alt="" src="${pageContext.servletContext.contextPath}/resources/images/mailing.jpg"
			style="width: 150px; height: 150px;">
			<p style="text-align: center">Mailing</p>
		</a>
	</div>
</div>
<div class="row col-md-12">
	<div class="col-md-3">
		
	</div>
	<div class="col-md-3">
		<a href="assessmentsreport" class="thumbnail"> <img alt="" src="${pageContext.servletContext.contextPath}/resources/images/report-icon.png"
			style="width: 150px; height: 150px;">
			<p style="text-align: center">Assessments Report</p>
		</a>
	</div>
	
	<div class="col-md-3">
		<a href="media/list" class="thumbnail"> <img alt="" src="${pageContext.servletContext.contextPath}/resources/images/media.png"
			style="width: 150px; height: 150px;">
			<p style="text-align: center">Courses Media</p>
		</a>
	</div>
	<div class="col-md-3">
		
	</div>
</div>




