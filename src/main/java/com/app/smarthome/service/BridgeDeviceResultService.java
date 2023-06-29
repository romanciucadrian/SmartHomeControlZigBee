package com.app.smarthome.service;


import com.app.smarthome.exceptions.InvalidArgumentException;
import com.app.smarthome.models.Device;
import com.app.smarthome.models.devices.bridge.result.BridgeResultDevice;
import org.json.JSONObject;

import java.util.Optional;

public interface BridgeDeviceResultService {

    BridgeResultDevice generateBridgeResultDevice(JSONObject jsonObject) throws InvalidArgumentException;

    Device createBridgeResultDeviceBasedOnMqttPayload(BridgeResultDevice bridgeResultDevice,
                                                      Optional<Device> optionalBridgeResult) throws InvalidArgumentException;
}
