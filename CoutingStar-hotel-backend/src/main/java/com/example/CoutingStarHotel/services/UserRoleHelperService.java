package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.entities.Role;
import com.example.CoutingStarHotel.entities.User;

public interface UserRoleHelperService {
    User getUserById(Long userId);

    Role getRoleByName(String roleName);
}
