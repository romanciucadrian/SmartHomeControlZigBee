package com.app.smarthome.service.impl;

import com.app.smarthome.dtos.RegistrationDTO;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.models.Role;
import com.app.smarthome.models.User;
import com.app.smarthome.repositories.UserRepository;
import com.app.smarthome.service.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static com.app.smarthome.constants.AppConstants.ADMIN;

@CrossOrigin(origins = "*", maxAge = 3600)
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private MessageSource messageSource;

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) throws DocumentNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new DocumentNotFoundException(messageSource.getMessage("no.user.found",
                        null, Locale.getDefault())));
    }

    @Transactional
    @Override
    public void saveDeviceForUser(ObjectId deviceId, User user) {
        if (CollectionUtils.isEmpty(user.getDevices())) {
            user.setDevices(new ArrayList<>());
        }
        user.getDevices().add(deviceId);
        userRepository.save(user);
    }

    @Override
    public void removeDeviceFromUser(ObjectId deviceId, User user) {
        if (!CollectionUtils.isEmpty(user.getDevices())) {
            user.getDevices().remove(deviceId);
        }
        userRepository.save(user);
    }

    @Override
    public List<User> getUsersWithGivenRole(Role role) {
        return userRepository.findAllUsersWithRoleMemberOfRoles(role);
    }

    @Transactional
    @Override
    public void removeRoleFromUsers(Role role, List<User> userWithGivenRole) {
        userWithGivenRole.forEach(user ->
                user.setRoles(user.getRoles().stream().filter(r -> !r.getId().equals(role.getId())).collect(Collectors.toSet())));
        userRepository.saveAll(userWithGivenRole);
    }

    public User saveUserForRegistration(RegistrationDTO registrationDTO, ObjectId houseId,
                                        Set<Role> rootAndAdmin) {
        User user = new User();
        user.setUsername(registrationDTO.getEmail());
        user.setNormalizedUserName(registrationDTO.getEmail().toUpperCase());
        user.setEmail(registrationDTO.getEmail());
        user.setNormalizedEmail(registrationDTO.getEmail().toUpperCase());
        user.setPassword(encoder.encode(registrationDTO.getPassword()));
        user.setEmailConfirmed(false);
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setRoot(registrationDTO.getIsRoot());
        user.setHouses(List.of(houseId));
        user.setRoles(registrationDTO.getIsRoot() ? rootAndAdmin :
                rootAndAdmin.stream().filter(role -> role.getName().equals(ADMIN)).collect(Collectors.toSet()));
        user.setDevices(new ArrayList<>());

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public void removeUserByEmail(String email) throws DocumentNotFoundException {
        User user = getUserByEmail(email);
        userRepository.delete(user);
    }

    @Transactional
    @Override
    public void removeUserById(ObjectId userId) throws DocumentNotFoundException {
        User user = getUserById(userId);
        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsersByHouseId(ObjectId houseId) {
        return userRepository.findAllUsersWithHouseIdMemberOfHouses(houseId);
    }

    @Transactional
    @Override
    public void saveAllUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    @Override
    public User getUserById(ObjectId userId) throws DocumentNotFoundException {
        return userRepository.findById(userId).orElseThrow(() ->
                new DocumentNotFoundException(messageSource.getMessage("no.user.found",
                        null, Locale.getDefault())));
    }
}
