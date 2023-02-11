package com.app.smarthome.repositories;

import com.app.smarthome.models.ERole;
import com.app.smarthome.models.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, ObjectId> {

    Optional<Role> findByName(ERole name);
}
