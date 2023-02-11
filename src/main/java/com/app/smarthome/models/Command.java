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
@Document("Commands")
public class Command {

    @Id
    @Field(name = "_id")
    private ObjectId id;

    @Field(name = "DeviceId")
    private ObjectId deviceId;

    @Field(name = "DeviceName")
    private String deviceNameCommand;

    @Field(name = "Name")
    private String commandName;

    @Field(name = "Topic")
    private String topic;

    @Field(name = "payload")
    private String payload;


}
