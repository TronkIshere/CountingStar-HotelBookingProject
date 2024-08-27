package com.example.CoutingStarHotel.DTO;

import com.example.CoutingStarHotel.entities.Role;
import com.example.CoutingStarHotel.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;

@Data
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private String name;
    private Collection<User> users = new HashSet<>();

    public RoleDTO(Role role) {
        this.name = role.getName();
    }
}
