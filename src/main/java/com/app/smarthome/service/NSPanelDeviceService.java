package com.app.smarthome.service;

import com.app.smarthome.exceptions.InvalidArgumentException;
import com.app.smarthome.models.Device;
import com.app.smarthome.models.devices.nspanel.NSPanelDevice;
import com.app.smarthome.models.devices.nspanel.status.NSPanelDeviceStatusSTS;
import org.json.JSONObject;

import java.util.Optional;

public interface NSPanelDeviceService {
    
    NSPanelDevice generateNSPanelDevice(JSONObject jsonObject) throws InvalidArgumentException;

    NSPanelDeviceStatusSTS generateNSPanelDeviceStatusSTS(JSONObject jsonObject) throws InvalidArgumentException;

    Device createNSPanelDeviceBasedOnMqttPayload(NSPanelDevice nsPanelDevice,
                                                 Optional<Device> optionalNsPanel) throws InvalidArgumentException;

    Device createNSPanelDeviceStatusBasedOnMqttPayload(NSPanelDeviceStatusSTS nsPanelDeviceStatusSTS,
                                                 Optional<Device> optionalNsPanel) throws InvalidArgumentException;
}
