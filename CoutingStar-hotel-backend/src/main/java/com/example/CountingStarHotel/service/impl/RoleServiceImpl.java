package com.example.CountingStarHotel.service.impl;

import com.example.CountingStarHotel.entity.Role;
import com.example.CountingStarHotel.entity.User;
import com.example.CountingStarHotel.exception.ApplicationException;
import com.example.CountingStarHotel.exception.ErrorCode;
import com.example.CountingStarHotel.repository.RoleRepository;
import com.example.CountingStarHotel.service.RoleService;
import com.example.CountingStarHotel.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    UserService userService;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_" + theRole.getName().toUpperCase();
        Role role = new Role(roleName);
        if (roleRepository.existsByName(roleName)) {
            throw new ApplicationException(ErrorCode.ROLE_ALREADY_EXCEPTION);
        }
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Role not found with ID: " + id));
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        User user = userService.getUserById(userId);
        Role role = getRoleById(roleId);

        if (user.getRoles().contains(role)) {
            throw new ApplicationException(ErrorCode.ROLE_ALREADY_EXCEPTION);
        }

        role.assignRoleToUser(user);
        roleRepository.save(role);

        return user;
    }


    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        User user = userService.getUserById(userId);
        Role role = getById(roleId);

        if (role == null) {
            throw new EntityNotFoundException("Role not found with ID: " + roleId);
        }

        if (user.getRoles().contains(role)) {
            throw new ApplicationException(ErrorCode.USER_ALREADY_HAS_THIS_ROLE);
        }

        role.assignRoleToUser(user);
        roleRepository.save(role);
        return user;
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Role role = getById(roleId);
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Role not found with name: " + name));
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Role not found with ID: " + id));
    }
}
