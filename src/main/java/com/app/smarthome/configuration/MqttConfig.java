package com.app.smarthome.configuration;


import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    @Value("${mqtt.url}")
    private String mqttURL;
    @Value("${mqtt.clientID}")
    private String mqttClientID;
    private static MqttAsyncClient client;


    public MqttConnectOptions mqttConnectOptions() {

        MqttConnectOptions options = new MqttConnectOptions();

        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);

        return options;
    }

    public MqttAsyncClient getClient() {
        try {

            if (client == null) {
               client =
                       new MqttAsyncClient(mqttURL, mqttClientID);
            }

            if (!client.isConnected()) {
                client.connect(mqttConnectOptions())
                      .waitForCompletion();
            }

        } catch (MqttException e) {
            e.printStackTrace();
        }
        return client;
    }


}
