package com.app.smarthome.models.devices.nspanel.status;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NSPanelDeviceStatusPRM {

    private String restartReason;
    private String uptime;
    private String startupUTC;
}
