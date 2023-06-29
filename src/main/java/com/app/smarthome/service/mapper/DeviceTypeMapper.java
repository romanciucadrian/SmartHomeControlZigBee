package com.app.smarthome.service.mapper;

import com.app.smarthome.dtos.DeviceTypeDTO;
import com.app.smarthome.models.DeviceType;

import java.util.ArrayList;
import java.util.List;

public class DeviceTypeMapper {

    public static List<DeviceTypeDTO> createDTOListFromEntityList(List<DeviceType> deviceTypes){
        List<DeviceTypeDTO> deviceTypesDto = new ArrayList<>();

        for(DeviceType currentDeviceType : deviceTypes) {
            DeviceTypeDTO deviceTypeDTO = createDTOFromEntity(currentDeviceType);
            deviceTypesDto.add(deviceTypeDTO);
        }
        return deviceTypesDto;
    }

    public static DeviceTypeDTO createDTOFromEntity(DeviceType deviceType){
        DeviceTypeDTO deviceTypeDTO = new DeviceTypeDTO();
        setDeviceTypeDTOBasedOnEntity(deviceType, deviceTypeDTO);
        return deviceTypeDTO;
    }

    private static void setDeviceTypeDTOBasedOnEntity(DeviceType deviceType, DeviceTypeDTO deviceTypeDTO) {
        deviceTypeDTO.setId(deviceType.getId().toHexString());
        deviceTypeDTO.setName(deviceType.getName());
        deviceTypeDTO.setMqttTopics((deviceType.getMqttTopics()));
        deviceTypeDTO.setDeviceHexName(deviceType.getDeviceHexName());
    }
}
