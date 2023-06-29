package com.app.smarthome.models.devices.bridge.status;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BridgeStatusDevice {

    private BridgeDeviceStatus bridgeDeviceStatus;
    private BridgeDeviceStatusPRM bridgeDeviceStatusPRM;
    private BridgeDeviceStatusNET bridgeDeviceStatusNET;
    private BridgeDeviceStatusLOG bridgeDeviceStatusLOG;
}
