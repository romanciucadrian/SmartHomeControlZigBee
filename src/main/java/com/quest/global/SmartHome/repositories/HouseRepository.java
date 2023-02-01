package com.quest.global.SmartHome.repositories;


import com.quest.global.SmartHome.models.House;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseRepository extends MongoRepository<House, ObjectId> {

    House findByName(String houseName);

    @Override
    Optional<House> findById(ObjectId id);
}
