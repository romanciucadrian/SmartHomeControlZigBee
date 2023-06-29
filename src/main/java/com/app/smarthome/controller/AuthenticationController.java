package com.app.smarthome.controller;

import com.app.smarthome.dtos.JwtResponseDTO;
import com.app.smarthome.dtos.LoginDTO;
import com.app.smarthome.dtos.RegistrationDTO;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.service.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Async("threadPoolTaskExecutor")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationDTO registrationDTO)
            throws Exception {

        authenticationService.register(registrationDTO);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(registrationDTO);
    }

    @Async("threadPoolTaskExecutor")
    @PostMapping("/login")
    public CompletableFuture<JwtResponseDTO> login(@RequestBody LoginDTO loginDTO) throws DocumentNotFoundException {
            return CompletableFuture.completedFuture(authenticationService.login(loginDTO));
    }

    @Async("threadPoolTaskExecutor")
    @GetMapping("/currentUser")
    public CompletableFuture<String> getCurrentUser() {
        return CompletableFuture.completedFuture(authenticationService.getCurrentUser());
    }
}
