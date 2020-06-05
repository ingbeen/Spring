<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.spring.emp.EmpVO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1" align="center">
		<tr>
			<th width="80">EMPNO</th>
			<th width="120">ENAME</th>
			<th width="120">JOB</th>
			<th width="80">MGR</th>
			<th width="150">HIREDATE</th>
			<th width="100">SAL</th>
			<th width="100">COMM</th>
			<th width="80">DEPTNO</th>
		</tr>
		<%
		ArrayList<EmpVO> list = (ArrayList<EmpVO>) request.getAttribute("list");
		for (int i = 0; i< list.size(); i++) {
			EmpVO empvo = (EmpVO) list.get(i);
		%>
			<tr>
				<td>&nbsp;<%=empvo.getEmpno() %></td>
				<td>&nbsp;<%=empvo.getEname() %></td>
				<td>&nbsp;<%=empvo.getJob() %></td>
				<%
				if (empvo.getMgr() == 0) {
				%>
					<td>&nbsp;</td>
				<%} else {%>
					<td>&nbsp;<%=empvo.getMgr() %></td>
				<%} %>
				<td>&nbsp;<%=empvo.getHiredate() %></td>
				<td>&nbsp;<%=empvo.getSal() %></td>
				<td>&nbsp;<%=empvo.getComm() %></td>
				<td>&nbsp;
					<a href="selectDept.me?deptno=<%=empvo.getDeptno() %>"><%=empvo.getDeptno() %></a>
				</td>
			</tr>
		<%} %>
		<tr>
			<td colspan="8" align="center"><a href="/emp">홈으로</a></td>
		</tr>
	</table>
</body>
</html>