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
					<div class="list-box" style="width: 230px">
						<span>${course.course.courseName}</span> <span><a
							href="assignments/fcourses/${course.course.id}" style="color: #000;">assignments</a></span>
					</div>
				</c:forEach>
			</c:if>

		</div>
		<div class="col-lg-9">
			<c:if test="${not empty myassignments}">
				<a href="assignments/fcourses/assignment/${course.id}/new"
					class="btn btn-primary">Add Assignment</a>
				
					<c:if test="${not empty message}">
						<div class="form-group">
							<label class="alert alert-warning col-sm-offset-2">${message}</label>
						</div>
					</c:if>
					<table class="table table-striped" id="table">
						<thead>
							<tr>
								<td>S/N</td>
								<td>Course Code</td>
								<td>Question</td>
								<td>Obtainable Mark</td>
								<td>Action</td>
							</tr>
						</thead>
						<tbody>
							<c:if test="${not empty courseassignments}">
								<%
									int sn = 0;
								%>
								<c:forEach var="assignment" items="${courseassignments}">
									<tr>
										<td><%=++sn%></td>
										<td>${assignment.course.courseCode}</td>
										<td>${assignment.question}</td>
										<td>${assignment.obtainableMark}</td>
										<td><a href="assignments/fcourses/assignment/${assignment.id}/view"><span
												class="label label-success">edit</span></a>
											<a href="assignments/fcourses/assignment/${assignment.id}/studentsattempt"><span
												class="label label-success">student answers</span></a></td>
									</tr>
								</c:forEach>

							</c:if>
						</tbody>

					</table>
				
			</c:if>
			<c:if test="${not empty gradeanswered}">
				<form:form action="assignments/fcourses/assignment/attempt"
					method="post" modelAttribute="answered" role="form"
					id="assignmentForm" class="form-horizontal">
					<form:hidden path="id"/>
					<a href="assignments/fcourses/assignment/${answered.assignment.id}/studentsattempt">&larr; Back</a>
					<c:if test="${not empty error}">
						<div class="form-group">
							<label class="alert alert-warning col-sm-offset-2">${error}</label>
						</div>
					</c:if>
					<center>
						<h4 style="border-bottom: 2px solid green; padding: 10px 10px">${answered.assignment.course.courseName}</h4>
					</center>

					<div class="form-group">
						<label class="control-label col-sm-4">Course Code</label>
						<div class="col-sm-6">
							<label class="control-label col-sm-12" style="text-align:left;">${answered.assignment.course.courseCode}</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4">Question</label>
						<div class="col-sm-6">
						   <label class="control-label col-sm-12" style="text-align:left;">${answered.assignment.question}</label>				
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4">Obtainable Mark</label>
						<div class="col-sm-6">
							<label class="control-label col-sm-12" style="text-align:left;">${answered.assignment.obtainableMark}</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4">Student's Attempt</label>
						<div class="col-sm-6">
							<label class="control-label col-sm-12" style="text-align:left;">${answered.answer}</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="obtained">Mark
							<span class="req">*</span>
						</label>
						<div class="col-sm-6">
						<form:input path="obtained" name="obtained" class="form-control" id="obtainedInput"/>
						<form:errors path="obtained"></form:errors>			  
						
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8">
							<button type="submit" class="btn btn-primary col-sm-6"
								name="save">Submit</button>
						</div>
					</div>
				</form:form>
			</c:if>
			<c:if test="${not empty answeredassignments}">
			        <a href="assignments/fcourses/${course.id}">&larr; Back</a>
					<c:if test="${not empty message}">
						<div class="form-group">
							<label class="alert alert-warning col-sm-offset-2">${message}</label>
						</div>
					</c:if>
					<center>
						<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Question: ${assignment.question}</h4>
					</center>
					<table class="table table-striped" id="table">
						<thead>
							<tr>
								<td>S/N</td>								
								<td>Student's Name</td>
								<td>Course Code</td>
								<td>Obtained Mark</td>
								<td>Action</td>
							</tr>
						</thead>
						<tbody>
							<c:if test="${not empty answeredcourseassignments}">
								<%
									int sn = 0;
								%>
								<c:forEach var="answered" items="${answeredcourseassignments}">
									<tr>
										<td><%=++sn%></td>										
										<td>${answered.student.firstname} ${answered.student.lastname}</td>
										<td>${answered.assignment.course.courseCode}</td>
										<td>${answered.obtained}</td>
										<td><a
											href="assignments/fcourses/assignment/${answered.id}/attempted"><span
												class="label label-success">award mark</span></a></td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>

					</table>
				
			</c:if>
			<c:if test="${not empty assignmentnew}">
				<form:form action="assignments/fcourses/assignment/add"
					method="post" modelAttribute="assignment" role="form"
					id="assignmentForm" class="form-horizontal">
					<c:if test="${not empty error}">
						<div class="form-group">
							<label class="alert alert-warning col-sm-offset-2">${error}</label>
						</div>
					</c:if>
					<center>
						<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Add
							New Assignment for ${assignment.course.courseName}</h4>
					</center>

					<div class="form-group">
						<label class="control-label col-sm-4" for="course.courseCode">Course
							Code <span class="req">*</span>
						</label>
						<div class="col-sm-6">
							<label class="control-label col-sm-6">${assignment.course.courseCode}</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="question">Question
							<span class="req">*</span>
						</label>
						<div class="col-sm-6">
							<form:textarea cols="50" rows="10" class="form-control"
								name="question" id="questionInput" path="question"
								tabindex="2" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="obtainableMark">Obtainable
							<span class="req">*</span>
						</label>
						<div class="col-sm-6">			  
							<form:input class="form-control" name="obtainableMark" id="obtainableMarkInput" path="obtainableMark" />														
							
							<form:errors path="obtainableMark"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8">
							<button type="submit" class="btn btn-primary col-sm-6"
								name="save">Save</button>
						</div>
						<input type="hidden" name="memberId"
							value="${assignment.member.id}"> <input type="hidden"
							name="courseId" value="${assignment.course.id}">

					</div>
				</form:form>
			</c:if>
			<c:if test="${not empty assignmentedit}">
				<form:form action="assignments/fcourses/assignment/update"
					method="post" modelAttribute="assignment" role="form"
					id="assignmentEditForm" class="form-horizontal">
					<form:hidden path="id" />
					<c:if test="${not empty error}">
						<div class="form-group">
							<label class="alert alert-warning col-sm-offset-2">${error}</label>
						</div>
					</c:if>
					<center>
						<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Edit
							Assignment</h4>
					</center>

					<div class="form-group">
						<label class="control-label col-sm-4" for="course.courseCode">Course
							Code
						</label>
						<div class="col-sm-6">
							<label class="control-label col-sm-6">${assignment.course.courseCode}</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="question">Question
							<span class="req">*</span>
						</label>
						<div class="col-sm-6">
							<form:textarea cols="50" rows="10" class="form-control"
								name="question" id="questionInput" path="question"
								tabindex="2" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="obtainableMark">Obtainable
							<span class="req">*</span>
						</label>
						<div class="col-sm-6">			  
							<form:input class="form-control"
								name="obtainableMark" id="obtainableMarkInput" path="obtainableMark"/>						
							<form:errors path="obtainableMark"></form:errors>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8">
							<button type="submit" class="btn btn-primary col-sm-6"
								name="update">Update</button>
							<input type="hidden" name="memberId"
								value="${assignment.member.id}"> <input type="hidden"
								name="courseId" value="${assignment.course.id}">
						</div>
					</div>
				</form:form>
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
					<div class="list-box" >
						<span>${scourse.course.courseName}</span> <span><a
							href="assignments/scourses/${scourse.course.id}"							
							style="color: #000;">assignment</a></span>
					</div>
				</c:forEach>
			</c:if>

		</div>
		<div class="col-lg-9">
			<c:if test="${not empty myassignments}">
			    <c:if test="${not empty message}">
						<div class="form-group">
							<label class="alert alert-warning col-sm-offset-2">${message}</label>
						</div>
				</c:if>
				<table class="table table-striped" id="table">
						<thead>
							<tr>
								<td>S/N</td>
								<td>Course Code</td>
								<td>Question</td>
								<td>Obtainable Mark</td>
								<td>Action</td>
							</tr>
						</thead>
						<tbody>
							<c:if test="${not empty scourseassignments}">
								<%
									int sn = 0;
								%>
								<c:forEach var="assignment" items="${scourseassignments}">
									<tr>
										<td><%=++sn%></td>
										<td>${assignment.course.courseCode}</td>
										<td>${assignment.question}</td>
										<td>${assignment.obtainableMark}</td>
										<td><a
											href="assignments/scourses/assignment/${assignment.id}/view"><span
												class="label label-success">attempt</span></a></td>
									</tr>
								</c:forEach>

							</c:if>
						</tbody>

					</table>
				</c:if>
			<c:if test="${not empty assignmentattempt}">
				<form:form action="assignments/scourses/attemptassignment"
					method="post" role="form"
					id="attemptanswerEditForm" class="form-horizontal">					
					<c:if test="${not empty error}">
						<div class="form-group">
							<label class="alert alert-warning col-sm-offset-2">${error}</label>
						</div>
					</c:if>
					<center>
						<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Attempt
							Question</h4>
					</center>

					<div class="form-group">
						<label class="control-label col-sm-3" >Course
							Name 
						</label>
						<div class="col-sm-6">
							<label class="control-label col-sm-12" style="text-align:left;">${attemptassignment.assignment.course.courseName}</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-3">Question
							
						</label>
						<div class="col-sm-6">
							<label class="control-label col-sm-12" style="text-align:left;">${attemptassignment.assignment.question}</label>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-3">Your Answer
							<span class="req">*</span>
						</label>
						<div class="col-sm-7">
							<textarea cols="50" rows="5" class="form-control col-sm-12"
								name="answer" id="answerInput" style="text-align:left;" 
								tabindex="2" ></textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8">
							<input type="submit" class="btn btn-primary col-sm-6"
							                 onclick="return window.confirm('Are you sure that you want to submit you answer?\nYou have only one attempt.');"
								name="submit" value="Submit">
							<input type="hidden" name="assignmentId"
								value="${attemptassignment.assignment.id}"> <input type="hidden"
								name="studentId" value="${attemptassignment.student.id}"><input type="hidden"
								name="courseId" value="${attemptassignment.assignment.course.id}">
						</div>
					</div>
				</form:form>
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
