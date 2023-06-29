package com.app.smarthome.dtos;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginDTO implements Serializable {

    @NonNull
    private String email;
    @NonNull
    private String password;

}
