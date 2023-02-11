package com.app.smarthome.controllers;

import com.app.smarthome.security.details.SmartHomeUserDetails;
import com.app.smarthome.payload.request.LoginRequest;
import com.app.smarthome.payload.response.UserInfoResponse;
import com.app.smarthome.security.jwt.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/login")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public AuthRestController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication =
                authenticationManager.authenticate
                        (
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword())
                        );

        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);

        SmartHomeUserDetails userDetails =
                (SmartHomeUserDetails) authentication.getPrincipal();

        List<String> roles =
                userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String jwt =
                jwtUtils.generateTokenFromUsername(userDetails.getUsername());

        return ResponseEntity.ok(new UserInfoResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
                ));
    }
}
