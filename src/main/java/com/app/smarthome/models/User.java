package com.app.smarthome.models;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Document("Users")
public class User {

    @Id
    private ObjectId id;

    @Field("UserName")
    private String username;

    @Field("NormalizedUserName")
    private String normalizedUserName;

    @Field("Email")
    private String email;

    @Field("NormalizedEmail")
    private String normalizedEmail;

    @Field("EmailConfirmed")
    private Boolean emailConfirmed;

    @Field("PasswordHash")
    private String password;

    @Field("SecurityStamp")
    private String securityStamp;

    @Field("ConcurrencyStamp")
    private String concurrencyStamp;

    @Field("PhoneNumber")
    private String phoneNumber;

    @Field("PhoneNumberConfirmed")
    private Boolean phoneNumberConfirmed;

    @Field("TwoFactorEnabled")
    private Boolean twoFactorEnabled;

    @Field("LockoutEnd")
    private Boolean lockoutEnd;

    @Field("LockoutEnabled")
    private Boolean lockoutEnabled;

    @Field("AccessFailedCount")
    private Long accessFailedCount;

    @Field("Version")
    private Long version;

    @Field("CreatedOn")
    private Date createdOn;

    @Field("Claims")
    private List<String> claims;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    @Field("Logins")
    private List<String> logins;

    @Field("Tokens")
    private List<String> tokens;

    @Field("FirstName")
    private String firstName;

    @Field("LastName")
    private String lastName;

    @Field("TimeZone")
    private String timeZone;

    @Field("Devices")
    private List<ObjectId> devices;

    @Field("isRoot")
    private Boolean root;

    @Field("Houses")
    private List<ObjectId> houses;
}
