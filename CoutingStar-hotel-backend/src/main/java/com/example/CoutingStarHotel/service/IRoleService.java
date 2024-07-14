package com.example.CoutingStarHotel.service;

import com.example.CoutingStarHotel.model.Role;
import com.example.CoutingStarHotel.model.User;

import java.util.Collection;
import java.util.List;

public interface IRoleService {
    List<Role> getRoles();
    Role createRole(Role theRole);

    void deleteRole(Long id);
    Role findByName(String name);

    Collection<Role> findAllRoleByUserId(Long userId);

    User removeUserFromRole(Long userId, Long roleId);
    User assignRoleToUser(Long userId, Long roleId);
    Role removeAllUsersFromRole(Long roleId);
}
