package com.app.smarthome.service;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface MqttService {

    void connectToMQTT() throws MqttException;

    Boolean isConnectedToMQTT();

   void sendMessage(String topic, String payload) throws MqttException;
}
