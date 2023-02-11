package com.app.smarthome.services;

import com.app.smarthome.dtos.HouseDTO;
import com.app.smarthome.exceptions.HouseNotFoundException;
import com.app.smarthome.mapstruct.MapStructMapperImpl;
import com.app.smarthome.models.House;
import com.app.smarthome.models.Room;
import com.app.smarthome.repositories.HouseRepository;
import com.app.smarthome.repositories.RoomRepository;
import com.app.smarthome.services.impl.IHouseService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseService implements IHouseService {

    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;

    private final MapStructMapperImpl mapStructMapper;

    public HouseService(HouseRepository houseRepository, RoomRepository roomRepository, MapStructMapperImpl mapStructMapper) {
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.mapStructMapper = mapStructMapper;
    }

    @Transactional
    public HouseDTO findHouseByID(ObjectId id) throws HouseNotFoundException {

        HouseDTO houseDTO =
                mapStructMapper.houseToHouseDTO(houseRepository.findById(id).orElse(null));

        if (houseDTO != null) {
            return houseDTO;
        } else {
            throw new HouseNotFoundException("Can't find this House ID " + id);
        }
    }

    @Transactional
    public HouseDTO findHouseByName(String houseName) throws HouseNotFoundException {

        HouseDTO houseDTO =
                mapStructMapper.houseToHouseDTO(houseRepository.findByName(houseName));

        if (houseDTO != null) {
            return houseDTO;
        } else {
            throw new HouseNotFoundException("Can't find this House Name " + houseName);
        }
    }

    @Transactional
    public House createHouse(House house) {

       houseRepository.save(house);

       List<Room> listRoomsForHouse
               = createRoomsForHouse(house);

       List<ObjectId> listRoomsIds
               = listRoomsForHouse
                    .stream()
                    .map(Room::getId)
                    .collect(Collectors.toList());

       house.setRooms(listRoomsIds);

       return houseRepository.save(house);
    }

    @Transactional
    public House updateHouseName(String houseName, String houseNewName) throws HouseNotFoundException {

        House house =
                    houseRepository.findByName(houseName);

        if (house != null) {

            house.setName(houseNewName);

        } else {
                throw new HouseNotFoundException("This house name doesn't exist!");
            }

        return houseRepository.save(house);
    }

    @Transactional
    public List<House> listAll() {
        return houseRepository.findAll();
    }

    public List<Room> createRoomsForHouse(House house) {

        List<Room> roomList = new ArrayList<>();

        Room roomKitchen =
                new Room(house.getId(), "Kitchen");
        roomList.add(roomKitchen);

        Room roomLivingRoom =
                new Room(house.getId(), "LivingRoom");
        roomList.add(roomLivingRoom);

        Room roomBathRoom =
                new Room(house.getId(), "BathRoom");
        roomList.add(roomBathRoom);

        return roomRepository.saveAll(roomList);
    }

}
