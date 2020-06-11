package com.spring.springwebsocket2;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


//websocket���� �������� ���μ����� ������ �� �ִ�.
public class SocketHandler extends TextWebSocketHandler{

	HttpServletRequest request;
	
	//�����ϴ� ����ڿ� ���� ������ �����ϱ� ���� ����
	private Set<WebSocketSession> sessionSet = new HashSet<WebSocketSession>();
	
	public SocketHandler() {
		super();
		System.out.println("create SocketHandler instance!");
	}
	
	//Ŭ���̾�Ʈ���� ������ ������ ��� �߻��ϴ� �̺�Ʈ
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
		super.afterConnectionClosed(session, status);
		
		sessionSet.remove(session);
		System.out.println("remove session!");
	}
	
	//Ŭ���̾�Ʈ���� �����Ͽ� ������ ��� �ڵ����� �߻��ϴ� �̺�Ʈ, session���� �����û�� client session ������ ���.
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		super.afterConnectionEstablished(session);
			
		sessionSet.add(session);
		System.out.println("add session!");
	}
	
	//Ŭ���̾�Ʈ���� send�� �̿��ؼ� �޽��� �߼��� �� ��� �ڵ����� �޼ҵ� ȣ�� �̺�Ʈ �ڵ鸵
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception{
		super.handleMessage(session, message);
		
		//session.getAttributes() : HandshakeInterceptor�� beforeHandshake()�޼��忡�� ������ map�� �����´�.
		Map<String, Object> map = session.getAttributes();
		/*
		String userName = (String)map.get("userName");
		System.out.println("������ �̸� : " + userName);
		*/
		String userId = (String)map.get("userId");
		System.out.println("������ ID : " + userId);
		
		for(WebSocketSession client_session : this.sessionSet) {
			if(client_session.isOpen()) {
				try {
					client_session.sendMessage(message);
				}catch(Exception ignored) {
					System.out.println("fail to send message!" + ignored);
				}
			}
		}
	}
	
	// ���� ���� �߻��� �� ȣ��
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception{
		System.out.println("web socket error!" + exception);
	}
	
	//�޽����� �� ��� �����ؼ� ���� �� �ִ��� ���θ� �����ϴ� �޼ҵ�
	public boolean supportsPartialMessage() {
		System.out.println("call method!");
		
		return false;
	}
	
}