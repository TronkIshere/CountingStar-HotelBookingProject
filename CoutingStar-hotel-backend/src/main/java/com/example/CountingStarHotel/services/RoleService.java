package com.example.CountingStarHotel.services;

import com.example.CountingStarHotel.entities.Role;
import com.example.CountingStarHotel.entities.User;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();

    Role createRole(Role theRole);

    void deleteRole(Long id);

    Role getRoleById(Long id);

    User removeUserFromRole(Long userId, Long roleId);

    User assignRoleToUser(Long userId, Long roleId);

    Role removeAllUsersFromRole(Long roleId);

    Role findByName(String name);

    Role getById(Long id);
}
