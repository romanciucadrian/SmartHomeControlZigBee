package com.app.smarthome.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class HouseDTO implements Serializable {

    private String id;

    private String name;

    private List<String> rooms;

    private List<String> devices;
}
