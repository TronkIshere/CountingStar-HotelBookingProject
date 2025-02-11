package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.entities.Role;
import com.example.CoutingStarHotel.entities.User;

public interface UserAndRoleService {
    User getUserById(Long userId);

    Role getRoleByName(String roleName);
}
