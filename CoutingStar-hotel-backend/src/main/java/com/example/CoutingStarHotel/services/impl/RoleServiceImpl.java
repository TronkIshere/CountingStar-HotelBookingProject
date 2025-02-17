package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.entities.Role;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.exception.RoleAlreadyExistException;
import com.example.CoutingStarHotel.exception.UserAlreadyExistsException;
import com.example.CoutingStarHotel.repositories.RoleRepository;
import com.example.CoutingStarHotel.services.RoleService;
import com.example.CoutingStarHotel.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_" + theRole.getName().toUpperCase();
        Role role = new Role(roleName);
        if (roleRepository.existsByName(roleName)) {
            throw new RoleAlreadyExistException(theRole.getName() + " role already exists");
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
            throw new UserAlreadyExistsException(
                    user.getFirstName() + " is already assigned to the " + role.getName() + " role");
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
            throw new UserAlreadyExistsException(
                    user.getFirstName() + " is already assigned to the " + role.getName() + " role");
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
