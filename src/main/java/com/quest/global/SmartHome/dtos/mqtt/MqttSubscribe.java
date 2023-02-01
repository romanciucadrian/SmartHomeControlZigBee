package com.quest.global.SmartHome.dtos.mqtt;

import lombok.Data;

@Data
public class MqttSubscribe {

    private String topic;

    private String message;
}
