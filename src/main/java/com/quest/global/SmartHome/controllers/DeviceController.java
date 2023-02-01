package com.quest.global.SmartHome.controllers;

import com.quest.global.SmartHome.dtos.DeviceDTO;
import com.quest.global.SmartHome.exceptions.DeviceNotFoundException;
import com.quest.global.SmartHome.models.Device;
import com.quest.global.SmartHome.services.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

   private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public List<Device> findAll() {
      return   deviceService.findAll();
    }

    @PostMapping
    public Device create(@RequestBody Device device) {
        return deviceService.saveDevice(device);
    }

    @PutMapping("{deviceName}")
    public Device update(@PathVariable String deviceName, String deviceNewName) throws DeviceNotFoundException {
        return deviceService.updateDeviceByName(deviceName, deviceNewName);
    }

    @DeleteMapping("{deviceName}")
    public void delete(@PathVariable String deviceName) throws DeviceNotFoundException {
        deviceService.deleteDeviceByName(deviceName);
    }


}
