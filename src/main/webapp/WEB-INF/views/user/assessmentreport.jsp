<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div style="float: right;">
	<a href="home" class="noprint">Home</a>
</div>
<sec:authorize access="hasRole('FACULTY')">
	<div class="row col-lg-12">
		<div class="col-lg-1"></div>
		<div class="col-lg-10">
			<form action="assessmentsreport/report" method="post" role="form"
				id="courseEditForm" class="form-horizontal">
				<c:if test="${not empty message}">
					<div class="form-group noprint">
						<label class="alert alert-warning col-sm-offset-4">${message}</label>
					</div>
				</c:if>
				<div class="form-group noprint">
					<label class="control-label col-sm-6" for="email">Get
						Assessments by Student's Email Address</label>
					<div class="col-sm-6">
						<input class="form-control" name="email" id="emailAddressInput"
							tabindex="1" />
					</div>
				</div>
				<div class="form-group noprint">
					<div class="col-sm-offset-6 col-sm-6">
						<button type="submit" class="btn btn-primary" name="update">Lookup
							Assessments</button>
					</div>
				</div>
				<c:if test="${not empty registeredcourses}">
					<table class="table table-striped noprint">
						<thead>
							<tr>
								<th>Course Code</th>
								<th>Course Name</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${registeredcourses}" var="register">
								<tr>
									<td>${register.course.courseCode}</td>
									<td>${register.course.courseName}</td>
									<td><a
										href="assessmentsreport/report/${register.course.id}">View
											Assignment Report</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
				<c:if test="${not empty courseassignments}">
				<img width="800" height="100" alt="" src="${pageContext.servletContext.contextPath}/resources/images/naspoly_banner.gif">
					<c:if test="${not empty answers}">
						<c:if test="${not empty course}">
							<table class="table">
								<tr>
									<td colspan="4"><h3>Lecturer Details</h3></td>
								</tr>
								<tr>
									<td>First Name:</td>
									<td>${lecturer.firstname}</td>
									<td>Last Name:</td>
									<td>${lecturer.lastname}</td>
								</tr>
								<tr>
									<td>Gender:</td>
									<td>${lecturer.gender}</td>
									<td>Email Address:</td>
									<td>${lecturer.emailaddress}</td>
								</tr>
								<tr>
									<td colspan="4"><h3>${course.courseCode}-
											${course.courseName}</h3></td>
								</tr>
							</table>
							<table class="table table-striped">
								<thead>
									<tr>
										<th>#</th>
										<th>Students</th>
										<%
											int sn = 0;
										%>
										<c:forEach items="${courseassignments}" var="assignment">
											<th style="text-align: center;">Assessment <%=++sn%> <br />
												${assignment.obtainableMark}
											</th>
										</c:forEach>
									</tr>
								</thead>
								<tbody>
									<%
										int s = 0;
									%>
									<c:forEach items="${students}" var="student">
										<tr>
											<td><%=++s%></td>
											<td>${student.firstname} ${student.lastname}</td>											
												<c:forEach items="${courseassignments}" var="assign">
													<c:if test="${assign.course.id eq course.id }">
														<td style="text-align: center;">
															<c:forEach items="${answers}" var="answer">
															<c:choose>
																<c:when test="${answer.assignment.id eq assign.id && answer.student.id eq student.id}">																	
																		${answer.obtained}																																	
																</c:when>																
															</c:choose>
							   							  </c:forEach>
														</td>														
													</c:if>
												</c:forEach>											
										</tr>
									</c:forEach>

								</tbody>
							</table>
							<div
								class="col-lg-offset-2 col-sm-offset-1 col-lg-12 col-sm-8 noprint">
								<a href="javascript:window.print()" class="btn btn-primary">PRINT</a>
							</div>
						</c:if>
					</c:if>
				</c:if>
				<c:if test="${not empty enrolledcourses}">
				<img width="800" height="100" alt="" src="${pageContext.servletContext.contextPath}/resources/images/naspoly_banner.gif">
					<table class="table">
						<tr>
							<td colspan="4"><h3>Student Details</h3></td>
						</tr>
						<tr>
							<td>First Name:</td>
							<td>${user.firstname}</td>
							<td>Last Name:</td>
							<td>${user.lastname}</td>
						</tr>
						<tr>
							<td>Gender:</td>
							<td>${user.gender}</td>
							<td>Email Address:</td>
							<td>${user.emailaddress}</td>
						</tr>
					</table>
					<table class="table table-striped">
						<thead>
							<tr><th>#</th><th><h4>Courses</h4></th><th colspan="10"><h4>Assessments</h4></th></tr>
						</thead>
						<tbody>
							<% int t = 0; %>
							<c:forEach items="${enrolledcourses}" var="enrolled">
								<tr>
									<td><%= ++t %></td>
									<td style="padding-left: 5px;"><h4>${enrolled.course.courseName}</h4></td>
									<td>
										<table>
											<tbody>
											<tr>
												<c:if test="${not empty answeredassessments}">													
													<c:forEach items="${answeredassessments}" var="answered">
														<c:if
															test="${answered.assignment.course.id == enrolled.course.id}">
															<td><em  style="padding-right: 15px;">${answered.assignment.obtainableMark}</em>
																<h4  style="padding-right: 15px;">${answered.obtained}</h4></td>
														</c:if>
													</c:forEach>
												</c:if>
											</tr></tbody>
										</table>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div
						class="col-lg-offset-2 col-sm-offset-1 col-lg-12 col-sm-8 noprint">
						<a href="javascript:window.print()" class="btn btn-primary">PRINT</a>
					</div>
				</c:if>
			</form>

		</div>
		<div class="col-lg-1"></div>
	</div>
</sec:authorize>
<sec:authorize access="hasRole('STUDENT')">
	<div class="row col-lg-12">
		<div class="col-lg-1"></div>
		<div class="col-lg-10">
			<form action="assessmentsreport/report" method="post" role="form"
				id="courseEditForm" class="form-horizontal">
				<c:if test="${not empty message}">
					<div class="form-group">
						<label class="alert alert-warning col-sm-offset-4">${message}</label>
					</div>
				</c:if>
				<input type="hidden" name="email"
					value="${pageContext.request.userPrincipal.name}">
				<div class="form-group noprint">
					<div class="col-sm-offset-4 col-sm-8">
						<button type="submit" class="btn btn-primary" name="update">VIEW
							YOUR ASSESSMENT SUMMARY</button>
					</div>
				</div>

				<c:if test="${not empty enrolledcourses}">
					<table class="table">
						<tr>
							<td colspan="4"><h3>Student Details</h3></td>
						</tr>
						<tr>
							<td>First Name:</td>
							<td>${user.firstname}</td>
							<td>Last Name:</td>
							<td>${user.lastname}</td>
						</tr>
						<tr>
							<td>Gender:</td>
							<td>${user.gender}</td>
							<td>Email Address:</td>
							<td>${user.emailaddress}</td>
						</tr>
					</table>
					<table class="table table-striped">
						<thead>
							<tr><th>#</th><th><h4>Courses</h4></th><th colspan="10"><h4>Assessments</h4></th></tr>
						</thead>
						<tbody>
							<% int q = 0; %>
							<c:forEach items="${enrolledcourses}" var="enrolled">
								<tr>
									<td><%= ++q %></td>
									<td style="padding-left: 5px;"><h4>${enrolled.course.courseName}</h4></td>
									<td>
										<table>
											<tbody>
											<tr>
												<c:if test="${not empty answeredassessments}">													
													<c:forEach items="${answeredassessments}" var="answered">																						
														<c:if test="${answered.assignment.course.id == enrolled.course.id}">
															<td><em  style="padding-right: 15px;">${answered.assignment.obtainableMark}</em>
																<h4  style="padding-right: 15px;">${answered.obtained}</h4></td>
														</c:if>
													</c:forEach>
												</c:if>
											</tr></tbody>
										</table>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div
						class="col-lg-offset-2 col-sm-offset-1 col-lg-12 col-sm-8 noprint">
						<a href="javascript:window.print()" class="btn btn-primary">PRINT</a>
					</div>
				</c:if>
			</form>

		</div>

		<div class="col-lg-1"></div>
	</div>
</sec:authorize>
