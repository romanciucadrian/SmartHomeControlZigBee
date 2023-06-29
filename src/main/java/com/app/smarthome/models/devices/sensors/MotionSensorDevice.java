package com.app.smarthome.models.devices.sensors;

import com.app.smarthome.models.devices.bridge.result.BridgeResultDevice;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class MotionSensorDevice extends BridgeResultDevice {

    private Long zoneStatus;
    private Long zoneType;
    private Long occupancy;
}
