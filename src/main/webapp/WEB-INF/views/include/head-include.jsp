<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<c:set var="req" value="${pageContext.request}" /> 
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}" />
<base href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/" />

<link href="${pageContext.servletContext.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css" media="screen">
<link href="${pageContext.servletContext.contextPath}/resources/dist/css/formValidation.min.css" rel="stylesheet" type="text/css" media="screen">
<link href="${pageContext.servletContext.contextPath}/resources/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css" media="screen">
<link href="${pageContext.servletContext.contextPath}/resources/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" media="screen">
<link href="${pageContext.servletContext.contextPath}/resources/css/jquery.dataTables.css" rel="stylesheet" type="text/css" media="screen">
<link href="${pageContext.servletContext.contextPath}/resources/css/custom.css" rel="stylesheet" type="text/css" media="screen">
<link href="${pageContext.servletContext.contextPath}/resources/css/ui.jqgrid.css" rel="stylesheet" type="text/css" media="screen">
<link href="${pageContext.servletContext.contextPath}/resources/jquery-ui-datepicker/jquery-ui.css" rel="stylesheet" type="text/css" media="screen">
<link href="${pageContext.servletContext.contextPath}/resources/jquery-ui-datepicker/jquery-ui.theme.min.css" rel="stylesheet" type="text/css" media="screen">
<link href="${pageContext.servletContext.contextPath}/resources/jquery-ui-datepicker/jquery-ui.structure.min.css" rel="stylesheet" type="text/css" media="screen">
<link href="${pageContext.servletContext.contextPath}/resources/css/print.css" rel="stylesheet" type="text/css" media="print">
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico" />
<script src="${pageContext.servletContext.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/dataTables.bootstrap.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/dist/js/formValidation.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/dist/js/framework/bootstrap.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/i18n/grid.locale-en.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/jquery.jqGrid.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/myscript.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/check.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/jquery-ui-datepicker/jquery-ui.js"></script>


<script>
$(function(){
	$('a').tooltip();
	$.datepicker.setDefaults({
		dateFormat: 'dd-mm-yy',
		changeMonth: true,
		changeYear : true,
	}); 
});

</script>
