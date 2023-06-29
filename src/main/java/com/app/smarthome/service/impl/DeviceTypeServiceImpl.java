package com.app.smarthome.service.impl;

import com.app.smarthome.dtos.DeviceTypeDTO;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.models.DeviceType;
import com.app.smarthome.repositories.DeviceTypeRepository;
import com.app.smarthome.service.DeviceTypeService;
import com.app.smarthome.service.mapper.DeviceTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class DeviceTypeServiceImpl implements DeviceTypeService {

    private final DeviceTypeRepository deviceTypeRepository;
    private final MessageSource messageSource;

    @Override
    public List<DeviceTypeDTO> getAllDeviceTypes() {
        List<DeviceType> deviceTypes = deviceTypeRepository.findAll();
        return DeviceTypeMapper.createDTOListFromEntityList(deviceTypes);
    }

    @Override
    public DeviceTypeDTO getDeviceTypeByName(String name) throws DocumentNotFoundException {
        DeviceType deviceType = deviceTypeRepository.findDeviceTypeByName(name).orElseThrow(() ->
                new DocumentNotFoundException(messageSource.getMessage("no.device.type.found", null, Locale.getDefault())));
        return DeviceTypeMapper.createDTOFromEntity(deviceType);
    }

    @Transactional
    @Override
    public DeviceTypeDTO createDeviceType(String name, List<String> mqttTopicsProvided,
                                                             String deviceHexName) {
        DeviceType deviceType = new DeviceType();
        setDeviceTypeProperties(deviceType, name, mqttTopicsProvided, deviceHexName);
        deviceType = deviceTypeRepository.save(deviceType);
        return DeviceTypeMapper.createDTOFromEntity(deviceType);
    }

    private void setDeviceTypeProperties(DeviceType deviceType, String newName, List<String> mqttTopics,
                                         String deviceHexName) {
        deviceType.setName(newName);
        deviceType.setMqttTopics(mqttTopics);
        deviceType.setDeviceHexName(deviceHexName);
    }

    @Transactional
    @Override
    public DeviceTypeDTO updateDeviceType(String currentName, String newName, List<String> mqttTopics,
                                                             String deviceHexName) throws DocumentNotFoundException {
        DeviceType deviceType = deviceTypeRepository.findDeviceTypeByName(currentName).orElseThrow(() ->
                new DocumentNotFoundException(messageSource.getMessage("no.device.type.found", null, Locale.getDefault())));
        setDeviceTypeProperties(deviceType, newName, mqttTopics, deviceHexName);
        deviceType = deviceTypeRepository.save(deviceType);
        return DeviceTypeMapper.createDTOFromEntity(deviceType);
    }

    @Transactional
    @Override
    public void removeDeviceTypeByName(String name) throws DocumentNotFoundException {
        DeviceType deviceType = deviceTypeRepository.findDeviceTypeByName(name).orElseThrow(() ->
                new DocumentNotFoundException(messageSource.getMessage("no.device.type.found", null, Locale.getDefault())));
        deviceTypeRepository.delete(deviceType);
    }

    @Override
    public DeviceType getDeviceTypeByHexName(String deviceHexName) throws DocumentNotFoundException {
        return deviceTypeRepository.findDeviceTypeByDeviceHexName(deviceHexName).orElseThrow(()
                -> new DocumentNotFoundException(messageSource.getMessage("no.device.type.found", null, Locale.getDefault())));
    }
}
