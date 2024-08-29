package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceImpl {
    User registerUser(User user);

    User registerHotelOwner(User user);

    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);

    Optional<User> getUserById(Long userId);

    int getTotalNumberOfUsers();

    double getPercentageOfUsersIncreasedDuringTheMonth();
}
