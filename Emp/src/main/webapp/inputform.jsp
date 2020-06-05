<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="inputProcess.me" method="post">
		<table>
			<tr>
				<td>EMPNO</td>
				<td><input type="text" name="empno" size="10" maxlength="10"></td>
			</tr>
			<tr>
				<td>ENAME</td>
				<td><input type="text" name="ename" size="10" maxlength="10"></td>
			</tr>
			<tr>
				<td>JOB</td>
				<td><input type="text" name="job" size="10" maxlength="10"></td>
			</tr>
			<tr>
				<td>MGR</td>
				<td><input type="text" name="mgr" size="10" maxlength="10"></td>
			</tr>
			<tr>
				<td>SAL</td>
				<td><input type="text" name="sal" size="10" maxlength="10"></td>
			</tr>
			<tr>
				<td>COMM</td>
				<td><input type="text" name="comm" size="10" maxlength="10"></td>
			</tr>
			<tr>
				<td>DEPTNO</td>
				<td><input type="text" name="deptno" size="10" maxlength="10"></td>
			</tr>
			<tr align="center">
				<td colspan="2">
					<input type="submit" value="입력">
					<input type="reset" value="다시작성">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>