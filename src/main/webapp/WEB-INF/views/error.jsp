<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row">
	<div class="col-sm-2"></div>
	<div class="col-sm-8">
		<c:if test="${message == null}">
			<h3>Error occurred on the page</h3>
			<p class="alert alert-danger">${exception.message}</p>
		</c:if>
		<c:if test="${not empty message}">
			<p class="alert alert-danger">${message}</p>
			<div>
			   <input type="submit" value="Reload page" class="btn btn-danger" onclick="location.reload(true);">
			</div>
		</c:if>
	</div>
	<div class="col-sm-2"></div>
</div>
