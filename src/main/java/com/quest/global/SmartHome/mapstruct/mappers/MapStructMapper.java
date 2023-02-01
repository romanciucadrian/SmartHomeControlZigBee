package com.quest.global.SmartHome.mapstruct.mappers;

import com.quest.global.SmartHome.dtos.*;
import com.quest.global.SmartHome.dtos.mqtt.MqttPublish;
import com.quest.global.SmartHome.models.*;
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
