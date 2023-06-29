package com.app.smarthome.controller;

import com.app.smarthome.dtos.DeviceTypeDTO;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.service.DeviceTypeService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/deviceTypes")
public class DeviceTypeController {

    private final DeviceTypeService deviceTypeService;

    public DeviceTypeController(DeviceTypeService deviceTypeService) {
        this.deviceTypeService = deviceTypeService;
    }


    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN') or hasAuthority('MEMBER')")
    @GetMapping("/allDeviceTypes")
    public CompletableFuture<List<DeviceTypeDTO>> getAllDeviceTypes() {
        return CompletableFuture.completedFuture(deviceTypeService.getAllDeviceTypes());
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN') or hasAuthority('MEMBER')")
    @GetMapping("/deviceTypeByName")
    public CompletableFuture<DeviceTypeDTO> getDeviceTypeByName(@RequestParam("name") String name)
                                                                                    throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(deviceTypeService.getDeviceTypeByName(name));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @PostMapping("/createDeviceType")
    public CompletableFuture<DeviceTypeDTO> createDeviceType(@RequestParam("name") String name,
                                                             @RequestParam("mqttTopicsProvided") List<String> mqttTopicsProvided,
                                                             @RequestParam("deviceHexName") String deviceHexName) {
        return CompletableFuture.completedFuture(deviceTypeService.createDeviceType(name, mqttTopicsProvided,
                                                                                                    deviceHexName));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @PutMapping("/updateDeviceType")
    public CompletableFuture<DeviceTypeDTO> updateDeviceType(@RequestParam("currentName") String currentName,
                                                             @RequestParam("newName") String newName,
                                                             @RequestParam("mqttTopicsProvided") List<String> mqttTopics,
                                                             @RequestParam("deviceHexName") String deviceHexName)
                                                                throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(deviceTypeService.updateDeviceType(currentName, newName, mqttTopics,
                                                                                                    deviceHexName));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @DeleteMapping("/removeDeviceType")
    public void removeDeviceType(@RequestParam("name") String name) throws DocumentNotFoundException {
         deviceTypeService.removeDeviceTypeByName(name);
    }
}
