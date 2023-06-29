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
@Document("Houses")
public class House {

    @Id
    private ObjectId id;

    @Field("Name")
    private String name;

    @Field("Rooms")
    private List<ObjectId> rooms;

    @Field("Devices")
    private List<ObjectId> devices;
}
