package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.entities.Role;
import com.example.CoutingStarHotel.entities.User;

import java.util.Collection;
import java.util.List;

public interface RoleServiceImpl {
    List<Role> getRoles();
    Role createRole(Role theRole);

    void deleteRole(Long id);
    Role findByName(String name);

    Collection<Role> findAllRoleByUserId(Long userId);

    User removeUserFromRole(Long userId, Long roleId);
    User assignRoleToUser(Long userId, Long roleId);
    Role removeAllUsersFromRole(Long roleId);
}
