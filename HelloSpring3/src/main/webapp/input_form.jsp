<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Insert title here</title>
</head>
<body>
	<form action="input.bo" method="post">
		id : <input type="text" name="id">
		pw : <input type="password" name="pw">
		<input type="submit" value="전송">
	</form>
</body>
</html>
