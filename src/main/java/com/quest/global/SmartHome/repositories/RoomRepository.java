package com.quest.global.SmartHome.repositories;

import com.quest.global.SmartHome.models.Room;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room, ObjectId> {

    Room findRoomByName(String name);

    Room findRoomById(ObjectId id);

}
