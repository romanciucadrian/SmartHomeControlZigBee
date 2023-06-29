package com.app.smarthome.models.devices.nspanel.status;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NSPanelDeviceStatusSTS {

    private String power1;
    private String power2;
}
