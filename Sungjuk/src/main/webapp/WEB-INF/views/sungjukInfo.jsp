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
			<td width="125px">${sungjukVO.getHakbun() }</td>
		</tr>
		<tr align="center">
			<td>이름</td>
			<td>${sungjukVO.getIrum() }</td>
		</tr>
		<tr align="center">
			<td>국어</td>
			<td>${sungjukVO.getKor() }</td>
		</tr>
		<tr align="center">
			<td>영어</td>
			<td>${sungjukVO.getEng() }</td>
		</tr>
		<tr align="center">
			<td>수학</td>
			<td>${sungjukVO.getMath() }</td>
		</tr>
		<tr align="center">
			<td>총합계</td>
			<td>${sungjukVO.getTot() }</td>
		</tr>
		<tr align="center">
			<td>평균</td>
			<td>${sungjukVO.getAvg() }</td>
		</tr>
		<tr align="center">
			<td>등급</td>
			<td>${sungjukVO.getGrade() }</td>
		</tr>
		<tr align="center">
			<td colspan="2" align=center>
				<a href="sungjuklist.su">뒤로가기</a>
			</td>
		</tr>
	</table>
</body>
</html>