package com.app.smarthome.service;

import com.app.smarthome.dtos.RegistrationDTO;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.models.Role;
import com.app.smarthome.models.User;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Set;

public interface UserService {

    Boolean existsByEmail(String email);

    User getUserByEmail(String email) throws DocumentNotFoundException;

    User getUserById(ObjectId userId) throws DocumentNotFoundException;

    void saveDeviceForUser(ObjectId deviceId, User user);

    void removeDeviceFromUser(ObjectId id, User user);

    List<User> getUsersWithGivenRole(Role role);

    void removeRoleFromUsers(Role role, List<User> userWithGivenRole);

    User saveUserForRegistration(RegistrationDTO registrationDTO, ObjectId houseId,
                                 Set<Role> rootAndAdmin);

    void saveUser(User user);

    Boolean emailExists(String email);

    void removeUserByEmail(String email) throws DocumentNotFoundException;

    void removeUserById(ObjectId objectId) throws DocumentNotFoundException;

    List<User> getAllUsersByHouseId(ObjectId houseId);

    void saveAllUsers(List<User> users);
}
