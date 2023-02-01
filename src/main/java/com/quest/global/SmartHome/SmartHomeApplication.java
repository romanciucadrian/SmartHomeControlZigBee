package com.quest.global.SmartHome;

import com.quest.global.SmartHome.services.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartHomeApplication implements CommandLineRunner {


	private final MqttService mqttService;

	public SmartHomeApplication(MqttService mqttService) {
		this.mqttService = mqttService;
	}


	public static void main(String[] args) {
		SpringApplication.run(SmartHomeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mqttService.doSubscribe();
	}
}
