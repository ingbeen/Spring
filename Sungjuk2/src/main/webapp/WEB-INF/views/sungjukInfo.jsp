<%@ page language="java" contentType="text/html; charset=utf-8"%>
<html>
<head>
<title>성적관리 시스템(성적입력)</title>
</head>
<body>
	<table align="center" border="1">
		<tr>
			<th colspan="2" align="center">성적정보 상세보기</th>
		</tr>
		<tr align="center">
			<td width="75px">학번</td>
			<td width="125px">${sungjukVO.hakbun }</td>
		</tr>
		<tr align="center">
			<td>이름</td>
			<td>${sungjukVO.irum }</td>
		</tr>
		<tr align="center">
			<td>국어</td>
			<td>${sungjukVO.kor }</td>
		</tr>
		<tr align="center">
			<td>영어</td>
			<td>${sungjukVO.eng }</td>
		</tr>
		<tr align="center">
			<td>수학</td>
			<td>${sungjukVO.math }</td>
		</tr>
		<tr align="center">
			<td>총합계</td>
			<td>${sungjukVO.tot }</td>
		</tr>
		<tr align="center">
			<td>평균</td>
			<td>${sungjukVO.avg }</td>
		</tr>
		<tr align="center">
			<td>등급</td>
			<td>${sungjukVO.grade }</td>
		</tr>
		<tr align="center">
			<td colspan="2" align=center>
				<a href="sungjuklist.su">뒤로가기</a>
			</td>
		</tr>
	</table>
</body>
</html>