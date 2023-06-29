package com.app.smarthome.service;

import com.app.smarthome.dtos.RoomDTO;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.models.Room;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    Optional<Room> getOptionalRoomById(ObjectId roomId);

    void deleteDeviceFromRoom(ObjectId deviceId, Room room);

    List<Room> createDefaultRoomsForHouse(ObjectId houseId);

    Room saveRoomForHouse(String roomName, ObjectId houseId);

    RoomDTO updateRoom(ObjectId roomId, String newName) throws DocumentNotFoundException;

    Room getRoomById(ObjectId objectId) throws DocumentNotFoundException;

    void removeRoom(Room room);

    RoomDTO getRoomDTOById(ObjectId objectId) throws DocumentNotFoundException;

    List<RoomDTO> getRoomDTOsByIds(List<ObjectId> roomIds);

    void removeRoomsByIds(List<ObjectId> roomIds);
}
