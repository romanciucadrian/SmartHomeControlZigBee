package com.app.smarthome.repositories;

import com.app.smarthome.models.Command;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommandRepository extends MongoRepository<Command, ObjectId> {
    Optional<Command> findCommandByName(String name);
    Optional<List<Command>> findCommandsByDeviceName(String deviceName);
}
