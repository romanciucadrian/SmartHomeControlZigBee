package com.quest.global.SmartHome.services.impl;

import org.springframework.web.socket.WebSocketSession;

public interface IWebSocketService {

    void subscribeToTopic(WebSocketSession session, String Topic);
}
