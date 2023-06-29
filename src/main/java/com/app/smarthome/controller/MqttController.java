package com.app.smarthome.controller;

import com.app.smarthome.service.MqttService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mqtt")
public class MqttController {

    private final MqttService mqttService;

    public MqttController(MqttService mqttService) {
        this.mqttService = mqttService;
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @PostMapping("/connectToMQTT")
    public void connectToMQTT() throws MqttException {
        mqttService.connectToMQTT();
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @GetMapping("/isConnectedToMQTT")
    public Boolean isConnectedToMQTT() {
        return mqttService.isConnectedToMQTT();
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @GetMapping("/sendMessage")
    public void sendMessage(@RequestParam("topic") String topic, @RequestParam("payload") String payload)
            throws MqttException {
        mqttService.sendMessage(topic, payload);
    }
}
