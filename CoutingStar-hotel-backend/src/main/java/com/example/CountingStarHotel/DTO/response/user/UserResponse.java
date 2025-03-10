package com.example.CountingStarHotel.DTO.response.user;

import com.example.CountingStarHotel.DTO.response.role.RoleResponse;
import com.example.CountingStarHotel.entities.*;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class UserResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Collection<RoleResponse> roles;
    private Set<BookedRoom> bookedRooms;
    private Hotel hotel;
    private List<Rating> rating;
}
