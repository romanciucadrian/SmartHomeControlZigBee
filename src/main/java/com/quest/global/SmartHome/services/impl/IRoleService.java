package com.quest.global.SmartHome.services.impl;

import com.quest.global.SmartHome.models.ERole;
import com.quest.global.SmartHome.models.Role;

public interface IRoleService {

    Role findRoleByName(ERole name);
}
