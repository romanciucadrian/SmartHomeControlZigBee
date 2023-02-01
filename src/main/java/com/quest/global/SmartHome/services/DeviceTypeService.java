package com.quest.global.SmartHome.services;

import com.quest.global.SmartHome.dtos.DeviceDTO;
import com.quest.global.SmartHome.dtos.DeviceTypeDTO;
import com.quest.global.SmartHome.exceptions.DeviceNotFoundException;
import com.quest.global.SmartHome.exceptions.DeviceTypeNotFoundException;
import com.quest.global.SmartHome.mapstruct.MapStructMapperImpl;
import com.quest.global.SmartHome.models.DeviceType;
import com.quest.global.SmartHome.repositories.CommandRepository;
import com.quest.global.SmartHome.repositories.DeviceRepository;
import com.quest.global.SmartHome.repositories.DeviceTypeRepository;
import org.bson.types.ObjectId;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceTypeService {

    public  final DeviceTypeRepository deviceTypeRepository;

    public final DeviceRepository deviceRepository;

    public final CommandRepository commandRepository;


    public DeviceTypeService(DeviceTypeRepository deviceTypeRepository, DeviceRepository deviceRepository, CommandRepository commandRepository) {
        this.deviceTypeRepository = deviceTypeRepository;
        this.deviceRepository = deviceRepository;
        this.commandRepository = commandRepository;
    }

    public List<DeviceType> findAll() {
        return deviceTypeRepository.findAll();
    }

    public DeviceType findById(ObjectId id) {
        DeviceType deviceType = deviceTypeRepository.findById(id).orElse(null);

        if (deviceType == null) {
            throw new RuntimeException("Nu exista acest id!!");
        }
        return deviceType;
    }

    public DeviceType update(ObjectId id) {
        DeviceType deviceType = findById(id);

        deviceType.setDeviceTypeName("ABCCC");
        deviceType.setDeviceHexName("ABND");

        return deviceTypeRepository.save(deviceType);
    }

    public void delete(ObjectId id) {

        DeviceType deviceType =  findById(id);
        deviceTypeRepository.deleteById(deviceType.getId());

    }

}

