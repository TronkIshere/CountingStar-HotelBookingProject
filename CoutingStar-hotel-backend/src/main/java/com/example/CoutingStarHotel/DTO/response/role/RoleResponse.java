package com.example.CoutingStarHotel.DTO.response.role;

import com.example.CoutingStarHotel.entities.User;
import lombok.*;
import java.util.Collection;

@Getter
@Setter
@Builder
public class RoleResponse {
    private Long id;
    private String name;
    private Collection<User> users;
}
