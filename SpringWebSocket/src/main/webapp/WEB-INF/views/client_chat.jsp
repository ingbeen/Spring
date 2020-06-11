<!-- 
순서
1.
서버가 구동되면 servlet-context.xml에 있는 bean객체가 자동으로 생성된다 = socketHandler

<beans:bean id="socketHandler" class="com.spring.springwebsocket.SocketHandler" />

2.
controller에 의해 해당 jsp파일이 실행된다

3.
스크립트 태그 안의 아래 구문에 의해
w = new WebSocket("ws://localhost:8080/springwebsocket/broadcasting?id=guest"); 
w에는 1번에서 생성된 socketHandler객체가 할당된다

참고 : servlet-context.xml의
<websocket:mapping handler="socketHandler" path="/broadcasting"/>

동시에 위 참고 태그안에 아래의 구문이 실행되어
HandshakeInterceptor클래스의 bean객체가 생성된다
<websocket:handshake-interceptors>
	<beans:bean class="com.spring.springwebsocket.HandshakeInterceptor" />
</websocket:handshake-interceptors>

4.
HandshakeInterceptor객체는 생성되면서
자동으로 beforeHandshake, afterHandshake 메소드를 실행시키며
위 메소드들은 ServerHttpRequest -> HttpServletRequest 변환하여 session객체의 정보를 얻어온다

5.
socketHandler에서 아래의 두가지 메소드가 자동실행된다
supportsPartialMessages 메시지가 긴 경우 분할해서 보낼 수 있는지 여부를 결정하는 메소드

클라이언트에서 연결을 종료할 경우 발생하는 이벤트
afterConnectionEstablished가 실행되어 Set<WebSocketSession> sessionSet에 add하게 된다

정리
서버가 구동되면 홈컨트로러에 의해 jsp 이동 -> SocketHandler 할당되면서 HandshakeInterceptor로 세션 정보 얻어옴
-> 추후 jsp과 SocketHandler로 정보 교류

추가 내용
w.onopen : 접속이 성공되면 실행
w.onmessage : 새로운 메세지가 생성되면 실행
w.onclose : 연결이 해제되면 실행
w.onerror : 오류가 나면 실행

-->

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
	<meta charset="utf-8">
	<title>WebSocket</title>
	<script>
		// 기존 대화내용에 새로운 대화를 더한다
		var log = function(s) {
			document.getElementById("output").textContent += (s + "\n");
		}
		
		// w = new WebSocket("ws://localhost:8080/springwebsocket/broadcasting");
		// servlet-context.xml에서 경로가 broadcasting이 들어오면
		// 맵핑된 socketHandler.java에 정의된 클래스를 할당 받는다
		w = new WebSocket("ws://localhost:8080/springwebsocket/broadcasting?id=guest"); 
		w.onopen = function() {
			alert("WebSocket Connected");
		}
		// SocketHandler의 handleMessage메소드의 sendMessage함수가 실행되면
		// onmessage가 실행되면서 위에서 정의한 log메소드가 실행된다.
		// 파라미터인 e객체는 입력한 메시지가 담겨있다
		w.onmessage = function(e) {
			log(e.data.toString());
		}
		w.onclose = function() {
			log("WevSocket Closed");
		}
		w.onerror = function() {
			log("WevSocket error");
		}
		
		window.onload = function() {
			document.getElementById("send_button").onclick = function() {
				if (document.getElementById("nicname").value == "") {
					alert("별명을 입력하세요");
				} else {
					var nicname = document.getElementById("nicname").value;
					var input = document.getElementById("input").value;
					// w.send()를 하게 되면
					// socketHandler.java의 메소드 중 handleMessage가 실행된다
					// 파라미터의 내용은 WebSocketMessage ? message에 전달된다
					w.send(nicname + "> " + input);
				}
			}
		}
	</script>
</head>
<body>
	<input type="text" id="input" placeholder="input message" size="55">
	<button id="send_button">Send</button>
	대화명<input type="text" id="nicname" placeholder="대화명 입력" size="10">
	<p/>
	<textarea id="output" rows="30" cols="80" readonly></textarea>
</body>
</html>
