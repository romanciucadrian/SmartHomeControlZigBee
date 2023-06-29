package com.app.smarthome.service.impl;


import com.app.smarthome.exceptions.DocumentAlreadyExistsException;
import com.app.smarthome.exceptions.DocumentNotFoundException;
import com.app.smarthome.models.Role;
import com.app.smarthome.models.User;
import com.app.smarthome.repositories.RoleRepository;
import com.app.smarthome.service.RoleService;
import com.app.smarthome.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    @Override
    public Role getRoleByName(String name) throws DocumentNotFoundException {
        return roleRepository.findRoleByName(name).orElseThrow(() ->
                new DocumentNotFoundException(messageSource.getMessage("no.role.found", null,
                        Locale.getDefault())));
    }

    @Transactional
    @Override
    public void removeRole(String name) throws DocumentNotFoundException {
        Role role = roleRepository.findRoleByName(name).orElseThrow(() ->
                new DocumentNotFoundException(messageSource.getMessage("no.role.found",
                        null, Locale.getDefault())));
        List<User> userWithGivenRole = userService.getUsersWithGivenRole(role);
        userService.removeRoleFromUsers(role, userWithGivenRole);
        roleRepository.delete(role);
    }

    @Override
    public List<Role> findRoleByNameIn(List<String> root) {
        return roleRepository.findRoleByNameIn(root);
    }

    @Override
    public void addRolesToUser(String email, List<String> roleNames) throws DocumentNotFoundException,
            DocumentAlreadyExistsException {
        User user = userService.getUserByEmail(email);
        List<Role> rolesToBeAdded = new ArrayList<>();
        for (String roleName : roleNames) {
            Role role = getRoleByName(roleName);
            if (user.getRoles().contains(role)) {
                throw new DocumentAlreadyExistsException(messageSource.getMessage("user.already.has.role",
                        new String[] {user.getEmail(), role.getName()}, Locale.getDefault()));
            }
            rolesToBeAdded.add(role);
        }
        user.getRoles().addAll(rolesToBeAdded);
        userService.saveUser(user);
    }
}
