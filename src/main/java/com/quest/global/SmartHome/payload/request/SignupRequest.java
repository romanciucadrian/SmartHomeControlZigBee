package com.quest.global.SmartHome.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    private String userName;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Boolean isRoot;
}
