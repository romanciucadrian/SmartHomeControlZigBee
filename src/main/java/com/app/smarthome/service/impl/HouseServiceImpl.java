package com.app.smarthome.service.impl;


import com.app.smarthome.dtos.HouseDTO;
import com.app.smarthome.dtos.RoomDTO;
import com.app.smarthome.dtos.UserDTO;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.models.*;
import com.app.smarthome.repositories.HouseRepository;
import com.app.smarthome.service.*;
import com.app.smarthome.service.mapper.HouseMapper;
import com.app.smarthome.service.mapper.RoomMapper;
import com.app.smarthome.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.app.smarthome.constants.AppConstants.*;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {


    private final HouseRepository houseRepository;

    private final RoomService roomService;

    private final UserService userService;

    private final MessageSource messageSource;

    @Override
    public House getHouseById(ObjectId houseId) throws DocumentNotFoundException {
        return houseRepository.findById(houseId).orElseThrow(() ->
                new DocumentNotFoundException(messageSource.getMessage("no.house.found",
                        null, Locale.getDefault())));
    }

    @Override
    public HouseDTO getHouseDTOById(ObjectId houseId) throws DocumentNotFoundException {
        House house = getHouseById(houseId);
        return HouseMapper.createDTOFromEntity(house);
    }

    @Transactional
    @Override
    public void saveDeviceIntoHouse(ObjectId deviceId, House house) {
        if (CollectionUtils.isEmpty(house.getDevices())) {
            house.setDevices(new ArrayList<>());
        }
        house.getDevices().add(deviceId);
        houseRepository.save(house);
    }

    @Transactional
    @Override
    public void removeDeviceFromHouse(ObjectId deviceId, House house) {
        if (!CollectionUtils.isEmpty(house.getDevices())) {
            house.getDevices().remove(deviceId);
        }
        houseRepository.save(house);
    }

    @Transactional
    @Override
    public House createDefaultHouseWithDefaultRooms() {
        House house = new House();
        house.setName(DEFAULT_HOUSE_NAME);
        house.setDevices(new ArrayList<>());
        house = houseRepository.save(house);
        List<Room> defaultRooms = roomService.createDefaultRoomsForHouse(house.getId());
        house.setRooms(defaultRooms.stream().map(Room::getId).collect(Collectors.toList()));
        house = houseRepository.save(house);
        return house;
    }

    @Override
    public RoomDTO createRoomForHouse(ObjectId houseId, String roomName) throws DocumentNotFoundException {
        House house = getHouseById(houseId);
        Room room = roomService.saveRoomForHouse(roomName, houseId);
        house.getRooms().add(room.getId());
        houseRepository.save(house);
        return RoomMapper.createDTOFromEntity(room);
    }

    @Override
    public void removeRoomFromHouse(ObjectId houseId) throws DocumentNotFoundException {
        Room room = roomService.getRoomById(houseId);
        House house = getHouseById(room.getHouseId());
        house.getRooms().remove(room.getId());
        houseRepository.save(house);
        roomService.removeRoom(room);
    }

    @Override
    public List<RoomDTO> getAllRoomsByHouseId(ObjectId houseId) throws DocumentNotFoundException {
        House house = getHouseById(houseId);
        return roomService.getRoomDTOsByIds(house.getRooms());
    }

    @Override
    public HouseDTO createHouseForUser(String houseName, String email) throws DocumentNotFoundException {
        User user = userService.getUserByEmail(email);
        House house = createNamedHouseWithDefaultRooms(houseName);
        user.getHouses().add(house.getId());
        userService.saveUser(user);
        return HouseMapper.createDTOFromEntity(house);
    }

    @Override
    public HouseDTO updateHouse(ObjectId houseId, String newHouseName, List<ObjectId> newDeviceIds, List<ObjectId> newRoomIds)
            throws DocumentNotFoundException {
        House house = getHouseById(houseId);
        house.setName(newHouseName);
        if (!new HashSet<>(house.getRooms()).containsAll(newRoomIds)) {
            house.setRooms(newRoomIds);
        }
        if (!new HashSet<>(house.getDevices()).containsAll(newDeviceIds)) {
            house.setDevices(newDeviceIds);
        }
        house = houseRepository.save(house);
        return HouseMapper.createDTOFromEntity(house);
    }

    @Override
    public List<HouseDTO> getHousesByUserId(ObjectId userId) throws DocumentNotFoundException {
        User user = userService.getUserById(userId);
        List<House> houses = houseRepository.findAllByIdIn(user.getHouses());
        return HouseMapper.createDTOListFromEntityList(houses);
    }

    @Override
    public List<HouseDTO> getHousesByEmail(String email) throws DocumentNotFoundException {
        User user = userService.getUserByEmail(email);
        List<House> houses = houseRepository.findAllByIdIn(user.getHouses());
        return HouseMapper.createDTOListFromEntityList(houses);
    }

    @Transactional
    @Override
    public void removeHouseById(ObjectId houseId) throws DocumentNotFoundException {
        House house = getHouseById(houseId);
        List<ObjectId> roomIds = house.getRooms();
        roomService.removeRoomsByIds(roomIds);
        List<User> houseUsers = userService.getAllUsersByHouseId(house.getId());
        houseUsers.forEach(hu -> hu.getHouses().remove(house.getId()));
        userService.saveAllUsers(houseUsers);
        houseRepository.delete(house);
    }

    @Transactional
    House createNamedHouseWithDefaultRooms(String houseName) {
        House house = new House();
        house.setName(houseName);
        house.setDevices(new ArrayList<>());
        house = houseRepository.save(house);
        List<Room> defaultRooms = roomService.createDefaultRoomsForHouse(house.getId());
        house.setRooms(defaultRooms.stream().map(Room::getId).collect(Collectors.toList()));
        house = houseRepository.save(house);
        return house;
    }

    @Override
    public List<UserDTO> getAllUsersDTOByHouseId(ObjectId houseId) throws DocumentNotFoundException {
        House house = getHouseById(houseId);
        List<User> houseUsers = userService.getAllUsersByHouseId(house.getId());
        return UserMapper.createDTOListFromEntityList(houseUsers);
    }
}
