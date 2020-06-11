<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%
	request.setCharacterEncoding("utf-8");
	if (session.getAttribute("id") == null){
		out.println("<script>");
		out.println("location.href='loginForm.do'");
		out.println("</script>");
	}
	String id = (String)session.getAttribute("id");
	String name = (String)session.getAttribute("name");

%>

<!DOCTYPE html>
<html>
<head>
<title>WebSocket</title>
<script>
	var log = function(s){
		document.getElementById("output").textContent += (s+ "\n");
	}
	w = new WebSocket("ws://localhost:8080/springwebsocket2/broadcasting.do");
	w.onopen = function(){
		alert('WebSocket Connected!!!');
	}
	w.onmessage = function(e){
		log(e.data.toString());
	}
	w.onclose = function(e){
		log("WebSocket Closed");
	}
	w.onerror = function(e){
		log("WebSocket error!!!:" + e.data);
	}

	window.onload = function(){
		document.getElementById("send_button").onclick = function(){
				var input = document.getElementById("input").value;
				w.send("<%=id%>" + ">" + input);
				document.getElementById("input").value = "";
			}
		}
</script>
</head>
<body>
	<input type='text' id='input' placeholder='input message...' size='55' />
	<button id='send_button'>SEND</button>
	대화명 : <%=id%>
	<p/>
	<textarea id='output' readonly rows='30' cols='80'></textarea>
</body>
</html>