package com.quest.global.SmartHome.test;


import com.quest.global.SmartHome.exceptions.HouseNotFoundException;
import com.quest.global.SmartHome.mapstruct.MapStructMapperImpl;
import com.quest.global.SmartHome.repositories.HouseRepository;
import com.quest.global.SmartHome.services.HouseService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

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

        ObjectId houseId =
                new ObjectId();

        given(houseRepository.findById(houseId))
                .willReturn(Optional.empty());

        given(mapper.houseToHouseDTO(null))
                .willReturn(null);

        assertThrows(HouseNotFoundException.class, () -> houseService.findHouseByID(houseId));
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
