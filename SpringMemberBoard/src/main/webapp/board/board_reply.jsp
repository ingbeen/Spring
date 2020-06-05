<%@page import="com.spring.memberboard.board.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String id = null;
if (session.getAttribute("id") != null) {
	id = (String) session.getAttribute("id");
}

BoardVO boardVO = (BoardVO) request.getAttribute("boardVO");
%>
<html>
<head>
	<title>MVC 게시판(답글 쓰기)</title>
	<script>
		function replyboard() {
			boardform.submit();
		}
	</script>
</head>
<body>
	<!-- 게시판 답변 -->
	<form action="boardreply.bo" method="post" name="boardform">
		<input type="hidden" name="num" value="<%=boardVO.getNum()%>">
		<input type="hidden" name="re_ref" value="<%=boardVO.getRe_ref()%>">
		<input type="hidden" name="re_lev" value="<%=boardVO.getRe_lev()%>">
		<input type="hidden" name="re_seq" value="<%=boardVO.getRe_seq()%>">
		<input type="hidden" name="id" value="<%=id%>">
		<table  align="center">
			<tr align="center" valign="middle">
				<td colspan="5">MVC 게시판</td>
			</tr>
			
			<tr>
				<td style="font-family: 돋움; font-size: 12" height="16">
					<div align="center">글쓴이</div>
				</td>
				<td><%=id%></td>
			</tr>
			
			<tr>
				<td style="font-family: 돋움; font-size: 12" height="16">
					<div align="center">제 목</div>
				</td>
				<td><input name="subject" type="text" size="50"
					maxlength="100" value="Re: <%=boardVO.getSubject()%>" /></td>
			</tr>
			
			<tr>
				<td style="font-family: 돋움; font-size: 12">
					<div align="center">내 용</div>
				</td>
				<td><textarea name="content" cols="67" rows="15"></textarea>
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
					<a href="javascript:replyboard()">[등록]</a>&nbsp;&nbsp;
					<a href="javascript:joinform.reset()">[다시작성]</a>&nbsp;&nbsp;
					<a href="javascript:history.go(-1)">[뒤로]</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
