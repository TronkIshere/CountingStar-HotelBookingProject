package com.example.CountingStarHotel.services;

import com.example.CountingStarHotel.DTO.request.user.UpdateUserRequest;
import com.example.CountingStarHotel.DTO.request.user.UploadUserRequest;
import com.example.CountingStarHotel.DTO.response.common.PageResponse;
import com.example.CountingStarHotel.DTO.response.user.UserResponse;
import com.example.CountingStarHotel.entities.User;

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
