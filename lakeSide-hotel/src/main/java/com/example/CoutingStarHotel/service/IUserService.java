package com.example.CoutingStarHotel.service;

import com.example.CoutingStarHotel.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);

    Optional<User> getUserById(Long userId);

    Long getCurrentUserId();
}
