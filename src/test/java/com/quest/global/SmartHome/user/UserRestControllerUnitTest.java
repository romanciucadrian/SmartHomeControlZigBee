package com.quest.global.SmartHome.user;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quest.global.SmartHome.controllers.UserController;
import com.quest.global.SmartHome.dtos.HouseDTO;
import com.quest.global.SmartHome.dtos.UserViewDTO;
import com.quest.global.SmartHome.exceptions.UserNotFoundException;
import com.quest.global.SmartHome.mapstruct.MapStructMapperImpl;
import com.quest.global.SmartHome.models.ERole;
import com.quest.global.SmartHome.models.House;
import com.quest.global.SmartHome.models.Role;
import com.quest.global.SmartHome.models.User;
import com.quest.global.SmartHome.payload.request.SignupRequest;
import com.quest.global.SmartHome.services.RoleService;
import com.quest.global.SmartHome.services.UserService;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserRestControllerUnitTest {

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    private final MapStructMapperImpl mapper =
            new MapStructMapperImpl();

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    public void testSaveUser() throws Exception {

        // Given
        SignupRequest signupRequest =
                new SignupRequest();

        signupRequest.setUserName("testUser");
        signupRequest.setEmail("testUser@gmail.com");
        signupRequest.setFirstName("Test");
        signupRequest.setLastName("User");
        signupRequest.setIsRoot(true);
        signupRequest.setPassword("password");

        User savedUser = new User(
                signupRequest.getUserName(),
                signupRequest.getEmail(),
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getIsRoot(),
                signupRequest.getPassword()

        );

        Role adminRole = new Role(ERole.Admin.toString());
        Role rootRole = new Role(ERole.Root.toString());

        List<Role> expectedRoles =
                new ArrayList<>();

        expectedRoles.add(adminRole);
        expectedRoles.add(rootRole);

        savedUser.setRoles(expectedRoles);

        given(roleService.findRoleByName(ERole.Admin)).willReturn(adminRole);
        given(roleService.findRoleByName(ERole.Root)).willReturn(rootRole);
        given(userService.createUser(savedUser)).willReturn(savedUser);

        // When
      String responseBody =  mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk())
              .andReturn().getResponse().getContentAsString();

        // Then
        User actualUser =
                objectMapper.readValue(responseBody, User.class);

        assertThat(actualUser).isEqualTo(savedUser);

    }

    @Test
    public void testDeleteUserByID_Success() throws Exception {

        // Given
        ObjectId userId =
                new ObjectId("637cb085be36767eae89c26b");

        User deleteUser =
                new User();

        deleteUser.setId(userId);

        given(userService.deleteUserByID(userId)).willReturn("User deleted!");

        // When
        String responseBody = mockMvc.perform(delete("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("User deleted!"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Then
        assertThat(responseBody).isEqualTo(userService.deleteUserByID(userId));
    }

    @Test
    public void testDeleteUserByID_NotFoundID() throws Exception {

        // Given
        ObjectId userId =
                new ObjectId("637cb085be36767eae89c26b");

        String errorMsg = "Could not find that user !";

        given(userService.deleteUserByID(userId)).willThrow(new UserNotFoundException(errorMsg));

        // When
        String reponseBody = mockMvc.perform(delete("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(errorMsg))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        // Then
        assertThat(reponseBody).isEqualTo(errorMsg);

    }

    @Test
    public void testListAllUsers() throws Exception {

        // Given
        User user1 =
                new User("adrian@gmail.com", "adrian");
        User user2 =
                new User("bogdan@gmail.com", "bogdan");

        UserViewDTO userViewDTO1 =
                mapper.userToUserDTO(user1);

        UserViewDTO userViewDTO2 =
                mapper.userToUserDTO(user2);

        List<UserViewDTO> expectedList =
                new ArrayList<>();

        expectedList.add(userViewDTO1);
        expectedList.add(userViewDTO2);

        given(userService.listAll()).willReturn(expectedList);

        // When
        String responseBody = mockMvc.perform(get("/api/users/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Then
        TypeReference<List<UserViewDTO>> typeReference =
                new TypeReference<>() {};

        List<UserViewDTO> actualList =
                new ObjectMapper().readValue(responseBody, typeReference);

        assertThat(actualList).isEqualTo(expectedList);

    }

    @Test
    public void testGetUserByID_whenIdIsFound() throws Exception {

        // Given
        ObjectId userId =
                new ObjectId();

        User user =
                new User();

        user.setId(userId);

        UserViewDTO expectedUser =
                mapper.userToUserDTO(user);

        given(userService.findUserByID(userId)).willReturn(expectedUser);

        // When
        String responseBody =
                mockMvc.perform(get("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();

        // Then
        UserViewDTO expectedUserDTO =
                objectMapper.readValue(responseBody, UserViewDTO.class);

        assertThat(expectedUserDTO).isEqualTo(expectedUser);

    }

    @Test
    public void testGetUserByID_whenIDisNotFound() throws Exception {

        // Given
        ObjectId userId =
                new ObjectId();

        String errorMsg = "User not found!";

        given(userService.findUserByID(userId)).willThrow(new UserNotFoundException(errorMsg));

        // When
        String responseBody =
                mockMvc.perform(get("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("User not found!"))
                        .andExpect(status().isNotFound())
                        .andReturn().getResponse().getContentAsString();

        // Then
        assertThat(responseBody).isEqualTo(errorMsg);

    }

}
