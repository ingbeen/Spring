<%@page import="com.spring.memberboard.board.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	BoardVO boardVO = (BoardVO) request.getAttribute("boardVO");
%>
<!DOCTYPE html>
<html>
<head>
<title>게시판 MVC</title>
<script type="text/javascript">
	function modifyboard() {
		modifyform.submit();
	}
</script>
</head>
<body>
	<!-- 게시판 수정  -->
	<form action="boardmodify.bo" method="post" name="modifyform">
		<input type="hidden" name="num" value="<%=boardVO.getNum()%>">
		<input type="hidden" name="id" value="<%=boardVO.getId()%>">
		<table align=center">
			<tr align="center" valign="middle">
				<td colspan="5">게시판 MVC</td>
			</tr>

			<tr>
				<td height="16" style="font-family: 돋움; font-size: 12">
					<div align="center">제 목</div>
				</td>
				<td><input name="subject" size="50" maxlength="100"
					value="<%=boardVO.getSubject()%>"></td>
			</tr>

			<tr>
				<td style="font-family: 돋움; font-size: 12">
					<div align="center">내 용</div>
				</td>
				<td>
					<textarea name="content" cols="67" rows="15"><%=boardVO.getContent()%></textarea>
				</td>
			</tr>

			<tr bgcolor="cccccc">
				<td colspan="2" style="height: 1px;"></td>
			</tr>

			<tr>
				<td colspan="2">&nbsp;</td>
			</tr>

			<tr align="center" valign="middle">
				<td colspan="5"><font size=2> <a
						href="javascript:modifyboard()">[수정]</a>&nbsp;&nbsp; <a
						href="javascript:history.go(-1)">[뒤로]</a>&nbsp;&nbsp;
				</font></td>
			</tr>
		</table>
	</form>
</body>
</html>
