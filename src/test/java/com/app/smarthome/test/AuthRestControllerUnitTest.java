package com.app.smarthome.test;


import com.app.smarthome.controllers.AuthRestController;
import com.app.smarthome.models.ERole;
import com.app.smarthome.models.Role;
import com.app.smarthome.payload.request.LoginRequest;
import com.app.smarthome.payload.response.UserInfoResponse;
import com.app.smarthome.security.details.SmartHomeUserDetails;
import com.app.smarthome.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AuthRestControllerUnitTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @InjectMocks
    private AuthRestController authRestController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setup() {

        mockMvc =
                MockMvcBuilders.standaloneSetup(authRestController).build();
    }

    @Test
    public void loginUser_ValidRequest_ReturnsExpectedResponse() throws Exception {

        // Given
        String username =
                "user@example.com";

        String password =
                "password";

        String jwtToken =
                "dummy-jwt";

        Role role =
                new Role();

        role.setName(ERole.Admin.toString());

        List<String> roles = new ArrayList<>();
        roles.add(role.getName());

        LoginRequest loginRequest =
                new LoginRequest();

        List<GrantedAuthority> authorities =
                new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.getName()));

        SmartHomeUserDetails userDetails = new SmartHomeUserDetails(
                new ObjectId(),
                username,
                username,
                password,
                authorities
        );

        loginRequest.setEmail(username);
        loginRequest.setPassword(password);

        UserInfoResponse userInfoResponse =
                new UserInfoResponse(
                        jwtToken,
                        userDetails.getId(),
                        username,
                        username,
                        roles
                );

        // When
        given(jwtUtils.generateTokenFromUsername(username))
                .willReturn(jwtToken);

        given(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())))
                .willReturn(authentication);

        given(authentication.getPrincipal())
                .willReturn(userDetails);


        // Then
        String actualResponse = mockMvc.perform(post("/api/login/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        String expectedResponse =
                objectMapper.writeValueAsString(userInfoResponse);

        assertThat(actualResponse).isEqualTo(expectedResponse);

    }



}
