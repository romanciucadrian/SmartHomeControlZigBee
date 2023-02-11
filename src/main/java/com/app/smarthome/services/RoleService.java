package com.app.smarthome.services;

import com.app.smarthome.models.Role;
import com.app.smarthome.models.ERole;
import com.app.smarthome.repositories.RoleRepository;
import com.app.smarthome.services.impl.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    @Autowired
    public RoleRepository roleRepository;


    public Role findRoleByName(ERole name) {
        return roleRepository.findByName(name).orElse(null);
    }
}
