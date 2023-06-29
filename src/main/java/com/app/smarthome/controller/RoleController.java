package com.app.smarthome.controller;

import com.app.smarthome.exceptions.DocumentAlreadyExistsException;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.service.RoleService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @DeleteMapping("/removeRole")
    public void removeRole(@RequestParam("name") String name) throws DocumentNotFoundException {
        roleService.removeRole(name);
    }

    @Async("threadPoolTaskExecutor")
    @PreAuthorize("hasAuthority('ROOT')")
    @PostMapping("/addRolesToUser")
    public void addRolesToUser(@RequestParam("email") String email,
                                                     @RequestBody List<String> roleNames)
            throws DocumentNotFoundException, DocumentAlreadyExistsException {
        roleService.addRolesToUser(email, roleNames);
    }
}
