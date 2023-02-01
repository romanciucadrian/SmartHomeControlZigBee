package com.quest.global.SmartHome.repositories;

import com.quest.global.SmartHome.dtos.UserViewDTO;
import com.quest.global.SmartHome.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByUserName(String userName);

    Optional<User> findUserByEmail(String email);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

}
