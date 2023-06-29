package com.app.smarthome.service.impl;

import com.app.smarthome.dtos.RoomDTO;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.models.Room;
import com.app.smarthome.repositories.RoomRepository;
import com.app.smarthome.service.RoomService;
import com.app.smarthome.service.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.app.smarthome.constants.AppConstants.*;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {


    private final RoomRepository roomRepository;
    private final MessageSource messageSource;

    @Override
    public Room getRoomById(ObjectId roomId) throws DocumentNotFoundException {
        return getOptionalRoomById(roomId).orElseThrow(() ->
                new DocumentNotFoundException(messageSource.getMessage("no.room.found",
                        null, Locale.getDefault())));
    }

    @Transactional
    @Override
    public void removeRoom(Room room) {
        roomRepository.delete(room);
    }

    @Override
    public Optional<Room> getOptionalRoomById(ObjectId roomId) {
        return roomRepository.findById(roomId);
    }

    @Transactional
    @Override
    public void deleteDeviceFromRoom(ObjectId deviceId, Room room) {
        if (!CollectionUtils.isEmpty(room.getDevices())) {
            room.getDevices().remove(deviceId);
        }
        roomRepository.save(room);
    }

    @Transactional
    @Override
    public List<Room> createDefaultRoomsForHouse(ObjectId houseId) {
        Room kitchen = createRoomForHouse(KITCHEN, houseId);
        Room livingRoom = createRoomForHouse(LIVING_ROOM, houseId);
        Room bedroom = createRoomForHouse(BEDROOM, houseId);
        return roomRepository.saveAll(Arrays.asList(kitchen, livingRoom, bedroom));
    }

    @Transactional
    @Override
    public Room saveRoomForHouse(String roomName, ObjectId houseId) {
        Room room = createRoomForHouse(roomName, houseId);
        return roomRepository.save(room);
    }

    @Override
    public RoomDTO updateRoom(ObjectId roomId, String newName) throws DocumentNotFoundException {
        Room room = getRoomById(roomId);
        room.setName(newName);
        room =  roomRepository.save(room);
        return RoomMapper.createDTOFromEntity(room);
    }

    @Override
    public RoomDTO getRoomDTOById(ObjectId objectId) throws DocumentNotFoundException {
        Room room = getRoomById(objectId);
        return RoomMapper.createDTOFromEntity(room);
    }

    @Override
    public List<RoomDTO> getRoomDTOsByIds(List<ObjectId> roomIds) {
        List<Room> rooms = (List<Room>)roomRepository.findAllById(roomIds);
        return RoomMapper.createDTOListFromEntityList(rooms);
    }

    @Override
    public void removeRoomsByIds(List<ObjectId> roomIds) {
        roomRepository.deleteAllById(roomIds);
    }

    private Room createRoomForHouse(String roomName, ObjectId houseId) {
        Room room = new Room();
        room.setName(roomName);
        room.setHouseId(houseId);
        return room;
    }
}
