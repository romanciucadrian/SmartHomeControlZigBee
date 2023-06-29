package com.app.smarthome.repositories;

import com.app.smarthome.models.House;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends MongoRepository<House, ObjectId> {
    List<House> findAllByIdIn(List<ObjectId> houses);
}
