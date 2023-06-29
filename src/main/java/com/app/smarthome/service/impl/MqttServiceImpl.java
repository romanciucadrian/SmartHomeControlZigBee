package com.app.smarthome.service.impl;

import com.app.smarthome.service.MqttService;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MqttServiceImpl implements MqttService {

    private final MqttConnectOptions mqttConnectOptions;
    private final MqttClient mqttClient;

    @Override
    public void connectToMQTT() throws MqttException {
        mqttClient.connect(this.mqttConnectOptions);
    }

    @Override
    public Boolean isConnectedToMQTT() {
        return mqttClient.isConnected();
    }

    @Override
    public void sendMessage(String topic, String payload) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(payload.getBytes());
        mqttMessage.setQos(2);
        mqttClient.publish(topic, mqttMessage);
    }
}
