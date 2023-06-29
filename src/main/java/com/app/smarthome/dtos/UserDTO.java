package com.app.smarthome.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDTO implements Serializable {

    private String id;

    private String username;

    private String normalizedUserName;

    private String email;

    private String normalizedEmail;

    private Boolean emailConfirmed;

    private String passwordHash;

    private String securityStamp;

    private String concurrencyStamp;

    private String phoneNumber;

    private Boolean phoneNumberConfirmed;

    private Boolean twoFactorEnabled;

    private Boolean lockoutEnd;

    private Boolean lockoutEnabled;

    private Long accessFailedCount;

    private Long version;

    private Date createdOn;

    private List<String> claims;

    private Set<RoleDTO> roles;

    private List<String> logins;

    private List<String> tokens;

    private String firstName;

    private String lastName;

    private String timeZone;

    private List<String> devices;
}
