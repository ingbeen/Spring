<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>
<!-- 단순히 값만 읽어올때 -->
<P>  The time on the server is ${serverTime}. </P> 
<!-- 값을 수정하거나 이용할때 -->
<P>  The time on the server is <%=request.getAttribute("serverTime") %>. </P> 
</body>
</html>
