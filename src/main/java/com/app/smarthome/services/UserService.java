package com.app.smarthome.services;

import com.app.smarthome.mapstruct.mappers.MapStructMapper;
import com.app.smarthome.models.User;
import com.app.smarthome.dtos.UserViewDTO;
import com.app.smarthome.exceptions.UserNotFoundException;
import com.app.smarthome.repositories.UserRepository;
import com.app.smarthome.services.impl.IUserService;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MapStructMapper mapStructMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, MapStructMapper mapStructMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapStructMapper = mapStructMapper;
    }

    @Transactional
    public List<UserViewDTO> listAll() {

       List<User> users
               = userRepository.findAll();

       return mapStructMapper.usersToUsersDTOs(users);
    }

    @Transactional
    public List<User> getAll() {

        return (List<User>) userRepository.findAll();
    }

    @Transactional
    public User createUser(User user) {
        encodePassword(user);
       return userRepository.save(user);
    }

    @Transactional
    public UserViewDTO findUserByID(ObjectId id) throws UserNotFoundException {

            UserViewDTO userViewDTO =
                    mapStructMapper.userToUserDTO(userRepository.findById(id).get());

            if (userViewDTO == null) {
                throw new UserNotFoundException("Could not find that user!");
            } else {
                return userViewDTO;
            }
    }

    @Transactional
    public String deleteUserByID(ObjectId id) throws UserNotFoundException {

        User user
                = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw  new UserNotFoundException("Could not find that user !");
        } else {
            userRepository.delete(user);
            return "User deleted!";
        }

    }

    private void encodePassword(User user) {

        String encodedPassword =
                passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
    }
}
