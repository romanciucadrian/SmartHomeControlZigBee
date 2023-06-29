package com.app.smarthome.models.devices.nspanel.status;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class NSPanelDeviceStatusSNS {

    private BigDecimal temperature;
    private String temperatureUnit;
}
