package com.quest.global.SmartHome.services.impl;

import com.quest.global.SmartHome.exceptions.HouseNotFoundException;
import com.quest.global.SmartHome.models.House;

import java.util.List;

public interface IHouseService {

    House createHouse(House house);

    House updateHouseName(String houseName, String houseNewName) throws HouseNotFoundException;

    List<House> listAll();
}
