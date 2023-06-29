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
@Document("DeviceTypes")
public class DeviceType {

    @Id
    private ObjectId id;

    @Field("Name")
    private String name;

    @Field("mqttTopics")
    private List<String> mqttTopics;

    @Field("DeviceHexName")
    private String deviceHexName;
}
