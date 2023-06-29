package com.app.smarthome.models.devices.bridge.status;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BridgeDeviceStatus {
    private String deviceName;
    private String topic;
    private Long power;
    private Long ledState;
}
