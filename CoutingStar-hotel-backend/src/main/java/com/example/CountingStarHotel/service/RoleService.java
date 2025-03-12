package com.example.CountingStarHotel.service;

import com.example.CountingStarHotel.entity.Role;
import com.example.CountingStarHotel.entity.User;

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
