package com.app.smarthome.services.impl;

import com.app.smarthome.exceptions.HouseNotFoundException;
import com.app.smarthome.models.Room;
import org.bson.types.ObjectId;

public interface IRoomService {

    Room createRoomForAHouse(ObjectId houseID, Room room) throws HouseNotFoundException;
}
