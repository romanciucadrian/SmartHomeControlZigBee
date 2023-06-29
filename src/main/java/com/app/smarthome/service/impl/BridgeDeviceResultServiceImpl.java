package com.app.smarthome.service.impl;

import com.app.smarthome.exceptions.InvalidArgumentException;
import com.app.smarthome.models.Device;
import com.app.smarthome.models.devices.bridge.result.BridgeResultDevice;
import com.app.smarthome.models.devices.bridge.result.BridgeResultDeviceJson;
import com.app.smarthome.models.devices.sensors.*;
import com.app.smarthome.service.BridgeDeviceResultService;
import com.app.smarthome.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.app.smarthome.constants.AppConstants.*;

@Service
@RequiredArgsConstructor
public class BridgeDeviceResultServiceImpl implements BridgeDeviceResultService {

    private final MessageSource messageSource;

    @Override
    public BridgeResultDevice generateBridgeResultDevice(JSONObject jsonObject) throws InvalidArgumentException {
        BridgeResultDeviceJson bridgeResultDeviceJson = getBridgeResultDeviceJson(jsonObject);
        JSONObject zbStatus3 = bridgeResultDeviceJson.getZbStatus3().getJSONObject(0);
        JSONArray endpointArray = (JSONArray) JsonUtils.getJSONValue(zbStatus3, ARRAY, ENDPOINTS);
        List<Long> endpoints = generateEndpoints(endpointArray);
        String bridgeResultDeviceHexId = (String) JsonUtils.getJSONValue(zbStatus3, STRING, DEVICE);

        return switch (bridgeResultDeviceHexId) {
            case Ox431C -> createBridgeMotionSensorResultDevice(zbStatus3, endpoints);
            case Ox79BD -> createBridgeTempHumResultDevice(zbStatus3, endpoints);
            case Ox66B9 -> createBridgeMagneticResultDevice(zbStatus3, endpoints);
            case Ox9A74 -> createBridgeSonOffSwitchResultDevice(zbStatus3, endpoints);
            default -> throw new InvalidArgumentException(messageSource.getMessage("invalid.bridge.result.device",
                    null, Locale.getDefault()));
        };
    }

    private BridgeResultDevice createBridgeMotionSensorResultDevice(JSONObject zbStatus3,
                                                                    List<Long> endpoints) throws InvalidArgumentException {
        return MotionSensorDevice.builder()
                .device((String) JsonUtils.getJSONValue(zbStatus3, STRING, DEVICE))
                .reachable((Boolean) JsonUtils.getJSONValue(zbStatus3, BOOLEAN, REACHABLE))
                .lastSeen((Long) JsonUtils.getJSONValue(zbStatus3, LONG, LAST_SEEN))
                .lastSeenEpoch((Long) JsonUtils.getJSONValue(zbStatus3, LONG, LAST_SEEN_EPOCH))
                .linkQuality((Long) JsonUtils.getJSONValue(zbStatus3, LONG, LINK_QUALITY))
                .endpoints(endpoints)
                .batteryPercentage((Long) JsonUtils.getJSONValue(zbStatus3, LONG, BATTERY_PERCENTAGE))
                .zoneStatus((Long) JsonUtils.getJSONValue(zbStatus3, LONG, ZONE_STATUS))
                .zoneType((Long) JsonUtils.getJSONValue(zbStatus3, LONG, ZONE_TYPE))
                .occupancy((Long) JsonUtils.getJSONValue(zbStatus3, LONG, OCCUPANCY))
                .batteryLastSeenEpoch((Long) JsonUtils.getJSONValue(zbStatus3, LONG, BATTERY_SEEN))
                .build();
    }

    private BridgeResultDevice createBridgeTempHumResultDevice(JSONObject zbStatus3,
                                                               List<Long> endpoints) throws InvalidArgumentException {
        return TempHumDevice.builder()
                .device((String) JsonUtils.getJSONValue(zbStatus3, STRING, DEVICE))
                .modelId((String) JsonUtils.getJSONValue(zbStatus3, STRING, MODEL_ID))
                .manufacturer((String) JsonUtils.getJSONValue(zbStatus3, STRING, MANUFACTURER))
                .reachable((Boolean) JsonUtils.getJSONValue(zbStatus3, BOOLEAN, REACHABLE))
                .lastSeen((Long) JsonUtils.getJSONValue(zbStatus3, LONG, LAST_SEEN))
                .lastSeenEpoch((Long) JsonUtils.getJSONValue(zbStatus3, LONG, LAST_SEEN_EPOCH))
                .batteryLastSeenEpoch((Long) JsonUtils.getJSONValue(zbStatus3, LONG, BATTERY_SEEN))
                .linkQuality((Long) JsonUtils.getJSONValue(zbStatus3, LONG, LINK_QUALITY))
                .endpoints(endpoints)
                .temperature((BigDecimal) JsonUtils.getJSONValue(zbStatus3, BIG_DECIMAL, TEMPERATURE2))
                .humidity((Float) JsonUtils.getJSONValue(zbStatus3, LONG, HUMIDITY))
                .build();
    }

    private BridgeResultDevice createBridgeMagneticResultDevice(JSONObject zbStatus3,
                                                                List<Long> endpoints) throws InvalidArgumentException {
        return PowerOutletDevice.builder()
                .ieeeAddr((String) JsonUtils.getJSONValue(zbStatus3, STRING, IEEEADDR))
                .device((String) JsonUtils.getJSONValue(zbStatus3, STRING, DEVICE))
                .reachable((Boolean) JsonUtils.getJSONValue(zbStatus3, BOOLEAN, REACHABLE))
                .lastSeen((Long) JsonUtils.getJSONValue(zbStatus3, LONG, LAST_SEEN))
                .batteryLastSeenEpoch((Long) JsonUtils.getJSONValue(zbStatus3, LONG, BATTERY_SEEN))
                .lastSeenEpoch((Long) JsonUtils.getJSONValue(zbStatus3, LONG, LAST_SEEN_EPOCH))
                .linkQuality((Long) JsonUtils.getJSONValue(zbStatus3, LONG, LINK_QUALITY))
                .endpoints(endpoints)
                .power((Long) JsonUtils.getJSONValue(zbStatus3, LONG, POWER))
                .build();
    }

    private BridgeResultDevice createBridgeSonOffSwitchResultDevice(JSONObject zbStatus3,
                                                                    List<Long> endpoints) throws InvalidArgumentException {
        return MagneticDevice.builder()
                .ieeeAddr((String) JsonUtils.getJSONValue(zbStatus3, STRING, NAME))
                .device((String) JsonUtils.getJSONValue(zbStatus3, STRING, DEVICE))
                .reachable((Boolean) JsonUtils.getJSONValue(zbStatus3, BOOLEAN, REACHABLE))
                .lastSeen((Long) JsonUtils.getJSONValue(zbStatus3, LONG, LAST_SEEN))
                .lastSeenEpoch((Long) JsonUtils.getJSONValue(zbStatus3, LONG, LAST_SEEN_EPOCH))
                .linkQuality((Long) JsonUtils.getJSONValue(zbStatus3, LONG, LINK_QUALITY))
                .endpoints(endpoints)
                .build();
    }
    private BridgeResultDeviceJson getBridgeResultDeviceJson(JSONObject jsonObject) throws InvalidArgumentException {
        return BridgeResultDeviceJson.builder()
                .zbStatus3((JSONArray) JsonUtils.getJSONValue(jsonObject, ARRAY, ZB_STATUS_3))
                .build();
    }

    private List<Long> generateEndpoints(JSONArray endpointArray) {
        List<Long> endpoints = new ArrayList<>();
        for (Object obj: endpointArray) {
            endpoints.add(Long.parseLong(String.valueOf(obj)));
        }
        return endpoints;
    }

    @Override
    public Device createBridgeResultDeviceBasedOnMqttPayload(BridgeResultDevice bridgeResultDevice,
                                                             Optional<Device> optionalBridgeResult)
            throws InvalidArgumentException {
        Device device = optionalBridgeResult.orElseGet(Device::new);
        device.setName(bridgeResultDevice.getIeeeAddr());
        device.setDevice(bridgeResultDevice.getDevice());
        device.setHexId(bridgeResultDevice.getDevice());
        device.setBatteryPercentage(bridgeResultDevice.getBatteryPercentage());
        device.setBatteryLastSeenEpoch(bridgeResultDevice.getBatteryLastSeenEpoch());
        device.setReachable(bridgeResultDevice.getReachable());
        device.setLastSeen(bridgeResultDevice.getLastSeen());
        device.setIeeeAddr(bridgeResultDevice.getIeeeAddr());
        device.setLastSeenEpoch(bridgeResultDevice.getLastSeenEpoch());
        device.setLinkQuality(bridgeResultDevice.getLinkQuality());
        device.setEndpoints(bridgeResultDevice.getEndpoints());
        switch (bridgeResultDevice.getDevice()) {
            case Ox431C -> {
                device.setZoneStatus(((MotionSensorDevice) bridgeResultDevice).getZoneStatus());
                device.setZoneType(((MotionSensorDevice) bridgeResultDevice).getZoneType());
                device.setOccupancy(((MotionSensorDevice) bridgeResultDevice).getOccupancy());
            }
            case Ox79BD -> {
                device.setTemperature2(((TempHumDevice) bridgeResultDevice).getTemperature());
                device.setHumidity(((TempHumDevice) bridgeResultDevice).getHumidity());
                device.setModelId(((TempHumDevice) bridgeResultDevice).getModelId());
                device.setManufacturer(((TempHumDevice) bridgeResultDevice).getManufacturer());
            }
            case Ox66B9 -> {}
            default -> throw new InvalidArgumentException(messageSource.getMessage("invalid.bridge.result.device",
                    null, Locale.getDefault()));
        }
        return device;
    }
}
