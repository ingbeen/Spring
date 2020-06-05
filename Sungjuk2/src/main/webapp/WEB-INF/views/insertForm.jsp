<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<title>성적관리 시스템(성적입력)</title>
</head>
<body>
	<form name="insertform" action="./insertsungjuk.su" method="post">
		<table align="center" border="1">
			<tr>
				<th colspan="2" align="center">성적입력 페이지</th>
			</tr>
			<tr align="center">
				<td width="75px">학번</td>
				<td width="125px"><input type="text" name="hakbun" maxlength=4 /></td>
			</tr>
			<tr align="center">
				<td>이름</td>
				<td><input type="text" name="irum" /></td>
			</tr>
			<tr align="center">
				<td>국어</td>
				<td><input type="text" name="kor" maxlength=3 /></td>
			</tr>
			<tr align="center">
				<td>영어</td>
				<td><input type="text" name="eng" maxlength=3 /></td>
			</tr>
			<tr align="center">
				<td>수학</td>
				<td><input type="text" name="math" maxlength=3 /></td>
			</tr>
			<tr align="center">
				<td colspan="2" align=center>
					<a href="javascript:insertform.submit()">성적입력</a>&nbsp;
					<a href="javascript:insertform.reset()">다시작성</a>&nbsp;
					<a href="sungjuklist.su">뒤로가기</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>