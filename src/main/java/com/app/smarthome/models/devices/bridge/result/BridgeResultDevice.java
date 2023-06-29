package com.app.smarthome.models.devices.bridge.result;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class BridgeResultDevice {

    protected String device;
    protected String ieeeAddr;
    protected Boolean reachable;
    protected Long lastSeen;
    protected Long lastSeenEpoch;
    protected Long linkQuality;
    protected Long batteryPercentage;
    protected Long batteryLastSeenEpoch;
    protected List<Long> endpoints;

}
