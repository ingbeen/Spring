<%@page import="com.spring.memberboard.board.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String id = null;
	if (session.getAttribute("id") != null) {
		id = (String) session.getAttribute("id");
	}
	ArrayList<BoardVO> boardList = (ArrayList<BoardVO>) request.getAttribute("boardlist");
	int listcount = ((Integer) request.getAttribute("listcount")).intValue();
	int nowpage = ((Integer) request.getAttribute("page")).intValue();
	int maxpage = ((Integer) request.getAttribute("maxpage")).intValue();
	int startpage = ((Integer) request.getAttribute("startpage")).intValue();
	int endpage = ((Integer) request.getAttribute("endpage")).intValue();
%>
<!DOCTYPE html>
<html>
<head>
	<title>MVC 게시판</title>
</head>
<body>
	<!-- 게시판 리스트  -->
	<table align="center">
		<tr align="center" valign="middle">
			<td colspan="4">게시판 MVC</td>
			<td align=right><font size=2>글 개수 : ${listcount }</font></td>
		</tr>
		
		<tr align="center" valign="middle" bordercolor="#333333">
			<td style="font-family: Tahoma; font-size: 8pt;" width="70px" height="26">
				<div align="center">번호</div>
			</td>
			<td style="font-family: Tahoma; font-size: 8pt;" width="320px">
				<div align="center">제목</div>
			</td>
			<td style="font-family: Tahoma; font-size: 8pt;" width="110px">
				<div align="center">작성자</div>
			</td>
			<td style="font-family: Tahoma; font-size: 8pt;" width="110px">
				<div align="center">날짜</div>
			</td>
			<td style="font-family: Tahoma; font-size: 8pt;" width="70px">
				<div align="center">조회수</div>
			</td>
		</tr>
		
		<%
		int num = listcount - ((nowpage - 1) * 10);
		for (int i = 0; i < boardList.size(); i++) {
			BoardVO vo = (BoardVO) boardList.get(i);
		%>
			<tr align="center" valign="middle" bordercolor="#333333"
				onmouseover="this.style.backgroundColor='F8F8F8'"
				onmouseout="this.style.backgroundColor=''">
				<td height="23" style="font-family: Tahoma; font-size: 10pt;">
					<%=num %>
				</td>
				<td style="font-family: Tahoma; font-size: 10pt;">
					<div align="left">
						<%
							if (vo.getRe_lev() != 0) {
						%>
							<%
								for (int a = 0; a <= vo.getRe_lev() * 2; a++) {
							%>
							&nbsp;
							<%} %>
							▶
						<%} else { %>
							▶
						<%} %>
						<a href="./boarddetail.bo?num=<%=vo.getNum()%>"> 
							<%=vo.getSubject()%>
						</a>
					</div>
				</td>
				<td style="font-family: Tahoma; font-size: 10pt;">
					<div align="center"><%=vo.getId()%></div>
				</td>
				<td style="font-family: Tahoma; font-size: 10pt;">
					<div align="center">
						<fmt:formatDate pattern="yyyy-MM-dd" value="<%=vo.getBoarddate()%>" />
					</div>
				</td>
				<td style="font-family: Tahoma; font-size: 10pt;">
					<div align="center"><%=vo.getReadcount()%></div>
				</td>
			</tr>
		<%
			num--;
		} 
		%>
		
		<tr align=center height=20>
			<td colspan=7 style="font-family: Tahoma; font-size: 10pt;">
				<%if (nowpage <= 1) { %> 
					[이전]
				<%} else { %> 
					<a href="boardlist.bo?page=<%=nowpage - 1%>">[이전]</a>
				<%} %> 
				<%for (int a = startpage; a <= endpage; a++) {
 					if (a == nowpage) {%>
 						[<%=a%>]
 					<%} else { %> 
 						<a href="boardlist.bo?page=<%=a%>">[<%=a%>]</a>
					<%} %> 
				<%} %>
				<%if (nowpage >= maxpage) { %> 
					[다음] 
				<%} else { %>
					<a href="boardlist.bo?page=<%=nowpage + 1%>">[다음]</a> 
					<%} %>
			</td>
		</tr>
		
		<tr>
			<td colspan="2" align="left">
				<a href="logout.me">[로그아웃]</a>
			</td>
			<td colspan="3" align="right">
				<%if (id != null && id.equals("admin")) { %>
				<a href="memberlist.me">[회원관리]</a>
				<%} %> 
				<a href="boardwriteform.bo">[글쓰기]</a>
			</td>
		</tr>
	</table>
</body>
</html>
