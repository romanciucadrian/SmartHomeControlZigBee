package com.app.smarthome.controller;

import com.app.smarthome.dtos.UserDTO;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.service.HouseService;
import com.app.smarthome.service.UserService;
import com.app.smarthome.utils.ObjectIdUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final HouseService houseService;

    public UserController(UserService userService, HouseService houseService) {
        this.userService = userService;
        this.houseService = houseService;
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @GetMapping("/emailExists")
    public CompletableFuture<Boolean> emailExists(@RequestParam("email") String email) {
        return CompletableFuture.completedFuture(userService.emailExists(email));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @DeleteMapping("/removeUserByEmail")
    public void removeUserByEmail(@RequestParam("email") String email)
            throws DocumentNotFoundException {
        userService.removeUserByEmail(email);
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @DeleteMapping("/removeUser/{userId}")
    public void removeUserById(@PathVariable String userId) throws DocumentNotFoundException {
        userService.removeUserById(ObjectIdUtils.convertStringToObjectId(userId));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @GetMapping("/usersByHouseId")
    public CompletableFuture<List<UserDTO>> getUsersByHouseId(@RequestParam("houseId") String houseId)
            throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(
                houseService.getAllUsersDTOByHouseId(ObjectIdUtils.convertStringToObjectId(houseId)));
    }
}
