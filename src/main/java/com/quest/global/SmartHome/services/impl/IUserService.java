package com.quest.global.SmartHome.services.impl;

import com.quest.global.SmartHome.dtos.UserViewDTO;
import com.quest.global.SmartHome.exceptions.UserNotFoundException;
import com.quest.global.SmartHome.models.User;
import org.bson.types.ObjectId;

import java.util.List;

public interface IUserService {

    List<UserViewDTO> listAll();

    User createUser(User user);

    UserViewDTO findUserByID(ObjectId id) throws UserNotFoundException;

    String deleteUserByID(ObjectId id) throws UserNotFoundException;
}
