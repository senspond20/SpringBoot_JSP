<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Spring Boot Application with JSP</title>
</head>
<body>
	Hello, Spring Boot App 
	
	<c:if test="${not empty data}">
		<div> data : <span>${data }</span></div>
	</c:if>
</body>
</html>