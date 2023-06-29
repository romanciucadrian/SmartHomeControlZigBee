package com.app.smarthome.models.devices.sensors;

import com.app.smarthome.models.devices.bridge.result.BridgeResultDevice;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@SuperBuilder
public class TempHumDevice extends BridgeResultDevice {

    private String modelId;
    private String manufacturer;
    private BigDecimal temperature;
    private Float humidity;
}
