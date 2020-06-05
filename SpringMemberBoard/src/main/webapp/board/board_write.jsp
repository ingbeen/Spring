<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String id = (String) session.getAttribute("id");
%>

<html>
<head>
	<title>MVC 게시판</title>
</head>
<body>
	<form action="boardwrite.bo" method="post" name="boardform">
		<!-- 아이디를 리퀘스트로 보내기 위해 히든객체로 만들었다 -->
		<input type="hidden" name="id" value="<%=id%>">
		
		<table align="center">
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
				<td>
					<input name="subject" type="text" size="50" maxlength="100" value="" />
				</td>
			</tr>
			
			<tr>
				<td style="font-family: 돋움; font-size: 12">
					<div align="center">내 용</div>
				</td>
				<td>
					<textarea name="content" cols="67" rows="15"></textarea>
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
					<a href="javascript:addboard()">[등록]</a>&nbsp;&nbsp;
					<a href="javascript:history.go(-1)">[뒤로]</a>
				</td>
			</tr>
		</table>
	</form>
</body>
<script>
	function addboard() {
		boardform.submit();
	}
</script>
</html>
