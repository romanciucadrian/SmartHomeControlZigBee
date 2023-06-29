package com.app.smarthome.models;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Document("Devices")
public class Device {

    @Id
    private ObjectId id;

    @Field("_t")
    private String t;

    @Field("HouseId")
    private ObjectId houseId;

    @Field("RoomId")
    private ObjectId roomId;

    @Field("SceneId")
    private ObjectId sceneId;

    @Field("DeviceTypeId")
    private ObjectId deviceTypeId;

    @Field("UserId")
    private ObjectId userId;

    @Field("Name")
    private String name;

    @Field("HexId")
    private String hexId;

    @Field("Topic")
    private String topic;

    @Field("LedState")
    private Long ledState;

    @Field("RestartReason")
    private String restartReason;

    @Field("Uptime")
    private String uptime;

    @Field("StartupUTC")
    private String startupUTC;

    @Field("WifiSSId")
    private List<String> wifiSSId;

    @Field("TelePeriod")
    private Long telePeriod;

    @Field("HostName")
    private String hostName;

    @Field("IpAddress")
    private String ipAddress;

    @Field("Gateway")
    private String gateway;

    @Field("SubnetMask")
    private String subnetMask;

    @Field("Temperature")
    private BigDecimal temperature;

    @Field("TemperatureUnit")
    private String temperatureUnit;

    @Field("Power1")
    private String power1;

    @Field("Power2")
    private String power2;

    @Field("Device")
    private String device;

    @Field("Hue")
    private Long hue;

    @Field("Sat")
    private Long sat;

    @Field("Power")
    private Long power;

    @Field("Temperature2")
    private BigDecimal temperature2;

    @Field("Humidity")
    private Float humidity;

    @Field("ModelId")
    private String modelId;

    @Field("Manufacturer")
    private String manufacturer;

    @Field("Reachable")
    private Boolean reachable;

    @Field("LastSeen")
    private Long lastSeen;

    @Field("LastSeenEpoch")
    private Long lastSeenEpoch;

    @Field("LinkQuality")
    private Long linkQuality;

    @Field("Endpoints")
    private List<Long> endpoints;

    @Field("BatteryPercentage")
    private Long batteryPercentage;

    @Field("BatteryLastSeenEpoch")
    private Long batteryLastSeenEpoch;

    @Field("IeeeAddr")
    private String ieeeAddr;

    @Field("ZoneStatus")
    private Long zoneStatus;

    @Field("ZoneType")
    private Long zoneType;

    @Field("Occupancy")
    private Long occupancy;

}
