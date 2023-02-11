package com.app.smarthome.dtos.mqtt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MqttPublish {

    private String topic;

    private String payload;
}
