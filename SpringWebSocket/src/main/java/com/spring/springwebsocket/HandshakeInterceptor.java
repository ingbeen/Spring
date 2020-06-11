package com.spring.springwebsocket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

// 웹소켓에서 HttpSession 객체를 사용하도록하기 위한 클래스 정의
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	
	// ServerHttpRequest -> HttpServletRequest
	// 변환하여 session객체의 정보를 얻는 것이 주된 목적이다
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, 
			WebSocketHandler wsHandler, Map<String, Object> map) throws Exception {
		// 위의 파라미터 중 attributes 에 값을 저장하면 웹소켓 핸들러 클래스의 WebSocketSession에 전달된다
		System.out.println("Before Handshake");
		ServletServerHttpRequest ssreq = (ServletServerHttpRequest) request;
		System.out.println("URI : " + request.getURI());
		HttpServletRequest req = ssreq.getServletRequest();
		String id = (String) req.getParameter("id");
		/*
		 * HttpSession에 저장된 이용자의 아이디를 추출하는 경우 String id = (String)
		 * req.getSession().getAttribute("id"); String id = "admin";
		 */
		map.put("userId", id);
		System.out.println("HttpSession에 저장된 id : " + id);

		return super.beforeHandshake(request, response, wsHandler, map);
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, 
			WebSocketHandler wsHandler, Exception ex) {
		System.out.println("After Handshake");

		super.afterHandshake(request, response, wsHandler, ex);;
	}
}
