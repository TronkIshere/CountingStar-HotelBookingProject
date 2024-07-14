package com.example.CoutingStarHotel.response;

import com.example.CoutingStarHotel.model.Role;
import com.example.CoutingStarHotel.model.User;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;

@Data
@NoArgsConstructor
public class RoleResponse {
    private Long id;
    private String name;
    private Collection<User> users = new HashSet<>();

    public RoleResponse(Role role) {
        this.name = role.getName();
    }
}
