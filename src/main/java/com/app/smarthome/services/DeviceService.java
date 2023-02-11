package com.app.smarthome.services;

import com.app.smarthome.exceptions.DeviceNotFoundException;
import com.app.smarthome.exceptions.DeviceTypeNotFoundException;
import com.app.smarthome.models.Device;
import com.app.smarthome.models.DeviceType;
import com.app.smarthome.repositories.DeviceRepository;
import com.app.smarthome.repositories.DeviceTypeRepository;
import com.app.smarthome.services.impl.IDeviceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DeviceService implements IDeviceService {

    private final DeviceRepository deviceRepository;
    private final MqttService mqttService;
    private final DeviceTypeRepository deviceTypeRepository;

    public DeviceService(DeviceRepository deviceRepository, MqttService mqttService,
                         DeviceTypeRepository deviceTypeRepository) {

        this.deviceRepository = deviceRepository;
        this.mqttService = mqttService;
        this.deviceTypeRepository = deviceTypeRepository;
    }


    public List<Device> findAll() {
        return new ArrayList<>(deviceRepository.findAll());
    }

    @Transactional
    public Device saveDevice(Device device) {

        return deviceRepository.save(device);
    }

    @Transactional
    public Device findDeviceByName(String deviceName) throws DeviceNotFoundException {
        Device device =
                deviceRepository.findDeviceByDeviceName(deviceName);

        try {
           return device;
        } catch (NoSuchElementException exception) {
            throw new DeviceNotFoundException("Can't find a device with this name!");
        }
    }

    @Transactional
    public Device updateDeviceByName(String deviceName, String deviceNewName) throws DeviceNotFoundException {

            Device device =
                    deviceRepository.findDeviceByDeviceName(deviceName);

            if (device != null) {

                device.setDeviceName(deviceNewName);

                return deviceRepository.save(device);
            } else {
                throw new DeviceNotFoundException("This device name doesn't exist!");
            }

    }

    @Transactional
    public String deleteDeviceByName(String deviceName) throws DeviceNotFoundException {

        Device device
                = findDeviceByName(deviceName);

        if (device != null) {

             deviceRepository.delete(device);
             return "Device deleted!";
        } else {
          throw new DeviceNotFoundException("This device doesn't exist!");
        }
    }

    @Transactional
    public Device createDeviceByDeviceTypeHexName(String deviceHexName) throws
            DeviceTypeNotFoundException, DeviceNotFoundException, InterruptedException {

        DeviceType deviceType =
               deviceTypeRepository.findDeviceTypeByDeviceHexName(deviceHexName).orElse(null);

        if (deviceType == null) {
            throw new DeviceTypeNotFoundException("DeviceType not found!");
        } else {
            Device device =
                   deviceRepository.findDeviceByHexId(deviceHexName).orElse(null);
            if (device != null) {
                throw new DeviceNotFoundException("This device exists!");
            } else {

                mqttService.doPublish(deviceType.getDeviceTypeName());

                Thread.sleep(3000);

                return deviceRepository.findDeviceByHexId(deviceHexName).orElse(null);
            }
        }
    }

}
