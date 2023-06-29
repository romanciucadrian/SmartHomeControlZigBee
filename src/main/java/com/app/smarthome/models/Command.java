package com.app.smarthome.models;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document("Commands")
public class Command {

    @Id
    private ObjectId id;

    @Field("DeviceId")
    private ObjectId deviceId;

    @Field("DeviceName")
    private String deviceName;

    @Field("Name")
    private String name;

    @Field("Topic")
    private String topic;

    @Field("payload")
    private String payload;
}
