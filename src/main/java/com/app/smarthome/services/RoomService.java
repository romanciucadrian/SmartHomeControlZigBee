package com.app.smarthome.services;

import com.app.smarthome.services.impl.IRoomService;
import com.app.smarthome.exceptions.HouseNotFoundException;
import com.app.smarthome.models.House;
import com.app.smarthome.models.Room;
import com.app.smarthome.repositories.HouseRepository;
import com.app.smarthome.repositories.RoomRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;

    private final HouseRepository houseRepository;

    public RoomService(RoomRepository roomRepository, HouseRepository houseRepository) {
        this.roomRepository = roomRepository;
        this.houseRepository = houseRepository;
    }

    @Transactional
    public Room createRoomForAHouse(ObjectId houseID, Room room) throws HouseNotFoundException {

            House house =
                    houseRepository.findById(houseID).orElse(null);

            if (house != null) {

                List<ObjectId> newRoomListFromHouse =
                        house.getRooms();

                room.setHouseId(house.getId());
                newRoomListFromHouse.add(room.getId());

                house.setRooms(newRoomListFromHouse);
                houseRepository.save(house);

            } else {
            throw new HouseNotFoundException("This House ID doesn't exist!");
        }
        return roomRepository.save(room);
    }

}
