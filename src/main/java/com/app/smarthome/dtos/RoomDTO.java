package com.app.smarthome.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class RoomDTO implements Serializable {

    private String id;

    private String houseId;

    private List<String> devices;

    private String name;
}
