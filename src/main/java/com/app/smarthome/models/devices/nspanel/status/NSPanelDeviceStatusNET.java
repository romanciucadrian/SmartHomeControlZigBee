package com.app.smarthome.models.devices.nspanel.status;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NSPanelDeviceStatusNET {

    private String hostName;
    private String ipAddress;
    private String gateway;
    private String subnetMask;
}
