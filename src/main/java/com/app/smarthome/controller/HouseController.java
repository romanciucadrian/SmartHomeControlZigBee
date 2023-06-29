package com.app.smarthome.controller;

import com.app.smarthome.dtos.HouseDTO;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.service.HouseService;
import com.app.smarthome.utils.ObjectIdUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/houses")
public class HouseController {

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN')")
    @PostMapping("/createHouseForUser")
    public CompletableFuture<HouseDTO> createHouseForUser(@RequestParam("houseName") String houseName,
                                                          @RequestParam("email") String email)
            throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(houseService.createHouseForUser(houseName, email));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN')")
    @PutMapping("/updateHouse")
    public CompletableFuture<HouseDTO> updateHouse(@RequestParam("houseId") String houseId,
                                                   @RequestParam("newHouseName") String newHouseName,
                                                    @RequestParam("newDevices") List<String> newDevices,
                                                    @RequestParam("newRooms") List<String> newRooms)
            throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(
                houseService.updateHouse(ObjectIdUtils.convertStringToObjectId(houseId),
                        newHouseName,
                        ObjectIdUtils.convertListStringToListObjectId(newDevices),
                        ObjectIdUtils.convertListStringToListObjectId(newRooms)));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN')")
    @GetMapping("/housesByUserId")
    public CompletableFuture<List<HouseDTO>> getHousesByUserId(@RequestParam("userId") String userId)
            throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(
                houseService.getHousesByUserId(ObjectIdUtils.convertStringToObjectId(userId)));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN')")
    @GetMapping("/housesByEmail")
    public CompletableFuture<List<HouseDTO>> getHousesByEmail(@RequestParam("email") String email)
            throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(houseService.getHousesByEmail(email));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN')")
    @DeleteMapping("/removeHouseById")
    public void removeHouseById(@RequestParam("houseId") String houseId)
            throws DocumentNotFoundException {
         houseService.removeHouseById(ObjectIdUtils.convertStringToObjectId(houseId));
    }
}
