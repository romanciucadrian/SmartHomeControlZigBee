package com.app.smarthome.models.devices.nspanel.status;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NSPanelDeviceStatusLOG {

    private List<String> wifiSSId;
    private Long telePeriod;
}
