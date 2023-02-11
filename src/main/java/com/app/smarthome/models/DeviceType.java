package com.app.smarthome.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("DeviceTypes")
public class DeviceType {

    @Id
    @Field(name = "_id")
    private ObjectId id;

    @Field(name = "Name")
    private String deviceTypeName;

    @Field(name = "mqttTopics")
    private List<String> mqttTopics = new ArrayList<>();

    @Field(name = "DeviceHexName")
    private String deviceHexName;

}
