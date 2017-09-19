<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/head-include.jsp"></jsp:include>
<title><tiles:getAsString name="title" /></title>
</head>
<body>

	<div class="container theme-showcase" role="main">
		<div class="row center-block">

			<div class="col-xs-12  noprint">
				<tiles:insertAttribute name="header" />
			</div>

			<!-- main -->
			<div class="col-lg-12">
				<div class="col-lg-12 col-xs-12 center-block">
				   <tiles:insertAttribute name="content" />
				</div>
			</div>
			<!-- main -->
			<div class="row noprint">
				<nav class="col-xs-12 center-block">
					<tiles:insertAttribute name="footer" />
				</nav>
			</div>
			<!-- footer -->
		</div>
		<!-- content -->
	</div>
	<!-- container -->
</body>
</html>