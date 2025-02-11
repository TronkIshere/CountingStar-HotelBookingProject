package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.repositories.RoleRepository;
import com.example.CoutingStarHotel.repositories.UserRepository;
import com.example.CoutingStarHotel.services.UserRoleHelperService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.CoutingStarHotel.entities.Role;

@Service
@RequiredArgsConstructor
public class UserRoleHelperServiceImpl implements UserRoleHelperService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
    }
}
