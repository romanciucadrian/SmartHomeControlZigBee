package com.app.smarthome.test;


import com.app.smarthome.dtos.HouseDTO;
import com.app.smarthome.exceptions.HouseNotFoundException;
import com.app.smarthome.mapstruct.MapStructMapperImpl;
import com.app.smarthome.models.House;
import com.app.smarthome.models.Room;
import com.app.smarthome.repositories.HouseRepository;
import com.app.smarthome.repositories.RoomRepository;
import com.app.smarthome.services.HouseService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HouseServiceExceptionTest {

    @Mock
    private HouseRepository houseRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private MapStructMapperImpl mapper;

    @InjectMocks
    private HouseService houseService;

    @Test
    public void findHouseByID_HouseNotFound_ShouldThrowException() {

        //Given
        ObjectId houseId =
                new ObjectId();

        //When
        given(houseRepository.findById(houseId))
                .willReturn(Optional.empty());

        //When & Then
        assertThatThrownBy(() -> houseService.findHouseByID(houseId))
                .isInstanceOf(HouseNotFoundException.class)
                .hasMessage("Can't find this House ID " + houseId);

    }

    @Test
    public void findHouseByID_HouseFound_ShouldNotThrowException() throws HouseNotFoundException {

        //Given
        ObjectId id =
                new ObjectId();

        House findHouse =
               new House();

        HouseDTO houseDTO =
                new HouseDTO();

        findHouse.setId(id);

        //When
        given(houseRepository.findById(id))
                .willReturn(Optional.of(findHouse));

        given(mapper.houseToHouseDTO(findHouse))
                .willReturn(houseDTO);

        HouseDTO resultDTO =
                houseService.findHouseByID(id);

        //Then
        verify(houseRepository).findById(id);
        verify(mapper).houseToHouseDTO(findHouse);

        assertThat(houseDTO).isEqualTo(resultDTO);

    }

    @Test
    public void findHouseByName_HouseNotFound_ShouldThrowException() {

        //Given
        String houseName =
                "houseName";

        given(houseRepository.findByName(houseName))
                .willReturn(null);

        //When & Then
        assertThatThrownBy(() -> houseService.findHouseByName(houseName))
                .isInstanceOf(HouseNotFoundException.class)
                .hasMessage("Can't find this House Name " + houseName);
    }

    @Test
    public void findHouseByName_HouseIsFound_ShouldNotThrowException() throws HouseNotFoundException {

        // Given
        String houseName =
                "houseName";

        House house =
                new House();

        HouseDTO houseDTO =
                new HouseDTO();

        house.setName(houseName);

        given(houseRepository.findByName(houseName))
                .willReturn(house);

        given(mapper.houseToHouseDTO(house))
                .willReturn(houseDTO);

        //When
        HouseDTO houseDTOResult = houseService.findHouseByName(houseName);

        // Then
        assertThat(houseDTOResult)
                .isEqualTo(houseDTO);
    }

    @Test
    public void updateHouseByName_HouseIsFound_ShouldNotThrowException() throws HouseNotFoundException {

        //Given
        String houseName =
                "houseName";

        String houseNewName =
                "houseNewName";

        House foundHouse =
                new House();

        given(houseRepository.findByName(houseName))
                .willReturn(foundHouse);

        given(houseRepository.save(foundHouse))
                .willReturn(foundHouse);

        //When
        House result =
                houseService.updateHouseName(houseName, houseNewName);

        //Then
        verify(houseRepository).save(foundHouse);
        verify(houseRepository).findByName(houseName);

        assertThat(result.getName())
                .isEqualTo(foundHouse.getName());
    }

    @Test
    public void updateHouseByName_HouseIsNotFound_ShouldThrowException() {

        //Given
        String houseName =
                "houseName";

        String houseNewName =
                "houseNewName";

        House house =
                new House();

        given(houseRepository.findByName(houseName))
                .willReturn(null);

        //When & Then
        assertThatThrownBy(() -> houseService.updateHouseName(houseName, houseNewName))
                .isInstanceOf(HouseNotFoundException.class)
                .hasMessage("This house name doesn't exist!");

        verify(houseRepository, never()).save(house);
    }

    @Test
    public void testListAllHouses() {

        //Given
        House house1 =
                new House();

        House house2 =
                new House();

        List<House> houseList =
                new ArrayList<>();

        houseList.add(house1);
        houseList.add(house2);

        given(houseRepository.findAll()).willReturn(houseList);

        //When
        List<House> actualListHouse =
                houseService.listAll();

        //Then
        assertThat(houseList).isEqualTo(actualListHouse);
    }

    @Test
    public void testCreateRoomsForAHouse() {

        //Given
        ObjectId houseId =
                new ObjectId();

        House house =
                new House();

        house.setId(houseId);

        ObjectId roomId =
                new ObjectId();

        Room room1 =
                new Room(roomId, house.getId(), "Kitchen");

        Room room2 =
                new Room(roomId, house.getId(), "BathRoom");

        Room room3 =
                new Room(roomId, house.getId(), "Bedroom");

        List<Room> roomList =
                Arrays.asList(room1, room2, room3);

        given(roomRepository.saveAll(anyList()))
                .willReturn(roomList);

        //When
        List<Room> actualRoomList =
                houseService.createRoomsForHouse(house);

        //Then
        assertThat(actualRoomList).isEqualTo(roomList);
    }



}
