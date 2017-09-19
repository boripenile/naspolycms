<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width"/>
<title><tiles:getAsString name="title"></tiles:getAsString></title>
<jsp:include page="/WEB-INF/views/include/head-include.jsp"></jsp:include>
</head>
<body id="theme-default" class="full_block">
   <tiles:insertAttribute name="content"></tiles:insertAttribute>
<jsp:include page="/WEB-INF/views/include/bottom-include.jsp"></jsp:include>
</body>
</html>