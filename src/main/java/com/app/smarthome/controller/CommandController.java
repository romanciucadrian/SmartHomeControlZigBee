package com.app.smarthome.controller;

import com.app.smarthome.dtos.CommandDTO;
import com.app.smarthome.dtos.DeviceDTO;
import com.app.smarthome.exceptions.DocumentAlreadyExistsException;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.exceptions.MqttConnectionException;
import com.app.smarthome.service.CommandService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/commands")
public class CommandController {

    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN') or hasAuthority('MEMBER')")
    @GetMapping("/commandByName")
    public CompletableFuture<CommandDTO> getCommandByName(@RequestParam("commandName") String commandName)
            throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(commandService.getCommandDTOByName(commandName));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN') or hasAuthority('MEMBER')")
    @GetMapping("/commandsByDeviceName")
    public CompletableFuture<List<CommandDTO>> getCommandsByDeviceName(@RequestParam("deviceName") String deviceName)
            throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(commandService.getCommandsByDeviceName(deviceName));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @PostMapping("/addCommand")
    public CompletableFuture<CommandDTO> addCommand(@RequestParam("commandName") String commandName,
                                                    @RequestParam("topic") String topic,
                                                    @RequestParam(value = "payload", required = false) String payload,
                                                    @RequestParam("deviceName") String deviceName)
            throws DocumentNotFoundException, DocumentAlreadyExistsException {
        return CompletableFuture.completedFuture(commandService.addCommand(commandName, topic, payload, deviceName));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @PutMapping("/updateCommand")
    public CompletableFuture<CommandDTO> updateCommand(@RequestParam("commandName") String commandName,
                                                       @RequestParam(value = "topic", required = false) String topic,
                                                       @RequestParam(value = "payload", required = false) String payload,
                                                       @RequestParam(value = "deviceName", required = false) String deviceName)
            throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(commandService.updateCommand(commandName, topic, payload, deviceName));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @GetMapping("/commandResult")
    public CompletableFuture<DeviceDTO> getCommandResult(@RequestParam("commandName") String commandName,
                                                         @RequestParam("deviceName") String deviceName)
            throws DocumentNotFoundException, MqttConnectionException, MqttException, InterruptedException {
        return CompletableFuture.completedFuture(commandService.getCommandResult(commandName, deviceName));
    }
}
