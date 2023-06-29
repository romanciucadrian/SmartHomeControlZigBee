package com.app.smarthome.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class DeviceDTO implements Serializable {

    private String id;

    private String t;

    private String houseId;

    private String roomId;

    private String sceneId;

    private String deviceTypeId;

    private String userId;

    private String name;

    private String hexId;

    private String topic;

    private Long power;

    private Long ledState;

    private String restartReason;

    private String uptime;

    private String startupUTC;

    private List<String> wifiSSId;

    private Long telePeriod;

    private String hostName;

    private String ipAddress;

    private String gateway;

    private String subnetMask;

    private BigDecimal temperature;

    private String temperatureUnit;

    private String power1;

    private String power2;

    private String device;

    private Long hue;

    private Long sat;

    private BigDecimal temperature2;

    private Float humidity;

    private String modelId;

    private String manufacturer;

    private Boolean reachable;

    private Long lastSeen;

    private Long lastSeenEpoch;

    private Long batteryLastSeenEpoch;

    private Long linkQuality;

    private String ieeeAddr;

    private List<Long> endpoints;

    private Long zoneStatus;

    private Long zoneType;
}
