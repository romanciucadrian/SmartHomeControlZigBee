package com.app.smarthome.service.impl;

import com.app.smarthome.exceptions.InvalidArgumentException;
import com.app.smarthome.models.Device;
import com.app.smarthome.models.devices.nspanel.*;
import com.app.smarthome.models.devices.nspanel.status.*;
import com.app.smarthome.service.NSPanelDeviceService;
import com.app.smarthome.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.smarthome.constants.AppConstants.*;

@Service
@RequiredArgsConstructor
public class NSPanelDeviceServiceImpl implements NSPanelDeviceService {

    @Override
    public NSPanelDevice generateNSPanelDevice(JSONObject jsonObject) throws InvalidArgumentException {
        NSPanelDeviceJson nsPanelDeviceJson = getNSPanelDeviceJson(jsonObject);

        return NSPanelDevice.builder()
                .nsPanelDeviceStatus(generateNSPanelDeviceStatus(nsPanelDeviceJson.getNsPanelJsonStatus()))
                .nsPanelDeviceStatusPRM(generateNSPanelDeviceStatusPRM(nsPanelDeviceJson.getNsPanelJsonStatusPRM()))
                .nsPanelDeviceStatusNET(generateNSPanelDeviceStatusNET(nsPanelDeviceJson.getNsPanelJsonStatusNET()))
                .nsPanelDeviceStatusSNS(generateNSPanelDeviceStatusSNS(nsPanelDeviceJson.getNsPanelJsonStatusSNS()))
                .nsPanelDeviceStatusSTS(generateNSPanelDeviceStatusSTS(nsPanelDeviceJson.getNsPanelJsonStatusSTS()))
                .nsPanelDeviceStatusLOG(generateNSPanelDeviceStatusLOG(nsPanelDeviceJson.getNsPanelJsonStatusLOG()))
                .build();
    }

    private NSPanelDeviceJson getNSPanelDeviceJson(JSONObject jsonObject) throws InvalidArgumentException {
        return NSPanelDeviceJson.builder()
                .nsPanelJsonStatus((JSONObject) JsonUtils.getJSONValue(jsonObject, OBJECT, STATUS))
                .nsPanelJsonStatusPRM((JSONObject)JsonUtils.getJSONValue(jsonObject, OBJECT, STATUS_PRM))
                .nsPanelJsonStatusNET((JSONObject)JsonUtils.getJSONValue(jsonObject, OBJECT, STATUS_NET))
                .nsPanelJsonStatusSNS((JSONObject)JsonUtils.getJSONValue(jsonObject, OBJECT, STATUS_SNS))
                .nsPanelJsonStatusSTS((JSONObject)JsonUtils.getJSONValue(jsonObject, OBJECT, STATUS_STS))
                .nsPanelJsonStatusLOG((JSONObject)JsonUtils.getJSONValue(jsonObject, OBJECT, STATUS_LOG))
                .build();
    }

    private NSPanelDeviceStatus generateNSPanelDeviceStatus(JSONObject jsonStatus) throws InvalidArgumentException {
        return NSPanelDeviceStatus.builder()
                .deviceName((String)JsonUtils.getJSONValue(jsonStatus, STRING, DEVICE_NAME))
                .topic((String)JsonUtils.getJSONValue(jsonStatus, STRING, TOPIC))
                .power((Long)JsonUtils.getJSONValue(jsonStatus, LONG, POWER))
                .build();
    }

    private NSPanelDeviceStatusPRM generateNSPanelDeviceStatusPRM(JSONObject jsonStatusPRM) throws InvalidArgumentException {
        return NSPanelDeviceStatusPRM.builder()
                .restartReason((String)JsonUtils.getJSONValue(jsonStatusPRM, STRING, RESTART_REASON))
                .uptime((String)JsonUtils.getJSONValue(jsonStatusPRM, STRING, UPTIME))
                .startupUTC((String)JsonUtils.getJSONValue(jsonStatusPRM, STRING, STARTUP_UTC))
                .build();
    }

    private NSPanelDeviceStatusNET generateNSPanelDeviceStatusNET(JSONObject jsonStatusNET) throws InvalidArgumentException {
        return NSPanelDeviceStatusNET.builder()
                .hostName((String)JsonUtils.getJSONValue(jsonStatusNET, STRING, HOST_NAME))
                .ipAddress((String)JsonUtils.getJSONValue(jsonStatusNET, STRING, IP_ADDRESS))
                .gateway((String)JsonUtils.getJSONValue(jsonStatusNET, STRING, GATEWAY))
                .subnetMask((String)JsonUtils.getJSONValue(jsonStatusNET, STRING, SUBNET_MASK))
                .build();
    }

    private NSPanelDeviceStatusSNS generateNSPanelDeviceStatusSNS(JSONObject jsonStatusSNS) throws InvalidArgumentException {
        JSONObject analog = (JSONObject)JsonUtils.getJSONValue(jsonStatusSNS, OBJECT, ANALOG);
        return NSPanelDeviceStatusSNS.builder()
                .temperature((BigDecimal)JsonUtils.getJSONValue(analog, BIG_DECIMAL, TEMPERATURE1))
                .temperatureUnit((String)JsonUtils.getJSONValue(jsonStatusSNS, STRING, TEMP_UNIT))
                .build();
    }

    @Override
    public NSPanelDeviceStatusSTS generateNSPanelDeviceStatusSTS(JSONObject jsonStatusSTS) throws InvalidArgumentException {
        return NSPanelDeviceStatusSTS.builder()
                .power1(jsonStatusSTS.has(POWER1) ? (String)JsonUtils.getJSONValue(jsonStatusSTS, STRING, POWER1) : null)
                .power2(jsonStatusSTS.has(POWER2) ? (String)JsonUtils.getJSONValue(jsonStatusSTS, STRING, POWER2) : null)
                .build();
    }

    private NSPanelDeviceStatusLOG generateNSPanelDeviceStatusLOG(JSONObject jsonStatusLOG) throws InvalidArgumentException {
        JSONArray ssId = (JSONArray)JsonUtils.getJSONValue(jsonStatusLOG, ARRAY, SS_ID);
        List<String> wifiSSId = generateNSPanelWifiSSId(ssId);
        return NSPanelDeviceStatusLOG.builder()
                .wifiSSId(wifiSSId)
                .telePeriod((Long)JsonUtils.getJSONValue(jsonStatusLOG, LONG, TELE_PERIOD))
                .build();
    }
    private List<String> generateNSPanelWifiSSId(JSONArray ssId) {
        List<String> wifiSSId = new ArrayList<>();
        for (Object obj: ssId) {
            wifiSSId.add(String.valueOf(obj));
        }
        return wifiSSId;
    }

    @Override
    public Device createNSPanelDeviceBasedOnMqttPayload(NSPanelDevice nsPanelDevice, Optional<Device> optionalNsPanel) {
        Device device;
        device = optionalNsPanel.orElseGet(Device::new);
        device.setName(nsPanelDevice.getNsPanelDeviceStatus().getDeviceName());
        device.setHexId(NSPANEL);
        device.setTopic(nsPanelDevice.getNsPanelDeviceStatus().getTopic());
        device.setPower(nsPanelDevice.getNsPanelDeviceStatus().getPower());
        device.setRestartReason(nsPanelDevice.getNsPanelDeviceStatusPRM().getRestartReason());
        device.setUptime(nsPanelDevice.getNsPanelDeviceStatusPRM().getUptime());
        device.setStartupUTC(nsPanelDevice.getNsPanelDeviceStatusPRM().getStartupUTC());
        device.setHostName(nsPanelDevice.getNsPanelDeviceStatusNET().getHostName());
        device.setIpAddress(nsPanelDevice.getNsPanelDeviceStatusNET().getIpAddress());
        device.setGateway(nsPanelDevice.getNsPanelDeviceStatusNET().getGateway());
        device.setSubnetMask(nsPanelDevice.getNsPanelDeviceStatusNET().getSubnetMask());
        device.setTemperature(nsPanelDevice.getNsPanelDeviceStatusSNS().getTemperature());
        device.setTemperatureUnit(nsPanelDevice.getNsPanelDeviceStatusSNS().getTemperatureUnit());
        device.setPower1(nsPanelDevice.getNsPanelDeviceStatusSTS().getPower1());
        device.setPower2(nsPanelDevice.getNsPanelDeviceStatusSTS().getPower2());
        device.setWifiSSId(nsPanelDevice.getNsPanelDeviceStatusLOG().getWifiSSId());
        device.setTelePeriod(nsPanelDevice.getNsPanelDeviceStatusLOG().getTelePeriod());
        return device;
    }
    @Override
    public Device createNSPanelDeviceStatusBasedOnMqttPayload(NSPanelDeviceStatusSTS nsPanelDeviceStatusSTS, Optional<Device> optionalNsPanel) {
        Device device;
        device = optionalNsPanel.orElseGet(Device::new);
        device.setName(NSPANEL);
        device.setHexId(NSPANEL);
        device.setPower1(nsPanelDeviceStatusSTS.getPower1() != null ? nsPanelDeviceStatusSTS.getPower1() : device.getPower1());
        device.setPower2(nsPanelDeviceStatusSTS.getPower2() != null ? nsPanelDeviceStatusSTS.getPower2() : device.getPower2());
        return device;
    }
}
