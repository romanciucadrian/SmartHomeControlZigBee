package com.app.smarthome.models.devices.bridge.status;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BridgeDeviceStatusNET {
    private String hostName;
    private String ipAddress;
    private String gateway;
    private String subnetMask;
}
