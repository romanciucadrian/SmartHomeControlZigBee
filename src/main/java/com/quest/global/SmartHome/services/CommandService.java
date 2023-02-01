package com.quest.global.SmartHome.services;

import com.quest.global.SmartHome.models.Command;
import com.quest.global.SmartHome.repositories.CommandRepository;
import com.quest.global.SmartHome.services.impl.ICommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        List<String> allTopicsFromCommands =
                 allCommandsFromDB
                .stream()
                .map(Command::getTopic)
                .collect(Collectors.toList());

        return allTopicsFromCommands;

    }
}
