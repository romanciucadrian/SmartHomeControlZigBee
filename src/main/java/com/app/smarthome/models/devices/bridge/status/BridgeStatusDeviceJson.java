package com.app.smarthome.models.devices.bridge.status;

import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;

@Getter
@Builder
public class BridgeStatusDeviceJson {
    private JSONObject bridgeJsonStatus;
    private JSONObject bridgeJsonStatusPRM;
    private JSONObject bridgeJsonStatusNET;
    private JSONObject bridgeJsonStatusLOG;
}
