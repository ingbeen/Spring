<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if ((session.getAttribute("id")==null) || 
	  (!((String)session.getAttribute("id")).equals("admin"))) {
		out.println("<script>");
		out.println("location.href='loginform.me'");
		out.println("</script>");
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원관리 시스템 관리자모드(회원 정보 수정)</title>
</head>
<body>
	<form name="memberupdateform" action="./memberupdate.me" method="post">
		<input type="hidden" name="id" value="${id}">
		<table border=1>
			<tr>
				<td colspan="2" align=center>
					<b><font size=5>회원 정보 수정 페이지</font></b>
				</td>
			</tr>
			<tr><td>수정할 아이디 : </td><td>${id}</td></tr>
			<tr><td>이름 : </td><td><input type="text" name="name"/></td></tr>
			<tr><td>나이 : </td><td><input type="text" name="age" maxlength=2 size=5/></td></tr>
			<tr>
				<td>성별 : </td>
				<td>
					<input type="radio" name="gender" value="남" checked/>남자
					<input type="radio" name="gender" value="여"/>여자
				</td>
			</tr>
			<tr><td>이메일 주소 : </td><td><input type="text" name="email" size=30/></td></tr>
			<tr>
				<td colspan="2" align=center>
					<a href="javascript:memberupdateform.submit()">수정하기</a>&nbsp;&nbsp;
					<a href="javascript:memberupdateform.reset()">다시작성</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>