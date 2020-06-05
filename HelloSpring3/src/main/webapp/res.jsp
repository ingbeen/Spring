<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Insert title here</title>
</head>
<body>
id : ${id}<br>
pw : ${pw}<br><br>

id : <%=request.getAttribute("id") %><br>
pw : <%=request.getAttribute("pw") %><br>
</body>
</html>
