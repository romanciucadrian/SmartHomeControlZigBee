package com.app.smarthome.utils;

import com.app.smarthome.exceptions.InvalidArgumentException;
import org.json.JSONObject;

import static com.app.smarthome.constants.AppConstants.*;

public class JsonUtils {

    public static Object getJSONValue(JSONObject jsonObject, String type, String key) throws InvalidArgumentException {
        Object object;
        switch (type) {
            case OBJECT:
                object = jsonObject.getJSONObject(key);
                break;
            case ARRAY:
                object = jsonObject.getJSONArray(key);
                break;
            case BIG_DECIMAL:
                object = jsonObject.getBigDecimal(key);
                break;
            case STRING:
                object = jsonObject.getString(key);
                break;
            case LONG:
                object = jsonObject.getLong(key);
                break;
            case BOOLEAN:
                object = jsonObject.getBoolean(key);
                break;
            default:
                throw new InvalidArgumentException("Invalid JSON found for deserialization");
        }
        return object;
    }
}
