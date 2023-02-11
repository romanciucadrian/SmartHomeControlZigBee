package com.quest.global.SmartHome.services;

import com.quest.global.SmartHome.exceptions.HouseNotFoundException;
import com.quest.global.SmartHome.models.House;
import com.quest.global.SmartHome.models.Room;
import com.quest.global.SmartHome.repositories.HouseRepository;
import com.quest.global.SmartHome.repositories.RoomRepository;
import com.quest.global.SmartHome.services.impl.IRoomService;
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
