package com.quest.global.SmartHome.services.impl;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface IMqttService {

    void doPublish(String deviceNameCommand) throws MqttException;

    void doSubscribe();
}
