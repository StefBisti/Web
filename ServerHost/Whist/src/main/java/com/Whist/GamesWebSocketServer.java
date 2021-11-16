package com.Whist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/GamesWebSocketServer")
public class GamesWebSocketServer {
	
	Session session;
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void open(Session session) {
		clients.add(session);
	}
	
	@OnClose
	public void close(Session session) {
		clients.remove(session);
	}
	
	@OnMessage
	public void handleMessage(String message, Session session) throws IOException{
		synchronized (clients) {
			for(Session client : clients) {
				if(!client.equals(session)) {
					client.getAsyncRemote().sendText(message);
				}
			}
		}
	}
}
