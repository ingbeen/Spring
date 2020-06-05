<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>업로드 완료</h1>
	이름 : ${name}
	<br>
	<!-- jstl 태그선언 했음 -->
	<!-- not empty = noy null -->
	<c:if test="${not empty fileName }">
		파라미터이름 : ${paramName }<br>
		파일명 : ${fileName }<br>
		파일사이즈 : ${fileSize }<br>
		<a href="${downlink }">다운로드</a>
		<br>

		<!-- server.xml에 등록된  path="/springfileupload/upload" 가 들어오면
		docBase="C:/Project156/upload"로 경로가 변경되어
		해당 경로의 storedFileName이랑 일치되는 파일을 읽어온다 -->
		<!-- spring 태그선언 했음 -->
		<img src="<spring:url value='/upload/${storedFileName }'/>">
		<br>
		<!-- spring 태그선언 안해도 됨 -->
		<img src="/springfileupload/upload/${storedFileName }" />
	</c:if>
</body>
</html>
