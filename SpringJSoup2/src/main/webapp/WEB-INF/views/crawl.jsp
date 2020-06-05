<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String title = (String) request.getAttribute("title");
	ArrayList<String> list_text = (ArrayList<String>) request.getAttribute("list_text");
	ArrayList<String> list_link = (ArrayList<String>) request.getAttribute("list_link");
%>
<html>
<head>
	<title>네이버 스포츠 뉴스 크롤링</title>
</head>
<body>
<table align="center">
	<tr>
		<th><font size="5"><%=title %></font></th>
	</tr>
	<tr>
		<th>&nbsp;</th>
	</tr>
	<%
	for(int i = 0; i <list_text.size(); i++) {
	%>
	<tr>
		<td>
			<a href=<%=list_link.get(i).toString() %>><%=list_text.get(i).toString() %></a>
		</td>
	</tr>
	<%} %>
</table>
</body>
</html>
