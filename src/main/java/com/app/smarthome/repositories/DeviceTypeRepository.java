package com.app.smarthome.repositories;

import com.app.smarthome.models.DeviceType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceTypeRepository extends MongoRepository<DeviceType, ObjectId> {

    Optional<DeviceType> findDeviceTypeByDeviceHexName(String deviceHexName);
}
