package com.quest.global.SmartHome.repositories;

import com.quest.global.SmartHome.models.ERole;
import com.quest.global.SmartHome.models.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, ObjectId> {

    Optional<Role> findByName(ERole name);
}
