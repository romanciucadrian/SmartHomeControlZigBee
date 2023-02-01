package com.quest.global.SmartHome.repositories;

import com.quest.global.SmartHome.models.Command;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommandRepository extends MongoRepository<Command, ObjectId > {

    Optional<Command> findCommandByCommandName(String commandName);

    Optional<Command> findCommandByDeviceNameCommand(String deviceNameCommand);

    List<Command> findAll();

}
