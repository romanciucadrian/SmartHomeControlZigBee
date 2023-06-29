package com.app.smarthome.models.devices.nspanel;

import com.app.smarthome.models.devices.nspanel.status.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NSPanelDevice {
    private NSPanelDeviceStatus nsPanelDeviceStatus;
    private NSPanelDeviceStatusPRM nsPanelDeviceStatusPRM;
    private NSPanelDeviceStatusNET nsPanelDeviceStatusNET;
    private NSPanelDeviceStatusSNS nsPanelDeviceStatusSNS;
    private NSPanelDeviceStatusSTS nsPanelDeviceStatusSTS;
    private NSPanelDeviceStatusLOG nsPanelDeviceStatusLOG;
}
