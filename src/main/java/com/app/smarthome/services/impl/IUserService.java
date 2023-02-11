package com.app.smarthome.services.impl;

import com.app.smarthome.models.User;
import com.app.smarthome.dtos.UserViewDTO;
import com.app.smarthome.exceptions.UserNotFoundException;
import org.bson.types.ObjectId;

import java.util.List;

public interface IUserService {

    List<UserViewDTO> listAll();

    User createUser(User user);

    UserViewDTO findUserByID(ObjectId id) throws UserNotFoundException;

    String deleteUserByID(ObjectId id) throws UserNotFoundException;
}
