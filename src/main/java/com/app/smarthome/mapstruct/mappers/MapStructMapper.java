package com.app.smarthome.mapstruct.mappers;

import com.app.smarthome.dtos.*;
import com.app.smarthome.models.*;
import com.app.smarthome.dtos.mqtt.MqttPublish;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    HouseDTO houseToHouseDTO(House house);

    House houseDtoToHouse(HouseDTO houseDTO);

    List<HouseDTO> housesToHouseDTOs(List<House> houses);

    RoomDTO roomToRoomDTO(Room room);

    Room roomDtoToRoom(RoomDTO roomDTO);

    UserViewDTO userToUserDTO(User user);

    List<UserViewDTO> usersToUsersDTOs(List<User> users);

    MqttPublish commandToMqttPublish(Command command);

    DeviceTypeDTO deviceTypeToDeviceTypeDTO(DeviceType deviceType);

    DeviceDTO deviceToDeviceDTO(Device device);

    CommandDTO commandToCommandDTO(Command command);

}
