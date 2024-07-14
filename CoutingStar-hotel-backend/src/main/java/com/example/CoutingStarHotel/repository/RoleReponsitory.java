package com.example.CoutingStarHotel.repository;

import com.example.CoutingStarHotel.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface RoleReponsitory extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);

    boolean existsByName(String roleName);

    @Query("SELECT r FROM Role r JOIN r.users u WHERE u.id = :userId")
    Collection<Role> findByUserId(Long userId);
}
