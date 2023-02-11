package com.app.smarthome.controllers;


import com.app.smarthome.models.Role;
import com.app.smarthome.models.User;
import com.app.smarthome.services.RoleService;
import com.app.smarthome.dtos.UserViewDTO;
import com.app.smarthome.exceptions.UserNotFoundException;
import com.app.smarthome.models.ERole;
import com.app.smarthome.payload.request.SignupRequest;
import com.app.smarthome.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {

        List<UserViewDTO> users = userService.listAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody SignupRequest signupRequest) {

        User user = new User(
                signupRequest.getUserName(),
                signupRequest.getEmail(),
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getIsRoot(),
                signupRequest.getPassword()
        );

        List<Role> roles =
                new ArrayList<>();

        roles.add(roleService.findRoleByName(ERole.Admin));

        if (user.getIsRoot()) {
            roles.add(roleService.findRoleByName(ERole.Root));
        }

        user.setRoles(roles);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.createUser(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId id) {

        try {
            return new ResponseEntity<>(userService.deleteUserByID(id),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id) {

        try {

            return new ResponseEntity<>(userService.findUserByID(id),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }

    }

}
