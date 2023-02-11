package com.app.smarthome.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Houses")
public class House {

    @Id
    @Field(name = "_id")
    private ObjectId id;

    @Field(name = "Name")
    private String name;

    @Field(name = "Rooms")
    private List<ObjectId> rooms = new ArrayList<>();

    @Field(name = "Devices")
    private List<ObjectId> devicesList = new ArrayList<>();

}
