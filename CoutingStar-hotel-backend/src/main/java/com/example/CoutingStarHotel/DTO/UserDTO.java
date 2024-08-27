package com.example.CoutingStarHotel.DTO;

import com.example.CoutingStarHotel.entities.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Collection<RoleDTO> roles = new HashSet<>();
    private Set<BookedRoom> bookedRooms = new HashSet<>();
    private Hotel hotel;
    private List<Rating> rating;

    public UserDTO(Long id){
        this.id = id;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        for (Role role : user.getRoles()) {
            this.roles.add(new RoleDTO(role));
        }
    }
}
