package com.app.smarthome.models.devices.nspanel;

import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;

@Getter
@Builder
public class NSPanelDeviceJson {

    private JSONObject nsPanelJsonStatus;
    private JSONObject nsPanelJsonStatusPRM;
    private JSONObject nsPanelJsonStatusNET;
    private JSONObject nsPanelJsonStatusSNS;
    private JSONObject nsPanelJsonStatusSTS;
    private JSONObject nsPanelJsonStatusLOG;
}
