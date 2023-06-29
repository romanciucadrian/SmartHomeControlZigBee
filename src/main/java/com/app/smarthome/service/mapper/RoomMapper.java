package com.app.smarthome.service.mapper;

import com.app.smarthome.dtos.RoomDTO;
import com.app.smarthome.models.Room;
import com.app.smarthome.utils.ObjectIdUtils;

import java.util.ArrayList;
import java.util.List;

public class RoomMapper {

    public static List<RoomDTO> createDTOListFromEntityList(List<Room> rooms){
        List<RoomDTO> roomsDTO = new ArrayList<>();

        for(Room currentRoom : rooms) {
            RoomDTO roomDTO = createDTOFromEntity(currentRoom);
            roomsDTO.add(roomDTO);
        }
        return roomsDTO;
    }

    public static RoomDTO createDTOFromEntity(Room room){
        RoomDTO roomDTO = new RoomDTO();
        setHouseDTOBasedOnEntity(room, roomDTO);
        return roomDTO;
    }

    private static void setHouseDTOBasedOnEntity(Room room, RoomDTO roomDTO) {
        roomDTO.setId(room.getId().toHexString());
        roomDTO.setName(room.getName());
        roomDTO.setHouseId(room.getHouseId() == null ? null : room.getHouseId().toHexString());
        roomDTO.setDevices(ObjectIdUtils.convertListObjectIdToListString(room.getDevices()));
    }
}
