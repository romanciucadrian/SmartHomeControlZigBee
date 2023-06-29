package com.app.smarthome.service;

import com.app.smarthome.dtos.DeviceTypeDTO;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.models.DeviceType;

import java.util.List;

public interface DeviceTypeService {

    List<DeviceTypeDTO> getAllDeviceTypes();

    DeviceTypeDTO getDeviceTypeByName(String name) throws DocumentNotFoundException;

    DeviceTypeDTO createDeviceType(String name, List<String> mqttTopicsProvided, String deviceHexName);

    DeviceTypeDTO updateDeviceType(String currentName, String newName, List<String> mqttTopics,
                                                      String deviceHexName) throws DocumentNotFoundException;

    void removeDeviceTypeByName(String name) throws DocumentNotFoundException;

    DeviceType getDeviceTypeByHexName(String deviceHexName) throws DocumentNotFoundException;
}
