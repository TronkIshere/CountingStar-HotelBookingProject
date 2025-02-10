package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.request.UpdateUserRequest;
import com.example.CoutingStarHotel.DTO.response.PageResponse;
import com.example.CoutingStarHotel.DTO.response.UserResponse;
import com.example.CoutingStarHotel.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);

    User registerHotelOwner(User user);

    List<User> getUsers();
    void deleteUser(String email);
    UserResponse getUser(String email);

    Optional<User> getUserById(Long userId);

    int getTotalNumberOfUsers();

    double getPercentageOfUsersIncreasedDuringTheMonth();

    PageResponse<UserResponse> getAllUserExceptAdminRole(Integer pageNo, Integer pageSize);

    PageResponse<UserResponse> searchUserByKeyWord(Integer pageNo, Integer pageSize, String keyWord);

    UserResponse getUserByUserId(Long userId);

    UserResponse updateUser(Long userId, UpdateUserRequest request);
}
