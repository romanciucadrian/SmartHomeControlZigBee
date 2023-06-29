package com.app.smarthome.exceptions;

public class MqttConnectionException extends Exception {
    public MqttConnectionException(String message) {
        super(message);
    }
}
