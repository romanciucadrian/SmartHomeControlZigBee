package com.app.smarthome.constants;

public class AppConstants {

    // Available Roles
    public static final String ROOT = "Root";
    public static final String ADMIN = "Admin";
    public static final String MEMBER = "Member";

    // Default House Name
    public static final String DEFAULT_HOUSE_NAME = "MyHouse";

    // Default Room Names
    public static final String KITCHEN = "Kitchen";
    public static final String LIVING_ROOM = "LivingRoom";
    public static final String BEDROOM = "Bedroom";

    // Empty String
    public static final String EMPTY_STRING = "";

    // Device Broadcast Keys
    public static final String COLOR_LIGHT_BULB_KEY = "ColorLightBulb";
    public static final String MOTION_SENSOR_KEY = "MotionSensor";
    public static final String LIGHT_BULB_KEY = "LightBulb";
    public static final String POWER_OUTLET_KEY = "PowerOutlet";
    public static final String SON_OFF_SWITCH_KEY = "SonOffSwitch";
    public static final String NSPANEL_KEY = "NSPanel";

    // Valid Device Hex Names
    public static final String ZBBRIDGE = "ZBBridge";
    public static final String OxFE7E = "0xFE7E"; // Colour Light Bulb
    public static final String Ox431C = "0x431C"; // MOTION_SENSOR
    public static final String Ox79BD = "0x79BD"; // TEMP_HUM_SENSOR
    public static final String Ox66B9 = "0x66B9"; // Magnetic Sensor
    public static final String Ox9A74 = "0x9A74"; // SonOffSwitch
    public static final String NSPANEL = "NSPanel";
    public static final String DUALR2 = "DualR2";
    public static final String RM4CMINI = "RM4cMini";

    // Valid MQTT Topics
    public static final String CMND_BRIDGE_STATUS0 = "cmnd/bridge/status0";
    public static final String CMND_BRIDGE_ZBSTATUS3 = "cmnd/bridge/zbstatus3";

    public static final String CMND_BRIDGE_ZBSEND = "cmnd/bridge/zbsend";
    public static final String CMND_NSPANELSWITCH_STATUS0 = "cmnd/nspanelswitch/status0";
    public static final String CMND_NSPANELSWITCH_POWER1 = "cmnd/nspanelswitch/power1";
    public static final String CMND_DR2SWITCH_STATUS0 = "cmnd/dr2switch/status0";
    public static final String STAT_NSPANELSWITCH_STATUS0 = "stat/nspanelswitch/STATUS0";
    public static final String STAT_NSPANELSWITCH_RESULT = "stat/nspanelswitch/RESULT";
    public static final String STAT_BRIDGE_STATUS0 = "stat/bridge/STATUS0";
    public static final String STAT_BRIDGE_RESULT = "stat/bridge/RESULT";

    // Valid MQTT Payloads
    public static final String COLOR_LIGHT_BULB = "ColorLightBulb";
    public static final String MOTION_SENSOR = Ox431C;
    public static final String TEMP_HUM_SENSOR = "0x79BD";
    public static final String MAGNETIC_SENSOR = "0x66B9";
    public static final String SON_OFF_SWITCH = "SonOffSwitch";

    // MQTT Devices Response JSON types
    public static final String OBJECT = "Object";
    public static final String ARRAY = "Array";
    public static final String BIG_DECIMAL = "BigDecimal";
    public static final String STRING = "String";
    public static final String LONG = "Long";
    public static final String BOOLEAN = "Boolean";

    // MQTT Devices Response JSON Keys
    public static final String STATUS = "Status";
    public static final String STATUS_PRM = "StatusPRM";
    public static final String STATUS_NET = "StatusNET";
    public static final String STATUS_SNS = "StatusSNS";
    public static final String STATUS_STS = "StatusSTS";
    public static final String STATUS_LOG = "StatusLOG";
    public static final String DEVICE_NAME = "DeviceName";
    public static final String TOPIC = "Topic";
    public static final String POWER = "Power";
    public static final String LED_STATE = "LedState";
    public static final String RESTART_REASON = "RestartReason";
    public static final String UPTIME = "Uptime";
    public static final String STARTUP_UTC = "StartupUTC";
    public static final String SS_ID = "SSId";
    public static final String TELE_PERIOD = "TelePeriod";
    public static final String HOST_NAME = "Hostname";
    public static final String IP_ADDRESS = "IPAddress";
    public static final String GATEWAY = "Gateway";
    public static final String SUBNET_MASK = "Subnetmask";
    public static final String ANALOG = "ANALOG";
    public static final String TEMPERATURE1 = "Temperature1";
    public static final String TEMP_UNIT = "TempUnit";
    public static final String POWER1 = "POWER1";
    public static final String POWER2 = "POWER2";
    public static final String ZB_STATUS_3 = "ZbStatus3";
    public static final String NAME = "Name";
    public static final String DEVICE = "Device";
    public static final String IEEEADDR = "IEEEAddr";
    public static final String BATTERY_SEEN = "BatteryLastSeenEpoch";
    public static final String TEMPERATURE2 = "Temperature";
    public static final String HUMIDITY = "Humidity";
    public static final String MODEL_ID = "ModelId";
    public static final String MANUFACTURER = "Manufacturer";
    public static final String REACHABLE = "Reachable";
    public static final String LAST_SEEN = "LastSeen";
    public static final String LAST_SEEN_EPOCH = "LastSeenEpoch";
    public static final String LINK_QUALITY = "LinkQuality";
    public static final String ENDPOINTS = "Endpoints";
    public static final String BATTERY_PERCENTAGE = "BatteryPercentage";
    public static final String ZONE_STATUS = "ZoneStatus";
    public static final String ZONE_TYPE = "ZoneType";
    public static final String OCCUPANCY = "Occupancy";
}
