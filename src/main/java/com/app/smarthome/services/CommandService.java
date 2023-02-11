package com.app.smarthome.services;

import com.app.smarthome.models.Command;
import com.app.smarthome.repositories.CommandRepository;
import com.app.smarthome.services.impl.ICommandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandService implements ICommandService {

    private final CommandRepository commandRepository;

    public CommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }


    public List<String> getAllTopics() {

        List<Command> allCommandsFromDB = commandRepository.findAll();

        return allCommandsFromDB
       .stream()
       .map(Command::getTopic)
       .collect(Collectors.toList());

    }
}
