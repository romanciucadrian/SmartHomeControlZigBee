package com.quest.global.SmartHome.services;

import com.quest.global.SmartHome.models.ERole;
import com.quest.global.SmartHome.models.Role;
import com.quest.global.SmartHome.repositories.RoleRepository;
import com.quest.global.SmartHome.services.impl.IRoleService;
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
