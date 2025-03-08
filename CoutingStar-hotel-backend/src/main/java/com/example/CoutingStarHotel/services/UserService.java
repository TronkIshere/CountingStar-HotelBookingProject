package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.request.user.LoginRequest;
import com.example.CoutingStarHotel.DTO.request.user.UpdateUserRequest;
import com.example.CoutingStarHotel.DTO.request.user.UploadUserRequest;
import com.example.CoutingStarHotel.DTO.response.jwt.JwtResponse;
import com.example.CoutingStarHotel.DTO.response.common.PageResponse;
import com.example.CoutingStarHotel.DTO.response.user.UserResponse;
import com.example.CoutingStarHotel.entities.User;

import java.util.List;

public interface UserService {
    UserResponse registerUser(UploadUserRequest user);

    User registerHotelOwner(User user);

    List<User> getUsers();

    void deleteUser(String email);

    UserResponse getUser(String email);

    User getUserById(Long userId);

    int getTotalNumberOfUsers();

    double getPercentageOfUsersIncreasedDuringTheMonth();

    PageResponse<UserResponse> getAllUserExceptAdminRole(Integer pageNo, Integer pageSize);

    PageResponse<UserResponse> searchUserByKeyWord(Integer pageNo, Integer pageSize, String keyWord);

    UserResponse getUserByUserId(Long userId);

    UserResponse updateUser(Long userId, UpdateUserRequest request);

    String softDelete(Long userId);
}
