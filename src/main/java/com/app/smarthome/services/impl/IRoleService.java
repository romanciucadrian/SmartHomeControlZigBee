package com.app.smarthome.services.impl;

import com.app.smarthome.models.ERole;
import com.app.smarthome.models.Role;

public interface IRoleService {

    Role findRoleByName(ERole name);
}
