package com.app.smarthome.service;

import com.app.smarthome.exceptions.InvalidArgumentException;

public interface MqttMessageProcessor {

    void processBridgeStatus0AndSaveDevice(String message) throws InvalidArgumentException;

    void processBridgeResultAndSaveDevice(String message) throws InvalidArgumentException;

    void processNspanelSwitchStatus0AndSaveDevice(String message) throws InvalidArgumentException;

    void processNspanelSwitchResultAndSaveDevice(String message) throws InvalidArgumentException;
}
