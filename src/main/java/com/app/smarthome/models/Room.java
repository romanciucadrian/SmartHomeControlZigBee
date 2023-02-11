package com.app.smarthome.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Rooms")
public class Room {

    @Id
    @Field(name = "_id")
    private ObjectId id;

    @Field(name = "HouseId")
    private ObjectId houseId;

    @Field(name = "Name")
    private String name;

    public Room(ObjectId houseId, String name) {
        this.houseId = houseId;
        this.name = name;
    }
}
