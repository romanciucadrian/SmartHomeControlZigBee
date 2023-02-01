package com.quest.global.SmartHome.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoResponse {

    private String token;
    private ObjectId id;
    private String userName;
    private String email;
    private List<String> roles;


}
