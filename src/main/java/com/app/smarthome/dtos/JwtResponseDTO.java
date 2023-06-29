package com.app.smarthome.dtos;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class JwtResponseDTO implements Serializable {

    private String token;
    private String type = "Bearer";
    private ObjectId id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponseDTO(String token ,ObjectId id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
