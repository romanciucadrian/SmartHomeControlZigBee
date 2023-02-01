package com.quest.global.SmartHome.dtos;


import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class HouseDTO {

    private ObjectId id;

    private String name;

    private List<ObjectId> rooms;

}
