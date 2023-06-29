package com.app.smarthome.repositories;


import com.app.smarthome.models.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role, ObjectId> {
    Optional<Role> findRoleByName(String name);
    List<Role> findRoleByNameIn(List<String> rootAndAdmin);
}
