package com.app.smarthome.service.impl;

import com.app.smarthome.dtos.JwtResponseDTO;
import com.app.smarthome.dtos.LoginDTO;
import com.app.smarthome.dtos.RegistrationDTO;
import com.app.smarthome.exceptions.DocumentAlreadyExistsException;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.models.House;
import com.app.smarthome.models.Role;
import com.app.smarthome.models.User;
import com.app.smarthome.security.details.SmartHomeUserDetails;
import com.app.smarthome.security.jwt.JwtUtils;
import com.app.smarthome.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.app.smarthome.constants.AppConstants.ADMIN;
import static com.app.smarthome.constants.AppConstants.ROOT;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final MessageSource messageSource;
    private final UserService userService;
    private final RoleService roleService;
    private final HouseService houseService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public String register(RegistrationDTO registrationDTO) throws DocumentAlreadyExistsException {
        Boolean existsByEmail = userService.existsByEmail(registrationDTO.getEmail());
        if (existsByEmail) {
            throw new DocumentAlreadyExistsException(messageSource.getMessage("email.already.in.use",
                    null, Locale.getDefault()));
        }

        House house = houseService.createDefaultHouseWithDefaultRooms();
        List<Role> rootAndAdmin = roleService.findRoleByNameIn(List.of(ROOT, ADMIN));
        User user = userService.saveUserForRegistration(registrationDTO, house.getId(), new HashSet<>(rootAndAdmin));
        return messageSource.getMessage("user.created.successfully",
                new String[] {user.getEmail()}, Locale.getDefault());
    }
    @Override
    public JwtResponseDTO login(LoginDTO loginDTO) throws DocumentNotFoundException {
        Boolean existsByEmail = userService.existsByEmail(loginDTO.getEmail());
        if (!existsByEmail) {
            throw new DocumentNotFoundException(messageSource.getMessage("no.user.for.email",
                    null, Locale.getDefault()));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        SmartHomeUserDetails smartHomeUserDetails = (SmartHomeUserDetails) authentication.getPrincipal();
        List<String> roles = smartHomeUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponseDTO(
                jwt,
                smartHomeUserDetails.getId(),
                smartHomeUserDetails.getUsername(),
                smartHomeUserDetails.getEmail(),
                roles
        );
    }
    @Override
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
