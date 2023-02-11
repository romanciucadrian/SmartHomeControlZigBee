package com.app.smarthome.dtos;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class UserViewDTO {

    private String email;

    private String firstName;

    private String lastName;

    private List<ObjectId> housesList;
}
