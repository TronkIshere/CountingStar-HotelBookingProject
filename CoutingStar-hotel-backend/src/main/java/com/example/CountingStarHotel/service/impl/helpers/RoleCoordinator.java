package com.example.CountingStarHotel.service.impl.helpers;

import com.example.CountingStarHotel.entity.Role;
import com.example.CountingStarHotel.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleCoordinator {
    private final RoleRepository roleRepository;

    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));
    }
}
