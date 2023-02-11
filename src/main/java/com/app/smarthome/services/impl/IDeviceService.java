package com.app.smarthome.services.impl;

import com.app.smarthome.models.Device;
import com.app.smarthome.exceptions.DeviceNotFoundException;
import com.app.smarthome.exceptions.DeviceTypeNotFoundException;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

public interface IDeviceService {

    List<Device> findAll();

    Device saveDevice(Device device);

    Device findDeviceByName(String deviceName) throws DeviceNotFoundException;

    Device updateDeviceByName(String deviceName, String deviceNewName) throws DeviceNotFoundException;

    String deleteDeviceByName(String deviceName) throws DeviceNotFoundException;

    Device createDeviceByDeviceTypeHexName(String deviceHexName) throws DeviceTypeNotFoundException, DeviceNotFoundException, MqttException, InterruptedException;
}
