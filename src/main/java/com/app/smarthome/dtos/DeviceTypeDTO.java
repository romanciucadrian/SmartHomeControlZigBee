package com.app.smarthome.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class DeviceTypeDTO implements Serializable {

    private String id;

    private String name;

    private List<String> mqttTopics;

    private String deviceHexName;
}
