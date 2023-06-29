package com.app.smarthome.service.impl;

import com.app.smarthome.exceptions.InvalidArgumentException;
import com.app.smarthome.models.Device;
import com.app.smarthome.models.devices.bridge.status.*;
import com.app.smarthome.service.BridgeDeviceStatusService;
import com.app.smarthome.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.smarthome.constants.AppConstants.*;

@Service
public class BridgeDeviceStatusServiceImpl implements BridgeDeviceStatusService {

    @Override
    public BridgeStatusDevice generateBridgeStatusDevice(JSONObject jsonObject)
            throws InvalidArgumentException {
        BridgeStatusDeviceJson bridgeStatusDeviceJson = getBridgeDeviceJson(jsonObject);

        return BridgeStatusDevice.builder()
                .bridgeDeviceStatus(generateBridgeDeviceStatus(bridgeStatusDeviceJson.getBridgeJsonStatus()))
                .bridgeDeviceStatusPRM(generateBridgeDeviceStatusPRM(bridgeStatusDeviceJson.getBridgeJsonStatusPRM()))
                .bridgeDeviceStatusNET(generateBridgeDeviceStatusNET(bridgeStatusDeviceJson.getBridgeJsonStatusNET()))
                .bridgeDeviceStatusLOG(generateBridgeDeviceStatusLOG(bridgeStatusDeviceJson.getBridgeJsonStatusLOG()))
                .build();
    }
    @Override
    public Device createBridgeDeviceStatusBasedOnMqttPayload(BridgeStatusDevice bridgeStatusDevice,
                                                             Optional<Device> optionalBridgeStatus) {
        Device device = optionalBridgeStatus.orElseGet(Device::new);
        device.setName(bridgeStatusDevice.getBridgeDeviceStatus().getDeviceName());
        device.setHexId(ZBBRIDGE);
        device.setTopic(bridgeStatusDevice.getBridgeDeviceStatus().getTopic());
        device.setPower(bridgeStatusDevice.getBridgeDeviceStatus().getPower());
        device.setLedState(bridgeStatusDevice.getBridgeDeviceStatus().getLedState());
        device.setRestartReason(bridgeStatusDevice.getBridgeDeviceStatusPRM().getRestartReason());
        device.setUptime(bridgeStatusDevice.getBridgeDeviceStatusPRM().getUptime());
        device.setStartupUTC(bridgeStatusDevice.getBridgeDeviceStatusPRM().getStartupUTC());
        device.setHostName(bridgeStatusDevice.getBridgeDeviceStatusNET().getHostName());
        device.setIpAddress(bridgeStatusDevice.getBridgeDeviceStatusNET().getIpAddress());
        device.setGateway(bridgeStatusDevice.getBridgeDeviceStatusNET().getGateway());
        device.setSubnetMask(bridgeStatusDevice.getBridgeDeviceStatusNET().getSubnetMask());
        device.setWifiSSId(bridgeStatusDevice.getBridgeDeviceStatusLOG().getWifiSSId());
        device.setTelePeriod(bridgeStatusDevice.getBridgeDeviceStatusLOG().getTelePeriod());
        return device;
    }

    private BridgeStatusDeviceJson getBridgeDeviceJson(JSONObject jsonObject)
            throws InvalidArgumentException {
        return BridgeStatusDeviceJson.builder()
                .bridgeJsonStatus((JSONObject) JsonUtils.getJSONValue(jsonObject, OBJECT, STATUS))
                .bridgeJsonStatusPRM((JSONObject)JsonUtils.getJSONValue(jsonObject, OBJECT, STATUS_PRM))
                .bridgeJsonStatusNET((JSONObject)JsonUtils.getJSONValue(jsonObject, OBJECT, STATUS_NET))
                .bridgeJsonStatusLOG((JSONObject)JsonUtils.getJSONValue(jsonObject, OBJECT, STATUS_LOG))
                .build();
    }
    private BridgeDeviceStatus generateBridgeDeviceStatus(JSONObject jsonStatus)
            throws InvalidArgumentException {
        return BridgeDeviceStatus.builder()
                .deviceName((String)JsonUtils.getJSONValue(jsonStatus, STRING, DEVICE_NAME))
                .topic((String)JsonUtils.getJSONValue(jsonStatus, STRING, TOPIC))
                .power((Long)JsonUtils.getJSONValue(jsonStatus, LONG, POWER))
                .ledState((Long)JsonUtils.getJSONValue(jsonStatus, LONG, LED_STATE))
                .build();
    }
    private BridgeDeviceStatusPRM generateBridgeDeviceStatusPRM(JSONObject jsonStatusPRM)
            throws InvalidArgumentException {
        return BridgeDeviceStatusPRM.builder()
                .restartReason((String)JsonUtils.getJSONValue(jsonStatusPRM, STRING, RESTART_REASON))
                .uptime((String)JsonUtils.getJSONValue(jsonStatusPRM, STRING, UPTIME))
                .startupUTC((String)JsonUtils.getJSONValue(jsonStatusPRM, STRING, STARTUP_UTC))
                .build();
    }
    private BridgeDeviceStatusNET generateBridgeDeviceStatusNET(JSONObject jsonStatusNET)
            throws InvalidArgumentException {
        return BridgeDeviceStatusNET.builder()
                .hostName((String)JsonUtils.getJSONValue(jsonStatusNET, STRING, HOST_NAME))
                .ipAddress((String)JsonUtils.getJSONValue(jsonStatusNET, STRING, IP_ADDRESS))
                .gateway((String)JsonUtils.getJSONValue(jsonStatusNET, STRING, GATEWAY))
                .subnetMask((String)JsonUtils.getJSONValue(jsonStatusNET, STRING, SUBNET_MASK))
                .build();
    }
    private BridgeDeviceStatusLOG generateBridgeDeviceStatusLOG(JSONObject jsonStatusLOG)
            throws InvalidArgumentException {
        JSONArray ssId = (JSONArray)JsonUtils.getJSONValue(jsonStatusLOG, ARRAY, SS_ID);
        List<String> wifiSSId = generateBridgeWifiSSId(ssId);
        return BridgeDeviceStatusLOG.builder()
                .wifiSSId(wifiSSId)
                .telePeriod((Long)JsonUtils.getJSONValue(jsonStatusLOG, LONG, TELE_PERIOD))
                .build();
    }
    private List<String> generateBridgeWifiSSId(JSONArray ssId) {
        List<String> wifiSSId = new ArrayList<>();
        for (Object obj: ssId) {
            wifiSSId.add(String.valueOf(obj));
        }
        return wifiSSId;
    }

}
