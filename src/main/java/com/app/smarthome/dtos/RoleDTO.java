package com.app.smarthome.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RoleDTO implements Serializable {

    private String id;

    private String name;

    private String normalizedName;
}
