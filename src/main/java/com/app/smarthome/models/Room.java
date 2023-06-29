package com.app.smarthome.models;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@Document("Rooms")
public class Room {

    @Id
    private ObjectId id;

    @Field("HouseId")
    private ObjectId houseId;

    @Field("Devices")
    private List<ObjectId> devices;

    @Field("Name")
    private String name;
}
