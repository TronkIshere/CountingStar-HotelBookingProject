package com.example.lakesidehotel.repository;

import com.example.lakesidehotel.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleReponsitory extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);

    boolean existsByName(String roleName);
}
