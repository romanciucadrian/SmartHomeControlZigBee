package com.app.smarthome.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommandDTO implements Serializable {

    private String deviceId;
    private String deviceName;
    private String name;
    private String topic;
    private String payload;
}
