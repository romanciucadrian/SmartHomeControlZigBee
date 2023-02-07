package com.quest.global.SmartHome.controllers;

import com.quest.global.SmartHome.dtos.DeviceDTO;
import com.quest.global.SmartHome.exceptions.DeviceNotFoundException;
import com.quest.global.SmartHome.models.Device;
import com.quest.global.SmartHome.services.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Device> findAllDevices() {
        return deviceService.findAll();
    }

    @PostMapping
    public Device create(@RequestBody Device device) {
        return deviceService.saveDevice(device);
    }

    @PutMapping
    public ResponseEntity<?> updateDevice(@RequestParam String deviceName,
                                          @RequestParam String deviceNewName)
            throws DeviceNotFoundException {

        try {
            return new ResponseEntity<>(deviceService.updateDeviceByName(deviceName, deviceNewName),
                    HttpStatus.OK
            );
        } catch (DeviceNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("{deviceName}")
    public ResponseEntity<?> deleteDevice(@PathVariable String deviceName) {

        try {
           return new ResponseEntity<>(deviceService.deleteDeviceByName(deviceName),
                   HttpStatus.OK
           );
        } catch (DeviceNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

}
