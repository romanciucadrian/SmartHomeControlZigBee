package com.app.smarthome.controllers;

import com.app.smarthome.models.DeviceType;
import com.app.smarthome.repositories.DeviceTypeRepository;
import com.app.smarthome.services.DeviceTypeService;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devicesType")
public class DeviceTypeController {

    private final DeviceTypeRepository deviceTypeRepository;
    private final DeviceTypeService deviceTypeService;

    public DeviceTypeController(DeviceTypeRepository deviceTypeRepository,
                                DeviceTypeService deviceTypeService) {
        this.deviceTypeRepository = deviceTypeRepository;
        this.deviceTypeService = deviceTypeService;
    }

    @GetMapping
    public List<DeviceType> getAll() {
        return deviceTypeRepository.findAll();
    }

    @PostMapping
    public DeviceType create(@RequestBody DeviceType deviceType) {
        return deviceTypeRepository.save(deviceType);
    }

    @PutMapping("{id}")
    public DeviceType update(@PathVariable String id) {
        return deviceTypeService.update(new ObjectId(id));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable ObjectId id){
        deviceTypeService.delete(id);
    }

}
