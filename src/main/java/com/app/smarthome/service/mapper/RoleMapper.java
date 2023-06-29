package com.app.smarthome.service.mapper;

import com.app.smarthome.dtos.RoleDTO;
import com.app.smarthome.models.Role;

import java.util.HashSet;
import java.util.Set;

public class RoleMapper {

    public static Set<RoleDTO> createDTOSetFromEntitySet(Set<Role> roles){
        Set<RoleDTO> roleDTOS = new HashSet<>();

        for(Role currentRole : roles) {
            RoleDTO roleDTO = createDTOFromEntity(currentRole);
            roleDTOS.add(roleDTO);
        }
        return roleDTOS;
    }

    public static RoleDTO createDTOFromEntity(Role role){
        RoleDTO roleDTO = new RoleDTO();
        setUserDTOBasedOnEntity(role, roleDTO);
        return roleDTO;
    }

    private static void setUserDTOBasedOnEntity(Role role, RoleDTO roleDTO) {
        roleDTO.setId(role.getId().toHexString());
        roleDTO.setName(role.getName());
        roleDTO.setNormalizedName(role.getNormalizedName());
    }
}
