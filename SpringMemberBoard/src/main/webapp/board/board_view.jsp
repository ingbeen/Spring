<%@page import="com.spring.memberboard.board.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	BoardVO boardVO = (BoardVO) request.getAttribute("boardVO");
%>
<html>
<head>
	<title>MVC 게시판</title>
</head>
<body>
	<!-- 게시판 수정 -->
	<table align="center">
		<tr align="center" valign="middle">
			<td colspan="5">MVC 게시판(상세보기)</td>
		</tr>
		
		<tr>
			<td style="font-family: 돋움; font-size: 12" height="16">
				<div align="center">제 목&nbsp;&nbsp;</div>
			</td>
			<td style="font-family: 돋움; font-size: 12">
				<%=boardVO.getSubject()%>
			</td>
		</tr>
		
		<tr bgcolor="cccccc">
			<td colspan="2" style="height: 1px;"></td>
		</tr>
		
		
		<tr>
			<td style="font-family: 돋움; font-size: 12" height="16">
				<div align="center">글쓴이&nbsp;&nbsp;</div>
			</td>
			<td style="font-family: 돋움; font-size: 12">
				<%=boardVO.getId()%>
			</td>
		</tr>
		
		<tr bgcolor="cccccc">
			<td colspan="2" style="height: 1px;"></td>
		</tr>
		
		<tr>
			<td style="font-family: 돋움; font-size: 12">
				<div align="center">내 용</div>
			</td>
			<td style="font-family: 돋움; font-size: 12">
				<table border=0 width=490 height=250 style="table-layout: fixed">
					<tr>
						<td valign=top style="font-family: 돋움; font-size: 12">
							<%=boardVO.getContent()%>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr bgcolor="cccccc">
			<td colspan="2" style="height: 1px;"></td>
		</tr>
		
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		
		<tr align="center" valign="middle">
			<td colspan="5">
				<font size=2>
					<a href="boardreplyform.bo?num=<%=boardVO.getNum()%>">[답변]</a>&nbsp;&nbsp;
					<a href="boardmodifyform.bo?num=<%=boardVO.getNum()%>">[수정]</a>&nbsp;&nbsp;
					<a href="boarddelete.bo?num=<%=boardVO.getNum()%>">[삭제]</a>&nbsp;&nbsp;
					<a href="boardlist.bo">[목록]</a>&nbsp;&nbsp;
				</font>
			</td>
		</tr>
	</table>
</body>
</html>
