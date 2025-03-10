package com.example.CountingStarHotel.repositories;

import com.example.CountingStarHotel.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Optional<Role> findByName(String name);

    boolean existsByName(String roleName);
}
