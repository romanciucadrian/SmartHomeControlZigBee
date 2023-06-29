package com.app.smarthome.service.mapper;

import com.app.smarthome.dtos.DeviceDTO;
import com.app.smarthome.models.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceMapper {

    public static List<DeviceDTO> createDTOListFromEntityList(List<Device> devices){
        List<DeviceDTO> devicesDto = new ArrayList<>();

        for(Device currentDevice : devices) {
            DeviceDTO deviceDTO = createDTOFromEntity(currentDevice);
            devicesDto.add(deviceDTO);
        }
        return devicesDto;
    }
    public static DeviceDTO createDTOFromEntity(Device device){
        DeviceDTO deviceDTO = new DeviceDTO();
        setDeviceDTOBasedOnEntity(device, deviceDTO);
        return deviceDTO;
    }
    private static void setDeviceDTOBasedOnEntity(Device device, DeviceDTO deviceDTO) {
        deviceDTO.setId(device.getId().toHexString());
        deviceDTO.setT(device.getT());
        deviceDTO.setHouseId(device.getHouseId() == null ? null : device.getHouseId().toHexString());
        deviceDTO.setRoomId(device.getRoomId() == null ? null : device.getRoomId().toHexString());
        deviceDTO.setSceneId(device.getSceneId() == null ? null : device.getSceneId().toHexString());
        deviceDTO.setDeviceTypeId(device.getDeviceTypeId() == null ? null : device.getDeviceTypeId().toHexString());
        deviceDTO.setUserId(device.getUserId() == null ? null : device.getUserId().toHexString());
        deviceDTO.setName(device.getName());
        deviceDTO.setHexId(device.getHexId());
        deviceDTO.setTopic(device.getTopic());
        deviceDTO.setPower(device.getPower());
        deviceDTO.setLedState(device.getLedState());
        deviceDTO.setRestartReason(device.getRestartReason());
        deviceDTO.setUptime(device.getUptime());
        deviceDTO.setStartupUTC(device.getStartupUTC());
        deviceDTO.setWifiSSId(device.getWifiSSId());
        deviceDTO.setTelePeriod(device.getTelePeriod());
        deviceDTO.setHostName(device.getHostName());
        deviceDTO.setIpAddress(device.getIpAddress());
        deviceDTO.setGateway(device.getGateway());
        deviceDTO.setSubnetMask(device.getSubnetMask());
        deviceDTO.setTemperature(device.getTemperature());
        deviceDTO.setTemperatureUnit(device.getTemperatureUnit());
        deviceDTO.setPower1(device.getPower1());
        deviceDTO.setPower2(device.getPower2());
        deviceDTO.setDevice(device.getDevice());
        deviceDTO.setHue(device.getHue());
        deviceDTO.setSat(device.getSat());
        deviceDTO.setTemperature2(device.getTemperature2());
        deviceDTO.setHumidity(device.getHumidity());
        deviceDTO.setModelId(device.getModelId());
        deviceDTO.setManufacturer(device.getManufacturer());
        deviceDTO.setReachable(device.getReachable());
        deviceDTO.setLastSeen(device.getLastSeen());
        deviceDTO.setLastSeenEpoch(device.getLastSeenEpoch());
        deviceDTO.setBatteryLastSeenEpoch(device.getBatteryLastSeenEpoch());
        deviceDTO.setIeeeAddr(device.getIeeeAddr());
        deviceDTO.setLinkQuality(device.getLinkQuality());
        deviceDTO.setZoneStatus(device.getZoneStatus());
        deviceDTO.setZoneType(device.getZoneType());
        deviceDTO.setEndpoints(device.getEndpoints());
    }
}
