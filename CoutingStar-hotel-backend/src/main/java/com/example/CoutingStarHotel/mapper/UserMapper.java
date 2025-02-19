package com.example.CoutingStarHotel.mapper;

import com.example.CoutingStarHotel.DTO.response.user.UserResponse;
import com.example.CoutingStarHotel.entities.User;

import java.util.List;

public class UserMapper {
    public UserMapper() {
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public static List<UserResponse> userResponses(List<User> users) {
        return users.stream()
                .map(UserMapper::toUserResponse)
                .toList();
    }
}
