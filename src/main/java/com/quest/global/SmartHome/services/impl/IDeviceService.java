package com.quest.global.SmartHome.services.impl;

import com.quest.global.SmartHome.exceptions.DeviceNotFoundException;
import com.quest.global.SmartHome.exceptions.DeviceTypeNotFoundException;
import com.quest.global.SmartHome.models.Device;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

public interface IDeviceService {

    List<Device> findAll();

    Device saveDevice(Device device);

    Device findDeviceByName(String deviceName) throws DeviceNotFoundException;

    Device updateDeviceByName(String deviceName, String deviceNewName) throws DeviceNotFoundException;

    void deleteDeviceByName(String deviceName) throws DeviceNotFoundException;

    Device createDeviceByDeviceTypeHexName(String deviceHexName) throws DeviceTypeNotFoundException, DeviceNotFoundException, MqttException, InterruptedException;
}
