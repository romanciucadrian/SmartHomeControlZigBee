package com.app.smarthome.service;

import com.app.smarthome.exceptions.InvalidArgumentException;
import com.app.smarthome.models.Device;
import com.app.smarthome.models.devices.bridge.status.BridgeStatusDevice;
import org.json.JSONObject;

import java.util.Optional;

public interface BridgeDeviceStatusService {

    BridgeStatusDevice generateBridgeStatusDevice(JSONObject jsonObject) throws InvalidArgumentException;

    Device createBridgeDeviceStatusBasedOnMqttPayload(BridgeStatusDevice bridgeStatusDevice,
                                                      Optional<Device> optionalBridgeStatus);
}
