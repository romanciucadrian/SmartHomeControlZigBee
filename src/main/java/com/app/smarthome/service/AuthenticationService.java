package com.app.smarthome.service;

import com.app.smarthome.dtos.JwtResponseDTO;
import com.app.smarthome.dtos.LoginDTO;
import com.app.smarthome.dtos.RegistrationDTO;
import com.app.smarthome.exceptions.DocumentAlreadyExistsException;
import com.app.smarthome.exceptions.DocumentNotFoundException;

public interface AuthenticationService {

    String register(RegistrationDTO registrationDTO) throws DocumentAlreadyExistsException;
    JwtResponseDTO login(LoginDTO loginDTO) throws DocumentNotFoundException;
    String getCurrentUser();
}
