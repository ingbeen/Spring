package com.spring.springwebsocket;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// �������� SocketHandler ����
// Websocket���� �������� ���μ����� ������ �� �ִ�
public class SocketHandler extends TextWebSocketHandler {
	
	HttpServletRequest request;
	
	// �����ϴ� ����ڿ� ���� ������ �����ϱ� ���� ����
	private Set<WebSocketSession> sessionSet = new HashSet<WebSocketSession>();
	
	// servlet-context.xml���� bean��ü�� ���鶧 �����ȴ�
	public SocketHandler() {
		super();
		System.out.println("create SocketHandler instance");
	}
	
	// Ŭ���̾�Ʈ���� ������ ������ ��� �߻��ϴ� �̺�Ʈ
	@Override
	public void afterConnectionClosed(WebSocketSession session, 
			CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		
		sessionSet.remove(session);
		System.out.println("remove session");
	}
	
	// Ŭ���̾�Ʈ���� ������ �Ͽ� ������ ��� �߻��ϴ� �̺�Ʈ
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		
		sessionSet.add(session);
		System.out.println("add session");
	}
	
	// Ŭ���̾�Ʈ���� send�� �̿��ؼ� �޽��� �߼��� �� ��� �̺�Ʈ �ڵ鸵
	@Override
	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
		super.handleMessage(session, message);
		
		// session.getAttributes()
		// HandshakeInterceptor�� beforeHandshake()�޼��忡�� ������ map�� �����´�
		Map<String, Object> map = session.getAttributes();
		String userId = (String) map.get("userId");
		System.out.println("������ ���̵� : " + userId);
		
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
	
	// ���� ���� �߻��� �� ȣ��
	@Override
	public void handleTransportError(WebSocketSession session, 
			Throwable exception) throws Exception {
		
		System.out.println("web socket error" + exception);
	}
	
	// �޽����� �� ��� �����ؼ� ���� �� �ִ��� ���θ� �����ϴ� �޼ҵ�
	// true�̸� �����ϸ޽����� ������ ȣ���ؼ� �޾ƿ� �� �ִ�.
	@Override
	public boolean supportsPartialMessages() {
		System.out.println("call method");
		
		return false;
	}
}
