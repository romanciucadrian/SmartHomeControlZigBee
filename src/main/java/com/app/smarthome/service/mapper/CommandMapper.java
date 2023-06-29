package com.app.smarthome.service.mapper;

import com.app.smarthome.dtos.CommandDTO;
import com.app.smarthome.models.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandMapper {

    public static List<CommandDTO> createDTOListFromEntityList(List<Command> commands){
        List<CommandDTO> commandsDTO = new ArrayList<>();

        for(Command currentCommand : commands) {
            CommandDTO commandDTO = createDTOFromEntity(currentCommand);
            commandsDTO.add(commandDTO);
        }
        return commandsDTO;
    }

    public static CommandDTO createDTOFromEntity(Command command) {
        CommandDTO commandDTO = new CommandDTO();
        setCommandDTOBasedOnEntity(command, commandDTO);
        return commandDTO;
    }

    private static void setCommandDTOBasedOnEntity(Command command, CommandDTO commandDTO) {
        commandDTO.setDeviceId(command.getDeviceId() == null ? null : command.getDeviceId().toHexString());
        commandDTO.setDeviceName((command.getDeviceName()));
        commandDTO.setName(command.getName());
        commandDTO.setTopic(command.getTopic());
        commandDTO.setPayload(command.getPayload());
    }
}
