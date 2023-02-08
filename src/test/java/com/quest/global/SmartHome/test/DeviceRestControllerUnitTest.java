package com.quest.global.SmartHome.test;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quest.global.SmartHome.controllers.DeviceController;
import com.quest.global.SmartHome.exceptions.DeviceNotFoundException;
import com.quest.global.SmartHome.models.Device;
import com.quest.global.SmartHome.services.DeviceService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class DeviceRestControllerUnitTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceController deviceController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(deviceController)
                .build();
    }

    @Test
    public void testDeleteDeviceById_whenNameIsFound() throws Exception {

        // Given
        String deviceName = "Device";
        String deleteMsg = "Device successfully deleted!";

        given(deviceService.deleteDeviceByName(deviceName)).willReturn(deleteMsg);

        // When
        String responseBody =
                mockMvc.perform(delete("/api/devices/" + deviceName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deleteMsg))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();
        // Then
        assertThat(responseBody).isEqualTo(deleteMsg);

    }

    @Test
    public void testDeleteDeviceById_WhenNameIsNotFound() throws Exception {

        // Given
        String deviceName =
                "deletedDevice";

        String errorMsg =
                "Name not found!";

        given(deviceService.deleteDeviceByName(deviceName)).willThrow(new DeviceNotFoundException(errorMsg));

        // When
        String responseBody =
                mockMvc.perform(delete("/api/devices/" + deviceName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(errorMsg))
                        .andExpect(status().isNotFound())
                        .andReturn().getResponse().getContentAsString();

        // Then
        assertThat(responseBody).isEqualTo(responseBody);

    }

    @Test
    public void testUpdateDeviceName_WhenNameIsFound() throws Exception {

        // Given
        String deviceName1 =
                "OldName";

        String deviceNewName =
                "NewName";

        Device device = new Device();

        device.setDeviceName(deviceNewName);

        given(deviceService.updateDeviceByName(deviceName1, deviceNewName)).willReturn(device);

        // When
           ResultActions resultActions =
                   mockMvc.perform(put("/api/devices/")
                        .param("deviceName", deviceName1)
                        .param("deviceNewName", deviceNewName)
                        .contentType(MediaType.APPLICATION_JSON));


        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceName", is(deviceNewName)));
    }

    @Test
    public void testUpdateDeviceName_WhenNameIsNotFound() throws Exception {

        // Given
        String deviceName =
                "deviceName";

        String deviceNewName =
                "deviceNewName";

        String errorMsg =
                "Device not found!";

        Device device =
                new Device();

        device.setDeviceName(deviceNewName);

        given(deviceService.updateDeviceByName(deviceName, deviceNewName)).willThrow(new DeviceNotFoundException(errorMsg));

        // When
        ResultActions resultActions =
                mockMvc.perform(put("/api/devices/")
                        .param("deviceName", deviceName)
                        .param("deviceNewName", deviceNewName)
                        .contentType(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindAllDevices() throws Exception {

        // Given
        List<Device> expectedList =
                new ArrayList<>();

        Device device1 = new Device();
        Device device2 = new Device();

        expectedList.add(device1);
        expectedList.add(device2);

        given(deviceService.findAll()).willReturn(expectedList);

        // When
        String responseBody =
                mockMvc.perform(get("/api/devices/")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();
        // Then
        TypeReference<List<Device>> typeReference =
                new TypeReference<>() {};

        List<Device> actualList =
                objectMapper.readValue(responseBody, typeReference);

        assertThat(actualList).isEqualTo(expectedList);
    }


}
