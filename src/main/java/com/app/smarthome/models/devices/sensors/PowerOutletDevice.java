package com.app.smarthome.models.devices.sensors;

import com.app.smarthome.models.devices.bridge.result.BridgeResultDevice;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PowerOutletDevice extends BridgeResultDevice {

    private Long power;
}
