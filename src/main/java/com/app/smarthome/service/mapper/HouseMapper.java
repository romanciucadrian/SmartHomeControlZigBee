package com.app.smarthome.service.mapper;

import com.app.smarthome.dtos.HouseDTO;
import com.app.smarthome.models.House;
import com.app.smarthome.utils.ObjectIdUtils;

import java.util.ArrayList;
import java.util.List;

public class HouseMapper {

    public static List<HouseDTO> createDTOListFromEntityList(List<House> houses){
        List<HouseDTO> housesDTO = new ArrayList<>();

        for(House currentHouse : houses) {
            HouseDTO deviceTypeDTO = createDTOFromEntity(currentHouse);
            housesDTO.add(deviceTypeDTO);
        }
        return housesDTO;
    }

    public static HouseDTO createDTOFromEntity(House house){
        HouseDTO houseDTO = new HouseDTO();
        setHouseDTOBasedOnEntity(house, houseDTO);
        return houseDTO;
    }

    private static void setHouseDTOBasedOnEntity(House house, HouseDTO houseDTO) {
        houseDTO.setId(house.getId().toHexString());
        houseDTO.setName(house.getName());
        houseDTO.setRooms(ObjectIdUtils.convertListObjectIdToListString(house.getRooms()));
        houseDTO.setDevices(ObjectIdUtils.convertListObjectIdToListString(house.getDevices()));
    }
}
