package com.app.smarthome.models;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document("Roles")
public class Role {

    @Id
    private ObjectId id;

    @Field("Name")
    private String name;

    @Field("NormalizedName")
    private String normalizedName;
}
