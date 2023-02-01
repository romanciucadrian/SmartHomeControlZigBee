package com.quest.global.SmartHome.services.impl;

import com.quest.global.SmartHome.exceptions.HouseNotFoundException;
import com.quest.global.SmartHome.models.Room;
import org.bson.types.ObjectId;

public interface IRoomService {

    Room createRoomForAHouse(ObjectId houseID, Room room) throws HouseNotFoundException;
}
