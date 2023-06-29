package com.app.smarthome.configuration;


import com.app.smarthome.exceptions.InvalidArgumentException;
import com.app.smarthome.service.MqttMessageProcessor;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.app.smarthome.constants.AppConstants.*;

@Configuration
public class MqttConfig {

    @Value("${mqtt.broker.uri}")
    private String mqttBrokerUri;
    @Value("${mqtt.client.id}")
    private String mqttClientId;
    @Value("${mqtt.automatic.reconnect}")
    private Boolean mqttAutomaticReconnect;
    @Value("${mqtt.clean.session}")
    private Boolean mqttCleanSession;
    @Value("${mqtt.connection.timeout}")
    private Integer mqttConnectionTimeout;
    @Value("${cmnd.bridge.topic.filter}")
    private String cmndBridgeTopicFilter;
    @Value("${cmnd.nspanelswitch.topic.filter}")
    private String cmndNspanelSwitchTopicFilter;
    @Value("${cmnd.dr2switch.topic.filter}")
    private String cmndDr2SwitchTopicFilter;
    @Value("${stat.bridge.topic.filter}")
    private String statBridgeTopicFilter;
    @Value("${stat.nspanelswitch.topic.filter}")
    private String statNspanelSwitchTopicFilter;
    @Value("${stat.dr2switch.topic.filter}")
    private String statDr2SwitchTopicFilter;

    @Autowired
    private MqttMessageProcessor mqttMessageProcessor;


    private static final Logger LOGGER = LoggerFactory.getLogger(MqttConfig.class);

    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient mqttClient = new MqttClient(mqttBrokerUri, mqttClientId);
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                LOGGER.error("Connection lost. " + throwable);
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws InvalidArgumentException {
                LOGGER.debug("topic: {}, message: {}", topic, mqttMessage);
                switch (topic) {
                    case STAT_BRIDGE_STATUS0 ->
                            mqttMessageProcessor.processBridgeStatus0AndSaveDevice(mqttMessage.toString());
                    case STAT_BRIDGE_RESULT ->
                            mqttMessageProcessor.processBridgeResultAndSaveDevice(mqttMessage.toString());
                    case STAT_NSPANELSWITCH_STATUS0 ->
                            mqttMessageProcessor.processNspanelSwitchStatus0AndSaveDevice(mqttMessage.toString());
                    case STAT_NSPANELSWITCH_RESULT ->
                            mqttMessageProcessor.processNspanelSwitchResultAndSaveDevice(mqttMessage.toString());
                }
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                LOGGER.debug("deliveryComplete: {}",  iMqttDeliveryToken);
            }
        });
        mqttClient.connect(mqttConnectOptions());
        mqttClient.subscribe(new String[]{cmndBridgeTopicFilter, cmndNspanelSwitchTopicFilter, cmndDr2SwitchTopicFilter,
                statBridgeTopicFilter, statNspanelSwitchTopicFilter, statDr2SwitchTopicFilter});
        return mqttClient;
    }

    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{mqttBrokerUri});
        options.setAutomaticReconnect(mqttAutomaticReconnect);
        options.setCleanSession(mqttCleanSession);
        options.setConnectionTimeout(mqttConnectionTimeout);
        return options;
    }


}
