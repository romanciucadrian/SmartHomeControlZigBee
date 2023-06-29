package com.app.smarthome.models.devices.sensors;

import com.app.smarthome.models.devices.bridge.result.BridgeResultDevice;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ColorLightBulbDevice extends BridgeResultDevice {

    private Long hue;
    private Long sat;
    private Long ct;
    private Long dimmer;
    private Long power;
}
