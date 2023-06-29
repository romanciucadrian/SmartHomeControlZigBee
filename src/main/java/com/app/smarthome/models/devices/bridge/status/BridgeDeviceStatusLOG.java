package com.app.smarthome.models.devices.bridge.status;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BridgeDeviceStatusLOG {

    private List<String> wifiSSId;
    private Long telePeriod;
}
