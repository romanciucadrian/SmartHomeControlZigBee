package com.app.smarthome.service.impl;

import com.app.smarthome.dtos.DeviceDTO;
import com.app.smarthome.dtos.HouseDTO;
import com.app.smarthome.exceptions.*;
import com.app.smarthome.models.*;
import com.app.smarthome.repositories.DeviceRepository;
import com.app.smarthome.service.*;
import com.app.smarthome.service.mapper.DeviceMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Locale;
import java.util.Optional;

import static com.app.smarthome.constants.AppConstants.*;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceTypeService deviceTypeService;
    private final HouseService houseService;
    private final UserService userService;
    private final RoomService roomService;
    private final MessageSource messageSource;
    private final MqttService mqttService;

    @Override
    public DeviceDTO addDeviceIntoSystem(String deviceHexName) throws DocumentNotFoundException, DocumentAlreadyExistsException,
            MqttException, DocumentNotCreatedException, InterruptedException, InvalidArgumentException {
        DeviceType deviceType = deviceTypeService.getDeviceTypeByHexName(deviceHexName);
        Optional<Device> optionalDevice = deviceRepository.findDeviceByHexId(deviceType.getDeviceHexName());
        if (optionalDevice.isPresent()) {
            throw new DocumentAlreadyExistsException(messageSource.getMessage("device.already.added.in.system",
                    null, Locale.getDefault()));
        }
        switch (deviceType.getDeviceHexName()) {
            case ZBBRIDGE -> mqttService.sendMessage(CMND_BRIDGE_STATUS0, EMPTY_STRING);
            case Ox431C -> mqttService.sendMessage(CMND_BRIDGE_ZBSTATUS3, MOTION_SENSOR);
            case Ox79BD -> mqttService.sendMessage(CMND_BRIDGE_ZBSTATUS3, TEMP_HUM_SENSOR);
            case Ox66B9 -> mqttService.sendMessage(CMND_BRIDGE_ZBSTATUS3, MAGNETIC_SENSOR);
            case NSPANEL -> mqttService.sendMessage(CMND_NSPANELSWITCH_STATUS0, EMPTY_STRING);
            default -> throw new InvalidArgumentException(messageSource.getMessage("invalid.system.device", null,
                    Locale.getDefault()));
        }

        Thread.sleep(1000);
        return getDeviceDTOByHexName(deviceHexName);
    }

    @Override
    public DeviceDTO getDeviceDTOByHexName(String hexId) throws DocumentNotCreatedException {
        Device device = getDeviceByHexName(hexId);
        return DeviceMapper.createDTOFromEntity(device);
    }

    @Override
    public Device getDeviceByHexName(String hexId) throws DocumentNotCreatedException {
        return deviceRepository.findDeviceByHexId(hexId).orElseThrow(() ->
                new DocumentNotCreatedException(messageSource.getMessage("device.not.added.in.system", null,
                        Locale.getDefault())));
    }

    @Override
    public void removeDeviceFromSystem(String deviceHexName) throws DocumentNotFoundException {
        Device device = deviceRepository.findDeviceByHexId(deviceHexName).orElseThrow(() ->
                new DocumentNotFoundException(messageSource.getMessage("device.not.found.in.system", null,
                        Locale.getDefault())));
        deviceRepository.delete(device);
    }

    @Override
    public Device getDeviceByName(String deviceName) throws DocumentNotFoundException {
        return deviceRepository.findDeviceByName(deviceName).orElseThrow(() ->
                new DocumentNotFoundException(messageSource.getMessage("no.device.found", null,
                        Locale.getDefault())));
    }

    @Override
    public DeviceDTO getDeviceDTOByName(String deviceName) throws DocumentNotFoundException {
        Device device = getDeviceByName(deviceName);
        return DeviceMapper.createDTOFromEntity(device);
    }

    @Override
    public DeviceDTO getDeviceInHouseByHexName(String deviceHexName, ObjectId houseId) throws DocumentNotFoundException,
            DocumentNotCreatedException, DeviceNotBelongToHouseException {
        HouseDTO houseDTO = houseService.getHouseDTOById(houseId);
        DeviceDTO deviceDTO = getDeviceDTOByHexName(deviceHexName);
        if (isDeviceInHouse(houseDTO, deviceDTO)) {
            return deviceDTO;
        }
        throw new DeviceNotBelongToHouseException(messageSource.getMessage("device.not.belong.to.house",
                new String[]{deviceHexName}, Locale.getDefault()));
    }

    private boolean isDeviceInHouse(HouseDTO houseDTO, DeviceDTO deviceDTO) {
        return deviceDTO.getHouseId() != null && deviceDTO.getHouseId().equals(houseDTO.getId());
    }

    @Override
    public DeviceDTO addDeviceToHouse(String deviceHexName, ObjectId houseId, String email)
            throws DocumentNotFoundException, DocumentNotCreatedException,
            DocumentAlreadyExistsException {
        User user = userService.getUserByEmail(email);
        House house = houseService.getHouseById(houseId);
        Device device = deviceRepository.findDeviceByHexId(deviceHexName).orElseThrow(() ->
                new DocumentNotCreatedException(messageSource.getMessage("device.not.added.in.system", null,
                        Locale.getDefault())));
        if (device.getHouseId() != null && !device.getHouseId().toHexString().equals("000000000000000000000000")) {
            throw new DocumentAlreadyExistsException(messageSource.getMessage("device.already.added.in.the.house",
                    null, Locale.getDefault()));
        }

        houseService.saveDeviceIntoHouse(device.getId(), house);
        userService.saveDeviceForUser(device.getId(), user);
        device = updateDeviceForHouseAndUser(device, house.getId(), user.getId());
        return DeviceMapper.createDTOFromEntity(device);
    }

    private Device updateDeviceForHouseAndUser(Device device, ObjectId houseId, ObjectId userId) {
        device.setHouseId(houseId);
        device.setUserId(userId);
        device = deviceRepository.save(device);
        return device;
    }

    private Device removeDeviceForHouseAndRoomAndUser(Device device) {
        device.setHouseId(null);
        device.setRoomId(null);
        device.setUserId(null);
        device = deviceRepository.save(device);
        return device;
    }

    @Override
    public DeviceDTO removeDeviceFromHouse(String deviceHexName, ObjectId houseId, String email)
            throws DocumentNotFoundException, DocumentAlreadyExistsException, DocumentNotCreatedException {
        User user = userService.getUserByEmail(email);
        House house = houseService.getHouseById(houseId);
        Device device = deviceRepository.findDeviceByHexId(deviceHexName).orElseThrow(() ->
                new DocumentNotCreatedException(messageSource.getMessage("device.not.added.in.system", null,
                        Locale.getDefault())));
        if (deviceBelongsToHouse(house, device)) {
            Optional<Room> optionalRoom;
            if (device.getRoomId() != null) {
                optionalRoom = roomService.getOptionalRoomById(device.getRoomId());
                if (optionalRoom.isPresent()) {
                    roomService.deleteDeviceFromRoom(device.getId(), optionalRoom.get());
                }
            }
            houseService.removeDeviceFromHouse(device.getId(), house);
            userService.removeDeviceFromUser(device.getId(), user);
            device = removeDeviceForHouseAndRoomAndUser(device);
            return DeviceMapper.createDTOFromEntity(device);
        }
        throw new DocumentAlreadyExistsException(messageSource.getMessage("device.not.part.of.the.house",
                null, Locale.getDefault()));
    }

    private boolean deviceBelongsToHouse(House house, Device device) {
        return !CollectionUtils.isEmpty(house.getDevices()) && house.getDevices().contains(device.getId());
    }

    @Override
    public Boolean existsByName(String deviceName) {
        return deviceRepository.existsByName(deviceName);
    }
}
