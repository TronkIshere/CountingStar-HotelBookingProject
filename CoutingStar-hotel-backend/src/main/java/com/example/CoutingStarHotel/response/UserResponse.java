package com.example.CoutingStarHotel.response;

import com.example.CoutingStarHotel.model.*;
import com.example.CoutingStarHotel.service.IRoleService;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Collection<RoleResponse> roles = new HashSet<>();
    private Set<BookedRoom> bookedRooms = new HashSet<>();
    private Hotel hotel;
    private List<Rating> rating;

    public UserResponse(Long id){
        this.id = id;
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        for (Role role : user.getRoles()) {
            this.roles.add(new RoleResponse(role));
        }
    }
}
