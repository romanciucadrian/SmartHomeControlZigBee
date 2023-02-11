package com.app.smarthome.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class RoomDTO {

    private ObjectId id;

    private ObjectId houseId;

    private String name;

}
