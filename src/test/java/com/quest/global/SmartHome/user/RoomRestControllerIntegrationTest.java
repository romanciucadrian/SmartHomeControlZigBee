package com.quest.global.SmartHome.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quest.global.SmartHome.controllers.RoomController;
import com.quest.global.SmartHome.exceptions.HouseNotFoundException;
import com.quest.global.SmartHome.models.House;
import com.quest.global.SmartHome.models.Room;
import com.quest.global.SmartHome.repositories.HouseRepository;
import com.quest.global.SmartHome.services.HouseService;
import com.quest.global.SmartHome.services.RoomService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.data.mongodb.util.BsonUtils.toJson;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RoomRestControllerIntegrationTest {

    @Mock
    private RoomService roomService;

    @Mock
    private HouseRepository houseRepository;

    @InjectMocks
    private RoomController roomController;

    private ObjectMapper objectMapper
            = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(roomController)
                .build();
    }

    @Test
    public void testSaveRoom_whenHouse_isFound() throws Exception {

        // Given
        ObjectId houseID = new ObjectId();

        House house = new House(houseID, "HouseRoom",
                new ArrayList<>(), new ArrayList<>());

        Room room = new Room(new ObjectId(), houseID, "ExpectedRoom");

        String roomJson = objectMapper.writeValueAsString(room);

        given(roomService.createRoomForAHouse(any(ObjectId.class), any(Room.class)))
                .willReturn(room);
        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/rooms/" + houseID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(roomJson)
        );

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void saveRoomTestWithHouseNotFoundException() throws Exception {
        ObjectId houseId = new ObjectId();
        Room room = new Room();

        given(roomService.createRoomForAHouse(any(), any())).willThrow(new HouseNotFoundException("House not found"));

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(room);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/rooms/" + houseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        );

    }
}
