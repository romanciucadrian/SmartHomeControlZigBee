package com.app.smarthome.repositories;

import com.app.smarthome.models.Device;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends MongoRepository<Device, ObjectId> {

    Optional<Device> findDeviceByHexId(String hexId);
    Optional<Device> findDeviceByName(String name);
    Boolean existsByName(String deviceName);
}
