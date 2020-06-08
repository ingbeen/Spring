<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList" %>
<%
	ArrayList<String> list_title = (ArrayList<String>)request.getAttribute("list_title");
	ArrayList<String> list_point = (ArrayList<String>)request.getAttribute("list_point");
	String sel = (String)request.getAttribute("sel");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>네이버 영화 랭킹 크롤링</title>
</head>
<body>
<br />
<table border="0" align="center">
<tr>
	<td><a href="crawl.do?sel=cnt">
		<img src="${pageContext.request.contextPath}/resources/image/cnt.gif" /></a></td>
	<td><a href="crawl.do?sel=cur">
		<img src="${pageContext.request.contextPath}/resources/image/cur.gif" /></a></td>
	<td><a href="crawl.do?sel=pnt">
		<img src="${pageContext.request.contextPath}/resources/image/pnt.gif" /></a></td>
</tr>
</table>
<%
if (list_point.size() == 0)
{
%>
	<table border="0" align="center">
		<tr>
			<th><font size="2">순위</font></th>
			<th><font size="2">영화제목(조회순)</font></th>
		</tr>
	<%
	for (int i=0; i<list_title.size(); i++)
	{
	%>
		<tr>
			<td><font size="2"><%=i+1 %></font></td>
			<td><font size="2"><%=list_title.get(i) %></font></a></td>
		</tr>
	<%
	}
	%>
	</table>
<%
}
else {
%>
	<table border="0" align="center">
		<tr>
			<th><font size="2">순위</font></th>
		<% if (sel.equals("cur")) {%> 
			<th><font size="2">영화제목(상영)</font></th>
		<% }else {%>
			<th><font size="2">영화제목(모두)</font></th>
		<% } %>
			<th><font size="2">평점</font></th>
		</tr>
	<%
	for (int i=0; i<list_title.size(); i++)
	{
	%>
		<tr>
			<td><font size="2"><%=i+1 %></font></td>
			<td><font size="2"><%=list_title.get(i) %></font></a></td>
			<td><font size="2"><%=list_point.get(i) %></font></a></td>
		</tr>
	<%
	}
	%>
	</table>
<%
}
%>
</body>
</html>