package com.quest.global.SmartHome.test;


import com.quest.global.SmartHome.dtos.HouseDTO;
import com.quest.global.SmartHome.exceptions.HouseNotFoundException;
import com.quest.global.SmartHome.mapstruct.MapStructMapperImpl;
import com.quest.global.SmartHome.models.House;
import com.quest.global.SmartHome.repositories.HouseRepository;
import com.quest.global.SmartHome.services.HouseService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class HouseServiceExceptionTest {

    @Mock
    private HouseRepository houseRepository;

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

        given(mapper.houseToHouseDTO(null))
                .willReturn(null);

        //Then
        assertThrows(HouseNotFoundException.class, () -> houseService.findHouseByID(houseId));

        verify(houseRepository).findById(houseId);
    }

    @Test
    public void findHouseByID_HouseFound_ShouldNotThrowException() {

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

        Optional<House> result =
                houseRepository.findById(id);

        HouseDTO resultDTO =
                mapper.houseToHouseDTO(result.get());

        //Then
        verify(houseRepository).findById(id);
        verify(mapper).houseToHouseDTO(findHouse);

        assertThat(houseDTO).isEqualTo(resultDTO);

    }

    @Test
    public void findHouseByName_HouseNotFound_ShouldThrowException() {

        String houseName =
                "houseName";

        given(houseRepository.findByName(houseName))
                .willReturn(null);

        given(mapper.houseToHouseDTO(null))
                .willReturn(null);

        assertThrows(HouseNotFoundException.class, () -> houseService.findHouseByName(houseName));
    }

    @Test
    public void updateHouseName_HouseNotFound_ShouldThrowException() {

        // Given
        String houseName =
                "houseName";

        // When
        given(houseRepository.findByName(houseName))
                .willReturn(null);

        given(mapper.houseToHouseDTO(null))
                .willReturn(null);

        // Then
        assertThrows(HouseNotFoundException.class, () -> houseService.findHouseByName(houseName));
    }



}
