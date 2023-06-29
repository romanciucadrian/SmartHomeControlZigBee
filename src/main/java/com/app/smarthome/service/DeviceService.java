package com.app.smarthome.service;

import com.app.smarthome.dtos.DeviceDTO;
import com.app.smarthome.exceptions.*;
import com.app.smarthome.models.Device;
import org.bson.types.ObjectId;
import org.eclipse.paho.client.mqttv3.MqttException;

public interface DeviceService {

    DeviceDTO addDeviceIntoSystem(String deviceHexName) throws DocumentNotFoundException, DocumentAlreadyExistsException,
            MqttException, DocumentNotCreatedException, InterruptedException, InvalidArgumentException;

    void removeDeviceFromSystem(String deviceHexName) throws DocumentNotFoundException;

    Device getDeviceByName(String deviceName) throws DocumentNotFoundException;

    DeviceDTO getDeviceDTOByName(String deviceName) throws DocumentNotFoundException;

    Device getDeviceByHexName(String deviceHexName) throws DocumentNotCreatedException;

    DeviceDTO getDeviceDTOByHexName(String deviceHexName) throws DocumentNotCreatedException;

    DeviceDTO getDeviceInHouseByHexName(String deviceHexName, ObjectId houseId) throws DocumentNotFoundException,
            DocumentNotCreatedException, DeviceNotBelongToHouseException;

    DeviceDTO addDeviceToHouse(String deviceHexName, ObjectId houseId, String email) throws DocumentNotFoundException,
            InvalidRoleException, DocumentNotCreatedException, DocumentAlreadyExistsException;

    DeviceDTO removeDeviceFromHouse(String deviceHexName, ObjectId houseId, String email)
            throws DocumentNotFoundException, InvalidRoleException, DocumentAlreadyExistsException,
            DocumentNotCreatedException;

    Boolean existsByName(String deviceName);
}
