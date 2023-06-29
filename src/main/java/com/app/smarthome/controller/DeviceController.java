package com.app.smarthome.controller;

import com.app.smarthome.dtos.DeviceDTO;
import com.app.smarthome.exceptions.*;
import com.app.smarthome.service.DeviceService;
import com.app.smarthome.utils.ObjectIdUtils;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @PostMapping("/addDeviceIntoSystem")
    public CompletableFuture<DeviceDTO> addDeviceIntoSystem(@RequestParam("deviceHexName") String deviceHexName)
            throws DocumentNotFoundException, DocumentAlreadyExistsException, MqttException, DocumentNotCreatedException,
            InterruptedException, InvalidArgumentException {
         return CompletableFuture.completedFuture(deviceService.addDeviceIntoSystem(deviceHexName));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @DeleteMapping("/removeDeviceFromSystem")
    public void deleteDeviceFromSystem(@RequestParam("deviceHexName") String deviceHexName) throws DocumentNotFoundException {
        deviceService.removeDeviceFromSystem(deviceHexName);
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN') or hasAuthority('MEMBER')")
    @GetMapping("/deviceByHexName")
    public CompletableFuture<DeviceDTO> getDeviceByHexName(@RequestParam("deviceHexName") String deviceHexName)
            throws DocumentNotCreatedException {
        return CompletableFuture.completedFuture(deviceService.getDeviceDTOByHexName(deviceHexName));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN') or hasAuthority('MEMBER')")
    @GetMapping("/deviceByName")
    public CompletableFuture<DeviceDTO> getDeviceByName(@RequestParam("deviceName") String deviceName)
            throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(deviceService.getDeviceDTOByName(deviceName));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN') or hasAuthority('MEMBER')")
    @GetMapping("/deviceInHouseByHexName")
    public CompletableFuture<DeviceDTO> getDeviceInHouseByHexName(@RequestParam("deviceHexName") String deviceHexName,
                                                                  @RequestParam("houseId") String houseId)
            throws DocumentNotFoundException, DocumentNotCreatedException, DeviceNotBelongToHouseException {
        return CompletableFuture.completedFuture(deviceService.getDeviceInHouseByHexName(deviceHexName,
                ObjectIdUtils.convertStringToObjectId(houseId)));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN')")
    @PostMapping("/addDeviceToHouse")
    public CompletableFuture<DeviceDTO> addDeviceToHouse(@RequestParam("deviceHexName") String deviceHexName,
                                                         @RequestParam("houseId") String houseId,
                                                         @RequestParam("email") String email)
            throws DocumentNotFoundException, DocumentNotCreatedException, DocumentAlreadyExistsException,
            InvalidRoleException {
        return CompletableFuture.completedFuture(
                deviceService.addDeviceToHouse(deviceHexName, ObjectIdUtils.convertStringToObjectId(houseId), email));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN')")
    @PostMapping("/removeDeviceFromHouse")
    public CompletableFuture<DeviceDTO> removeDeviceFromHouse(@RequestParam("deviceHexName") String deviceHexName,
                                                         @RequestParam("houseId") String houseId,
                                                         @RequestParam("email") String email)
            throws DocumentNotFoundException, DocumentNotCreatedException, DocumentAlreadyExistsException,
            InvalidRoleException {
        return CompletableFuture.completedFuture(
                deviceService.removeDeviceFromHouse(deviceHexName, ObjectIdUtils.convertStringToObjectId(houseId), email));
    }
}
