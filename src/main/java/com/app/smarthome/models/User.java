package com.app.smarthome.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Users")
public class User {

    @Id
    @Field(name = "_id")
    private ObjectId id;

    @Field(name = "UserName")
    private String userName;

    @Field(name = "NormalizedUserName")
    private String normalizedUserName;

    @Field(name = "Email")
    private String email;

    @Field(name = "NormalizedEmail")
    private String normalizedEmail;

    @Field(name = "EmailConfirmed")
    private Boolean emailConfirmed;

    @Field(name = "PasswordHash")
    private String password;

    @Field(name = "roles")
    @DBRef
    private List<Role> roles = new ArrayList<>();

    @Field(name = "FirstName")
    private String firstName;

    @Field(name = "LastName")
    private String lastName;

    @Field(name = "Devices")
    private List<ObjectId> devicesList = new ArrayList<>();

    @Field(name = "isRoot")
    private Boolean isRoot = true;

    @Field(name = "Houses")
    private List<ObjectId> housesList = new ArrayList<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String userName, String email, String firstName, String lastName, Boolean isRoot, String password) {
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isRoot = isRoot;
        this.password = password;
    }

}