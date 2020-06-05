<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<table border="1" align="center">
		<!-- 아이디 -->
		<tr> 
			<th width="130px">아이디</th>
			<td width="350px">${id}</td>
		</tr>
		
		<!-- 비밀번호 -->
		<tr>
			<th>비밀번호</th>
			<td>${password1}</td>
		</tr>
		
		<!-- 주민번호 -->
		<tr>
			<th>주민번호</th>
			<td>
				${jumin1} - 
				${jumin2}
			</td>
		</tr>
		
		<!-- 성별 -->
		<tr>
			<th>성별</th>
			<td>${sex}</td>
		</tr>
		
		<!-- 전화번호 -->
		<tr>
			<th>전화번호</th>
			<td>
				${tel1} - ${tel2} - ${tel3}	
			</td>
		</tr>
		
		<!-- 이메일 -->
		<tr>
			<th>이메일</th>
			<td>
				${emailFront}@${emailBack}
			</td>
		</tr>
		
		<!-- 취미 -->
		<tr>
			<th>취미</th>
			<td>${hobby}</td>
		</tr>
		
		<!-- 자기소개 -->
		<tr>
			<th>자기소개서</th>
			<td>
				${intro}
			</td>
		</tr>
	</table>
</body>
</html>