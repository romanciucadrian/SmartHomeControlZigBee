package com.app.smarthome.utils;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ObjectIdUtils {

    public static List<String> convertListObjectIdToListString(List<ObjectId> objectIds) {
        List<String> stringIds = new ArrayList<>();
        objectIds.forEach(objectId -> stringIds.add(objectId.toHexString()));
        return stringIds;
    }

    public static List<ObjectId> convertListStringToListObjectId(List<String> stringIds) {
        List<ObjectId> objectIds = new ArrayList<>();
        stringIds.forEach(stringId -> objectIds.add(convertStringToObjectId(stringId)));
        return objectIds;
    }

    public static ObjectId convertStringToObjectId(String stringId) {
        return new ObjectId(stringId);
    }
}
