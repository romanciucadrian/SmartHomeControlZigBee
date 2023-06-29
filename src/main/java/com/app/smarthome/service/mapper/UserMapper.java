package com.app.smarthome.service.mapper;

import com.app.smarthome.dtos.UserDTO;
import com.app.smarthome.models.User;
import com.app.smarthome.utils.ObjectIdUtils;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static List<UserDTO> createDTOListFromEntityList(List<User> users) {
        List<UserDTO> usersDTO = new ArrayList<>();

        for(User currentUser : users) {
            UserDTO userDTO = createDTOFromEntity(currentUser);
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }

    public static UserDTO createDTOFromEntity(User user){
        UserDTO userDTO = new UserDTO();
        setUserDTOBasedOnEntity(user, userDTO);
        return userDTO;
    }
    private static void setUserDTOBasedOnEntity(User user, UserDTO userDTO) {
        userDTO.setId(user.getId().toHexString());
        userDTO.setUsername(user.getUsername());
        userDTO.setNormalizedUserName(user.getNormalizedUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setNormalizedEmail(user.getNormalizedEmail());
        userDTO.setEmailConfirmed(user.getEmailConfirmed());
        userDTO.setPasswordHash(user.getPassword());
        userDTO.setSecurityStamp(user.getSecurityStamp());
        userDTO.setConcurrencyStamp(user.getConcurrencyStamp());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setPhoneNumberConfirmed(user.getPhoneNumberConfirmed());
        userDTO.setTwoFactorEnabled(user.getTwoFactorEnabled());
        userDTO.setLockoutEnd(user.getLockoutEnd());
        userDTO.setLockoutEnabled(user.getLockoutEnabled());
        userDTO.setAccessFailedCount(user.getAccessFailedCount());
        userDTO.setVersion(user.getVersion());
        userDTO.setCreatedOn(user.getCreatedOn());
        userDTO.setClaims(user.getClaims());
        userDTO.setRoles(RoleMapper.createDTOSetFromEntitySet(user.getRoles()));
        userDTO.setLogins(user.getLogins());
        userDTO.setTokens(user.getTokens());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setTimeZone(user.getTimeZone());
        userDTO.setDevices(ObjectIdUtils.convertListObjectIdToListString(user.getDevices()));
    }
}
