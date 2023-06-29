package com.app.smarthome.models.devices.nspanel.status;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NSPanelDeviceStatus {

    private String deviceName;
    private String topic;
    private Long power;
}
