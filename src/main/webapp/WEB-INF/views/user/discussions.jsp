<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div style="float: right;"><a href="home">Home</a></div>
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
							href="discussions/${course.id}/topics">topics</a></span>
						<p>
							<span><a href="discussions/${course.id}/new">new</a></span>
						</p>
					</div>
				</c:forEach>
			</c:if>
		</div>
		<div class="col-lg-8">
			<c:if test="${not empty newdiscussion}">
				<form:form action="discussions/add" method="post"
					modelAttribute="discussion" role="form" id="discussionForm"
					class="form-horizontal">
					<c:if test="${not empty error}">
						<div class="form-group">
							<label class="alert alert-warning col-sm-offset-2">${error}</label>
						</div>
					</c:if>
					<center>
						<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Create
							New Discussion in ${mycourse.course.courseName}</h4>
					</center>

					<div class="form-group">
						<label class="control-label col-sm-4" for="topic">Topic <span
							class="req">*</span>
						</label>
						<div class="col-sm-6">
							<form:input class="form-control" name="topic" id="topicInput"
								path="topic" tabindex="1" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="description">Description
						</label>
						<div class="col-sm-6">
							<form:textarea rows="3" cols="30" class="form-control"
								name="description" id="descriptionInput" maxlength="100"
								path="description" tabindex="2" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8">
							<button type="submit" class="btn btn-primary col-sm-6"
								name="save">Save</button>
						</div>
						<input type="hidden" name="ownerId" value="${owner.id}"> <input
							type="hidden" name="memberId" value="${member.id}"> <input
							type="hidden" name="courseId" value="${course.id}"> <input
							type="hidden" name="userCourseId" value="${mycourse.id}">
					</div>
				</form:form>
			</c:if>
			<c:if test="${not empty editdiscussion}">
				<form:form action="discussions/update" method="post"
					modelAttribute="discussion" role="form" id="discussionForm"
					class="form-horizontal">
					<form:hidden path="id"/>
					<c:if test="${not empty error}">
						<div class="form-group">
							<label class="alert alert-warning col-sm-offset-2">${error}</label>
						</div>
					</c:if>
					<center>
						<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Edit Discussion's Topic</h4>
					</center>

					<div class="form-group">
						<label class="control-label col-sm-4" for="topic">Topic <span
							class="req">*</span>
						</label>
						<div class="col-sm-6">
							<form:input class="form-control" name="topic" id="topicInput"
								path="topic" tabindex="1" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="description">Description
						</label>
						<div class="col-sm-6">
							<form:textarea rows="3" cols="30" class="form-control"
								name="description" id="descriptionInput" maxlength="100"
								path="description" tabindex="2" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8">
							<button type="submit" class="btn btn-primary col-sm-6"
								name="save">Update</button>
						</div>
						<input type="hidden" name="ownerId" value="${discussion.owner.id}"> <input
							type="hidden" name="memberId" value="${discussion.member.id}"> <input
							type="hidden" name="courseId" value="${discussion.course.id}"><input
							type="hidden" name="mycourseId" value="${mycourseId}">
					</div>
				</form:form>
			</c:if>
			<c:if test="${newdiscussion == null}">
				<center>
				   <c:if test="${not empty mdiscussions}">
				      <h4 style="border-bottom: 2px solid green; padding: 10px 10px">Discussion
						Topics on ${course.courseName}</h4>
				   </c:if>
					
				</center>
				<c:if test="${not empty mdiscussions}">
					<c:forEach var="sdiscussion" items="${mdiscussions}">
						<div class="list-box" style="width: 550px">
							<span>${sdiscussion.topic}</span> <span><a
								href="discussions/${sdiscussion.course.id}/${sdiscussion.id}/join">join</a></span>
							<p>
								<em style="font-size: smaller;">${sdiscussion.description}</em>
								<c:if test="${logged.id == sdiscussion.owner.id}">
									<span><a href="discussions/${sdiscussion.id}/edit/${mycourseId}">edit</a></span>
								</c:if>
								<c:if test="${logged.id == sdiscussion.member.id}">
									<span><a
										href="discussions/${sdiscussion.id}/discontinue">discontinue</a></span>
								</c:if>
							</p>
						</div>

					</c:forEach>
				</c:if>
			</c:if>
			<c:if test="${not empty feedslist}">
			    <center>
						<p style="border-bottom: 2px solid green; padding: 10px 10px">${discussion.topic}</p>
				</center>
				 <p><small><em>${discussion.description}</em></small></p>	
				 <small>Created by: ${discussion.owner.firstname} ${discussion.owner.lastname}</small>
				 <p><small>Date: ${discussion.addDate}</small></p>
			    <c:if test="${not empty feeds}">	    
                 <c:forEach var="feed" items="${feeds}">
                     <div class="list-box" style="width: 600px">						
						    <p>${feed.comment}</p>
						    <small style="float: right;">Comment By: ${feed.contributor.firstname} ${feed.contributor.lastname}</small>
						    <small style="float: left;">Date: ${feed.addDate}</small>
					 </div>                 
                 </c:forEach>
            </c:if>	
            
            <br/>
            <form action="discussions/feeds/add" method="post"
					role="form" id="feed"
					class="form-horizontal">
					
					<center>
						<p style="border-bottom: 2px solid green; padding: 10px 10px">Contribute to this discussion</p>
					</center>
                    
					<div class="form-group">
						<label class="control-label col-sm-2" for="comment">Comment
						</label>
						<div class="col-sm-8">
						    <c:if test="${not empty error}">
						        <label class="req">${error}</label>
						    </c:if>
							<textarea rows="5" cols="50" class="form-control"
								name="comment" id="commentInput" maxlength="1000"
								tabindex="2"></textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8">
							<button type="submit" class="btn btn-primary col-sm-6"
								name="save">Submit Comment</button>
						</div>
						<input type="hidden" name="discussionId" value="${discussion.id}"> 
						<input type="hidden" name="courseId" value="${discussion.course.id}"> 
					</div>
				</form>			
			</c:if>          
		</div>
	</div>
</sec:authorize>
<sec:authorize access="hasRole('STUDENT')">
	<div class="row col-lg-12">
		<div class="col-lg-4">
			<center>
				<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Enrolled
					Courses</h4>
			</center>
			<c:if test="${not empty studentcourses}">
				<c:forEach var="course" items="${studentcourses}">
					<div class="list-box">
						<span>${course.course.courseName}</span> <span><a
							href="discussions/${course.id}/topics">topics</a></span>
						<p>
							<span><a href="discussions/${course.id}/new">new</a></span>
						</p>
					</div>
				</c:forEach>
			</c:if>
		</div>
		<div class="col-lg-8">
			<c:if test="${not empty newdiscussion}">
				<form:form action="discussions/add" method="post"
					modelAttribute="discussion" role="form" id="discussionForm"
					class="form-horizontal">
					<c:if test="${not empty error}">
						<div class="form-group">
							<label class="alert alert-warning col-sm-offset-2">${error}</label>
						</div>
					</c:if>
					<center>
						<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Create
							New Discussion in ${mycourse.course.courseName}</h4>
					</center>

					<div class="form-group">
						<label class="control-label col-sm-4" for="topic">Topic <span
							class="req">*</span>
						</label>
						<div class="col-sm-6">
							<form:input class="form-control" name="topic" id="topicInput"
								path="topic" tabindex="1" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="description">Description
						</label>
						<div class="col-sm-6">
							<form:textarea rows="3" cols="30" class="form-control"
								name="description" id="descriptionInput" maxlength="100"
								path="description" tabindex="2" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8">
							<button type="submit" class="btn btn-primary col-sm-6"
								name="save">Save</button>
						</div>
						<input type="hidden" name="ownerId" value="${owner.id}"> <input
							type="hidden" name="memberId" value="${member.id}"> <input
							type="hidden" name="courseId" value="${course.id}"> <input
							type="hidden" name="userCourseId" value="${mycourse.id}">
					</div>
				</form:form>
			</c:if>
			<c:if test="${not empty editdiscussion }">
				<form:form action="discussions/update" method="post"
					modelAttribute="discussion" role="form" id="discussionForm"
					class="form-horizontal">
					<form:hidden path="id"/>
					<c:if test="${not empty error}">
						<div class="form-group">
							<label class="alert alert-warning col-sm-offset-2">${error}</label>
						</div>
					</c:if>
					<center>
						<h4 style="border-bottom: 2px solid green; padding: 10px 10px">Edit Discussion's Topic</h4>
					</center>

					<div class="form-group">
						<label class="control-label col-sm-4" for="topic">Topic <span
							class="req">*</span>
						</label>
						<div class="col-sm-6">
							<form:input class="form-control" name="topic" id="topicInput"
								path="topic" tabindex="1" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-4" for="description">Description
						</label>
						<div class="col-sm-6">
							<form:textarea rows="3" cols="30" class="form-control"
								name="description" id="descriptionInput" maxlength="100"
								path="description" tabindex="2" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8">
							<button type="submit" class="btn btn-primary col-sm-6"
								name="save">Update</button>
						</div>
						<input type="hidden" name="ownerId" value="${discussion.owner.id}"> <input
							type="hidden" name="memberId" value="${discussion.member.id}"> <input
							type="hidden" name="courseId" value="${discussion.course.id}"><input
							type="hidden" name="mycourseId" value="${mycourseId}">
					</div>
				</form:form>
			</c:if>
			<c:if test="${newdiscussion == null && feedslist == null}">
				<center>
				   <c:if test="${not empty sdiscussions}">
				      <h4 style="border-bottom: 2px solid green; padding: 10px 10px">Discussion
						Topics on ${course.courseName}</h4>
				   </c:if>
					
				</center>
				<c:if test="${not empty sdiscussions}">
					<c:forEach var="sdiscussion" items="${sdiscussions}">
						<div class="list-box" style="width: 550px">
							<span>${sdiscussion.topic}</span> <span><a
								href="discussions/${sdiscussion.course.id}/${sdiscussion.id}/join">join</a></span>
							<p>
								<em style="font-size: smaller;">${sdiscussion.description}</em>
								<c:if test="${logged.id == sdiscussion.owner.id}">
									<span><a href="discussions/${sdiscussion.id}/edit/${mycourseId}">edit</a></span>
								</c:if>
								<c:if test="${logged.id == sdiscussion.member.id}">
									<span><a
										href="discussions/${sdiscussion.id}/discontinue">discontinue</a></span>
								</c:if>
							</p>
						</div>

					</c:forEach>
				</c:if>
			</c:if>
			<c:if test="${not empty feedslist}">
			    <center>
						<p style="border-bottom: 2px solid green; padding: 10px 10px">${discussion.topic}</p>
				</center>
				 <p><small><em>${discussion.description}</em></small></p>	
				 <small>Created by: ${discussion.owner.firstname} ${discussion.owner.lastname}</small>
				 <p><small>Date: ${discussion.addDate}</small></p>
			    <c:if test="${not empty feeds}">	    
                 <c:forEach var="feed" items="${feeds}">
                     <div class="list-box" style="width: 600px">						
						    <p>${feed.comment}</p>
						    <small style="float: right;">Comment By: ${feed.contributor.firstname} ${feed.contributor.lastname}</small>
						    <small style="float: left;">Date: ${feed.addDate}</small>
					 </div>                 
                 </c:forEach>
            </c:if>	
            
            <br/>
            <form action="discussions/feeds/add" method="post"
					role="form" id="feed"
					class="form-horizontal">					
					<center>
						<p style="border-bottom: 2px solid green; padding: 10px 10px">Contribute to this discussion</p>
					</center>
                    
					<div class="form-group">
						<label class="control-label col-sm-2" for="comment">Comment
						</label>
						<div class="col-sm-8">
						    <c:if test="${not empty error}">
						        <label class="req">${error}</label>
						    </c:if>
							<textarea rows="5" cols="70" class="form-control"
								name="comment" id="commentInput" maxlength="1000"
								tabindex="2"></textarea>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-4 col-sm-8">
							<button type="submit" class="btn btn-primary col-sm-6"
								name="save">Submit Comment</button>
						</div>
						<input type="hidden" name="discussionId" value="${discussion.id}"> 
						<input type="hidden" name="courseId" value="${discussion.course.id}"> 
					</div>
					<div style="float: right;"><a href="#feed">Back to top</a></div>
				</form>		
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
