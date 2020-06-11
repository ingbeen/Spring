package com.spring.springwebsocket;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// 서버단의 SocketHandler 정의
// Websocket에서 서버단의 프로세스를 정의할 수 있다
public class SocketHandler extends TextWebSocketHandler {
	
	HttpServletRequest request;
	
	// 접속하는 사용자에 대한 세션을 보관하기 위해 정의
	private Set<WebSocketSession> sessionSet = new HashSet<WebSocketSession>();
	
	// servlet-context.xml에서 bean객체를 만들때 생성된다
	public SocketHandler() {
		super();
		System.out.println("create SocketHandler instance");
	}
	
	// 클라이언트에서 연결을 종료할 경우 발생하는 이벤트
	@Override
	public void afterConnectionClosed(WebSocketSession session, 
			CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		
		sessionSet.remove(session);
		System.out.println("remove session");
	}
	
	// 클라이언트에서 접속을 하여 성공할 경우 발생하는 이벤트
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		
		sessionSet.add(session);
		System.out.println("add session");
	}
	
	// 클라이언트에서 send를 이용해서 메시지 발송을 한 경우 이벤트 핸들링
	@Override
	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
		super.handleMessage(session, message);
		
		// session.getAttributes()
		// HandshakeInterceptor의 beforeHandshake()메서드에서 저장한 map을 가져온다
		Map<String, Object> map = session.getAttributes();
		String userId = (String) map.get("userId");
		System.out.println("전송자 아이디 : " + userId);
		
		for (WebSocketSession client_session : this.sessionSet) {
			if (client_session.isOpen()) {
				try {
					client_session.sendMessage(message);
				} catch (Exception ignored) {
					System.out.println("fail to send message" + ignored);
				}
			}
		}
	}
	
	// 전송 에러 발생할 때 호출
	@Override
	public void handleTransportError(WebSocketSession session, 
			Throwable exception) throws Exception {
		
		System.out.println("web socket error" + exception);
	}
	
	// 메시지가 긴 경우 분할해서 보낼 수 있는지 여부를 결정하는 메소드
	// true이면 웹소켓메시지를 여러번 호출해서 받아올 수 있다.
	@Override
	public boolean supportsPartialMessages() {
		System.out.println("call method");
		
		return false;
	}
}
