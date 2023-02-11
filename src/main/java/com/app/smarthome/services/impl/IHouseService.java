package com.app.smarthome.services.impl;

import com.app.smarthome.exceptions.HouseNotFoundException;
import com.app.smarthome.models.House;

import java.util.List;

public interface IHouseService {

    House createHouse(House house);

    House updateHouseName(String houseName, String houseNewName) throws HouseNotFoundException;

    List<House> listAll();
}
