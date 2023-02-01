package com.quest.global.SmartHome.services;

import com.quest.global.SmartHome.configuration.MqttConfig;
import com.quest.global.SmartHome.services.impl.IWebSocketService;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class WebSocketService implements IWebSocketService {

    private final Logger logger = LoggerFactory.getLogger(WebSocketService.class);
    private final MqttConfig mqttConfig;
    private final ExecutorService executorService
            = Executors.newFixedThreadPool(2);

    public WebSocketService(MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
    }

    @Async
    public void subscribeToTopic(WebSocketSession session, String subscribeTopic) {

        executorService.submit(() -> {
            try {
                mqttConfig.getClient()
                        .subscribe(subscribeTopic, 2, null, new IMqttActionListener() {

                            @Override
                            public void onSuccess(IMqttToken iMqttToken) {
                                logger.info("Successfully subscribed to topic: {}", subscribeTopic);
                            }

                            @Override
                            public void onFailure(IMqttToken iMqttToken, Throwable exception) {
                                logger.error("Error occurred while subscribing to topic: {}", subscribeTopic, exception);
                            }
                        }, new IMqttMessageListener() {
                            @Override
                            public void messageArrived(String topic, MqttMessage message) throws Exception {

                                String arrivedMessage
                                        = new String(message.getPayload());

                                logger.info("The received message is: {}" + arrivedMessage);

                                session.sendMessage(new TextMessage(arrivedMessage));
                            }
                        });
            } catch (MqttException e) {
                logger.error("Error occurred while subscribing to topic {}", subscribeTopic, e);
            }
        });
    }
}
