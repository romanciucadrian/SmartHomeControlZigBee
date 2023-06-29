package com.app.smarthome.service;

import com.app.smarthome.dtos.CommandDTO;
import com.app.smarthome.dtos.DeviceDTO;
import com.app.smarthome.exceptions.DocumentAlreadyExistsException;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.exceptions.MqttConnectionException;
import com.app.smarthome.models.Command;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

public interface CommandService {

    Command getCommandByName(String name) throws DocumentNotFoundException;

    CommandDTO getCommandDTOByName(String name) throws DocumentNotFoundException;

    List<CommandDTO> getCommandsByDeviceName(String deviceName) throws DocumentNotFoundException;

    CommandDTO addCommand(String commandName, String topic, String payload, String deviceName)
            throws DocumentNotFoundException, DocumentAlreadyExistsException;

    CommandDTO updateCommand(String commandName, String topic, String payload, String deviceName)
            throws DocumentNotFoundException;

    DeviceDTO getCommandResult(String commandName, String deviceName) throws DocumentNotFoundException,
            MqttConnectionException, MqttException, InterruptedException;
}
