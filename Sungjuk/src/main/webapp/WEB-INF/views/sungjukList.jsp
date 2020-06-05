<%@page import="com.spring.sungjuk.SungjukVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	ArrayList<SungjukVO> sungjukList = (ArrayList<SungjukVO>) request.getAttribute("sungjukList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>성적관리 시스템</title>
</head>
<body>
	<table align="center" border="1">
		<tr align="center">
			<th colspan="2" width="200px">성적 목록</th>
			<td width="100px"><a href="insertform.su">성적입력</a></td>
		</tr>
		<%
		if (sungjukList != null) {
			for (int i = 0; i < sungjukList.size(); i++) {
				SungjukVO vo = sungjukList.get(i);
		%>
		<tr align="center">
			<td width="100px"><a href="selectsungjuk.su?hakbun=<%=vo.getHakbun()%>"><%=vo.getHakbun()%></a></td>
			<td><a href="updateform.su?hakbun=<%=vo.getHakbun()%>&irum=<%=vo.getIrum()%>">수정</a></td>
			<td><a href="deletesungjuk.su?hakbun=<%=vo.getHakbun()%>">삭제</a></td>
		</tr>
		<%
			}
		}
		%>
	</table>
</body>
</html>