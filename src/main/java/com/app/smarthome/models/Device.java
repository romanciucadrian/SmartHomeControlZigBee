package com.app.smarthome.models;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Devices")
public class Device {

    @Id
    @Field(name = "_id")
    private ObjectId id;

    @Field(name = "Name")
    private String deviceName;

    @Field(name = "HexId")
    private String hexId;

    private Map<String, Object> properties;

    @JsonAnySetter
    public void add(String key, Object value) {
        if (null == properties) {
            properties = new HashMap<>();
        }
        properties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> get() {
        return properties;
    }

}
