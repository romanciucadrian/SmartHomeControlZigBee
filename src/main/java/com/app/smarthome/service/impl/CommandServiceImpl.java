package com.app.smarthome.service.impl;

import com.app.smarthome.dtos.CommandDTO;
import com.app.smarthome.dtos.DeviceDTO;
import com.app.smarthome.exceptions.DocumentAlreadyExistsException;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.exceptions.MqttConnectionException;
import com.app.smarthome.models.Command;
import com.app.smarthome.models.Device;
import com.app.smarthome.repositories.CommandRepository;
import com.app.smarthome.service.CommandService;
import com.app.smarthome.service.DeviceService;
import com.app.smarthome.service.MqttService;
import com.app.smarthome.service.mapper.CommandMapper;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.app.smarthome.constants.AppConstants.EMPTY_STRING;

@Service
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {

    private final CommandRepository commandRepository;
    private final DeviceService deviceService;
    private final MqttService mqttService;
    private final MessageSource messageSource;

    @Override
    public CommandDTO getCommandDTOByName(String name) throws DocumentNotFoundException {
        Command command = getCommandByName(name);
        return CommandMapper.createDTOFromEntity(command);
    }

    @Override
    public Command getCommandByName(String name) throws DocumentNotFoundException {
        return commandRepository.findCommandByName(name).orElseThrow(() ->
                new DocumentNotFoundException(messageSource.getMessage("no.command.found",
                        null, Locale.getDefault())));
    }

    @Override
    public List<CommandDTO> getCommandsByDeviceName(String deviceName) throws DocumentNotFoundException {
        Device device = deviceService.getDeviceByName(deviceName);
        List<Command> commands = commandRepository.findCommandsByDeviceName(device.getName()).orElseThrow(() ->
                new DocumentNotFoundException(messageSource.getMessage("no.commands.for.device",
                        null, Locale.getDefault())));
        return CommandMapper.createDTOListFromEntityList(commands);
    }

    @Override
    public CommandDTO addCommand(String commandName, String topic, String payload, String deviceName)
            throws DocumentNotFoundException, DocumentAlreadyExistsException {
        Device device = deviceService.getDeviceByName(deviceName);
        Optional<Command> optionalCommand = commandRepository.findCommandByName(commandName);
        if (optionalCommand.isPresent()) {
            throw new DocumentAlreadyExistsException(messageSource.getMessage("command.already.exists",
                    null, Locale.getDefault()));
        }
        payload = payload == null ? EMPTY_STRING : payload;
        Command command = generateCommandBasedOnInput(commandName, topic, payload, device);
        command = commandRepository.save(command);
        return CommandMapper.createDTOFromEntity(command);
    }

    private Command generateCommandBasedOnInput(String commandName, String topic, String payload, Device device) {
        Command command = new Command();
        command.setName(commandName);
        command.setTopic(topic);
        command.setPayload(payload);
        command.setDeviceId(device.getId());
        command.setDeviceName(device.getName());
        return command;
    }

    @Override
    public CommandDTO updateCommand(String commandName, String topic, String payload, String deviceName)
            throws DocumentNotFoundException {
        Command command = commandRepository.findCommandByName(commandName).orElseThrow(() ->
                new DocumentNotFoundException(messageSource.getMessage("no.command.found",
                        null, Locale.getDefault())));
        if (topic != null) {
            command.setTopic(topic);
        }
        if (payload != null) {
            command.setPayload(payload);
        }
        if (deviceName != null) {
            command.setDeviceName(deviceName);
        }
        command = commandRepository.save(command);
        return CommandMapper.createDTOFromEntity(command);
    }

    @Override
    public DeviceDTO getCommandResult(String commandName, String deviceName) throws DocumentNotFoundException,
            MqttConnectionException, MqttException, InterruptedException {
        Boolean deviceExists = deviceService.existsByName(deviceName);
        if (!deviceExists) {
            throw new DocumentNotFoundException(messageSource.getMessage("device.not.found.in.system",
                    null, Locale.getDefault()));
        }
        CommandDTO commandDTO = getCommandDTOByName(commandName);
        if (!mqttService.isConnectedToMQTT()) {
            throw new MqttConnectionException(messageSource.getMessage("not.established.mqtt.connection",
                    null, Locale.getDefault()));
        }
        mqttService.sendMessage(commandDTO.getTopic(), commandDTO.getPayload());
        Thread.sleep(1000);
        return deviceService.getDeviceDTOByName(commandDTO.getDeviceName());
    }
}
