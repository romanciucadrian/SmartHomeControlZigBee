package com.app.smarthome.controller;

import com.app.smarthome.dtos.RoomDTO;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.service.HouseService;
import com.app.smarthome.service.RoomService;
import com.app.smarthome.utils.ObjectIdUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final HouseService houseService;

    public RoomController(RoomService roomService, HouseService houseService) {
        this.roomService = roomService;
        this.houseService = houseService;
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN')")
    @PostMapping("/createRoomForHouse")
    public CompletableFuture<RoomDTO> createRoomForHouse(@RequestParam("houseId") String houseId,
                                                         @RequestParam("roomName") String roomName)
            throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(
                houseService.createRoomForHouse(ObjectIdUtils.convertStringToObjectId(houseId), roomName));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN')")
    @PutMapping("/updateRoom")
    public CompletableFuture<RoomDTO> updateRoom(@RequestParam("roomId") String roomId,
                                                             @RequestParam("newName") String newName)
            throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(roomService.updateRoom(
                ObjectIdUtils.convertStringToObjectId(roomId), newName));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN')")
    @DeleteMapping("/removeRoom")
    public void removeRoom(@RequestParam("roomId") String roomId) throws DocumentNotFoundException {
        houseService.removeRoomFromHouse(ObjectIdUtils.convertStringToObjectId(roomId));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN')")
    @GetMapping("/roomById/{roomId}")
    public CompletableFuture<RoomDTO> getRoomById(@PathVariable String roomId) throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(
                roomService.getRoomDTOById(ObjectIdUtils.convertStringToObjectId(roomId)));
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT') or hasAuthority('ADMIN')")
    @GetMapping("/allRoomsByHouseId")
    public CompletableFuture<List<RoomDTO>> getAllRoomsByHouseId(@RequestParam("houseId") String houseId)
            throws DocumentNotFoundException {
        return CompletableFuture.completedFuture(
                houseService.getAllRoomsByHouseId(ObjectIdUtils.convertStringToObjectId(houseId)));
    }
}
