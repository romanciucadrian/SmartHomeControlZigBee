package com.quest.global.SmartHome.test;


import com.quest.global.SmartHome.exceptions.HouseNotFoundException;
import com.quest.global.SmartHome.models.House;
import com.quest.global.SmartHome.models.Room;
import com.quest.global.SmartHome.repositories.HouseRepository;
import com.quest.global.SmartHome.repositories.RoomRepository;
import com.quest.global.SmartHome.services.RoomService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTestException {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private HouseRepository houseRepository;

    @InjectMocks
    private RoomService roomService;

    @Test
    public void testCreateRoomForAHouse_when_houseIsFound() throws HouseNotFoundException {

        //Given
        ObjectId houseId =
                new ObjectId();

        House foundHouse =
                new House();

        Room createdRoom =
                new Room();

        foundHouse.setId(houseId);

        given(houseRepository.findById(houseId))
                .willReturn(Optional.of(foundHouse));

        given(roomRepository.save(createdRoom))
                .willReturn(createdRoom);

        //When
        Room resultRoom =
                roomService.createRoomForAHouse(houseId, createdRoom);

        //Then
        assertThat(resultRoom)
                .isEqualTo(createdRoom);

        assertThat(foundHouse.getRooms().size())
                .isEqualTo(1);

        assertThat(foundHouse.getRooms().get(0))
                .isEqualTo(createdRoom.getId());

    }

    @Test
    public void testCreateRoomForAHouse_when_HouseIsNotFound() {

        //Given
        ObjectId houseId =
                new ObjectId();

        Room room =
                new Room();

        given(houseRepository.findById(houseId))
                .willReturn(Optional.empty());

        //When & Then
        assertThatThrownBy(() -> roomService.createRoomForAHouse(houseId, room))
                .isInstanceOf(HouseNotFoundException.class)
                .hasMessage("This House ID doesn't exist!");

        verify(roomRepository, never()).save(room);


    }
}
