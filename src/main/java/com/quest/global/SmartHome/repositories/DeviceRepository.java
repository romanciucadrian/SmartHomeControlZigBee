package com.quest.global.SmartHome.repositories;

import com.quest.global.SmartHome.models.Device;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends MongoRepository<Device, ObjectId> {

    Device findDeviceByDeviceName(String deviceName);

    Optional<Device> findDeviceByHexId(String hexId);


}
