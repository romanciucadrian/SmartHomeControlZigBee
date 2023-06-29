package com.app.smarthome.service;

import com.app.smarthome.dtos.RoleDTO;
import com.app.smarthome.exceptions.DocumentAlreadyExistsException;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.exceptions.InvalidArgumentException;
import com.app.smarthome.models.Role;

import java.util.List;

public interface RoleService {

    void removeRole(String name) throws DocumentNotFoundException;

    List<Role> findRoleByNameIn(List<String> rootAndAdmin);

    void addRolesToUser(String email, List<String> roleNames) throws DocumentNotFoundException, DocumentAlreadyExistsException;

    Role getRoleByName(String name) throws DocumentNotFoundException;
}
