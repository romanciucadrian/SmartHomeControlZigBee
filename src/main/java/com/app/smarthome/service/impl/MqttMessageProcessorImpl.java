package com.app.smarthome.service.impl;

import com.app.smarthome.exceptions.InvalidArgumentException;
import com.app.smarthome.models.Device;
import com.app.smarthome.models.devices.bridge.result.BridgeResultDevice;
import com.app.smarthome.models.devices.bridge.status.BridgeStatusDevice;
import com.app.smarthome.models.devices.nspanel.NSPanelDevice;
import com.app.smarthome.models.devices.nspanel.status.NSPanelDeviceStatusSTS;
import com.app.smarthome.repositories.DeviceRepository;
import com.app.smarthome.service.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.app.smarthome.constants.AppConstants.NSPANEL;

@Service
@RequiredArgsConstructor
public class MqttMessageProcessorImpl implements MqttMessageProcessor {

    private final BridgeDeviceStatusService bridgeDeviceStatusService;
    private final BridgeDeviceResultService bridgeDeviceResultService;
    private final NSPanelDeviceService nsPanelDeviceService;
    private final DeviceRepository deviceRepository;

    @Transactional
    @Override
    public void processBridgeStatus0AndSaveDevice(String message) throws InvalidArgumentException {
        JSONObject jsonObject = new JSONObject(message);
        BridgeStatusDevice bridgeStatusDevice = bridgeDeviceStatusService.generateBridgeStatusDevice(jsonObject);
        Optional<Device> optionalBridgeStatus =
                deviceRepository.findDeviceByName(bridgeStatusDevice.getBridgeDeviceStatus().getDeviceName());
        Device bridgeStatus = bridgeDeviceStatusService.createBridgeDeviceStatusBasedOnMqttPayload(bridgeStatusDevice,
                optionalBridgeStatus);
        deviceRepository.save(bridgeStatus);
    }

    @Transactional
    @Override
    public void processBridgeResultAndSaveDevice(String message) throws InvalidArgumentException {
        JSONObject jsonObject = new JSONObject(message);
        BridgeResultDevice bridgeResultDevice = bridgeDeviceResultService.generateBridgeResultDevice(jsonObject);
        Optional<Device> optionalBridgeResult = deviceRepository.findDeviceByName(bridgeResultDevice.getIeeeAddr());
        Device bridgeResult = bridgeDeviceResultService.createBridgeResultDeviceBasedOnMqttPayload(bridgeResultDevice,
                optionalBridgeResult);
        deviceRepository.save(bridgeResult);
    }

    @Transactional
    @Override
    public void processNspanelSwitchStatus0AndSaveDevice(String message) throws InvalidArgumentException {
        JSONObject jsonObject = new JSONObject(message);
        NSPanelDevice nsPanelDevice = nsPanelDeviceService.generateNSPanelDevice(jsonObject);
        Optional<Device> optionalNsPanel =
                deviceRepository.findDeviceByName(nsPanelDevice.getNsPanelDeviceStatus().getDeviceName());
        Device nsPanel = nsPanelDeviceService.createNSPanelDeviceBasedOnMqttPayload(nsPanelDevice, optionalNsPanel);
        deviceRepository.save(nsPanel);
    }

    @Transactional
    @Override
    public void processNspanelSwitchResultAndSaveDevice(String message) throws InvalidArgumentException {
        JSONObject jsonObject = new JSONObject(message);
        NSPanelDeviceStatusSTS nsPanelDeviceStatusSTS = nsPanelDeviceService.generateNSPanelDeviceStatusSTS(jsonObject);
        Optional<Device> optionalNsPanel = deviceRepository.findDeviceByName(NSPANEL);
        Device nsPanel =
                nsPanelDeviceService.createNSPanelDeviceStatusBasedOnMqttPayload(nsPanelDeviceStatusSTS, optionalNsPanel);
        deviceRepository.save(nsPanel);
    }
}
