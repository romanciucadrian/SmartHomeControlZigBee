package com.app.smarthome.models.devices.bridge.result;

import lombok.Builder;
import lombok.Getter;
import org.json.JSONArray;

@Getter
@Builder
public class BridgeResultDeviceJson {

    private JSONArray zbStatus3;
}
