package com.example.CountingStarHotel.DTO.response.role;

import com.example.CountingStarHotel.entities.User;
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
