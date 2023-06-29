package com.app.smarthome.service;

import com.app.smarthome.dtos.HouseDTO;
import com.app.smarthome.dtos.RoomDTO;
import com.app.smarthome.dtos.UserDTO;
import com.app.smarthome.exceptions.CannotLeaveHouseException;
import com.app.smarthome.exceptions.DocumentAlreadyExistsException;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.exceptions.InvalidRoleException;
import com.app.smarthome.models.House;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

public interface HouseService {

    House getHouseById(ObjectId houseId) throws DocumentNotFoundException;

    HouseDTO getHouseDTOById(ObjectId houseId) throws DocumentNotFoundException;

    void saveDeviceIntoHouse(ObjectId deviceId, House house);

    void removeDeviceFromHouse(ObjectId deviceId, House house);

    House createDefaultHouseWithDefaultRooms();

    RoomDTO createRoomForHouse(ObjectId houseId, String roomName) throws DocumentNotFoundException;

    void removeRoomFromHouse(ObjectId houseId) throws DocumentNotFoundException;

    List<RoomDTO> getAllRoomsByHouseId(ObjectId objectId) throws DocumentNotFoundException;

    HouseDTO createHouseForUser(String houseName, String email) throws DocumentNotFoundException;

    HouseDTO updateHouse(ObjectId houseId, String newHouseName, List<ObjectId> newDeviceIds,
                         List<ObjectId> newRoomIds) throws DocumentNotFoundException;

    List<HouseDTO> getHousesByUserId(ObjectId userId) throws DocumentNotFoundException;

    List<HouseDTO> getHousesByEmail(String email) throws DocumentNotFoundException;

    void removeHouseById(ObjectId houseId) throws DocumentNotFoundException;

    List<UserDTO> getAllUsersDTOByHouseId(ObjectId houseId) throws DocumentNotFoundException;
}
