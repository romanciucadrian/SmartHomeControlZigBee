package com.app.smarthome.models.devices.bridge.status;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BridgeDeviceStatusPRM {

    private String restartReason;
    private String uptime;
    private String startupUTC;
}
