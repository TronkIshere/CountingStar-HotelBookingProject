package com.example.CoutingStarHotel.response;

import com.example.CoutingStarHotel.model.BookedRoom;
import com.example.CoutingStarHotel.model.Hotel;
import com.example.CoutingStarHotel.model.Rating;
import com.example.CoutingStarHotel.model.Role;
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
    private Collection<Role> roles = new HashSet<>();
    private Set<BookedRoom> bookedRooms = new HashSet<>();
    private Hotel hotel;
    private List<Rating> rating;

    public UserResponse(Long id){
        this.id = id;
    }
}
