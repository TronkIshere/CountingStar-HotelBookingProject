package com.example.lakesidehotel.service;

import com.example.lakesidehotel.model.User;

import java.util.List;

public interface IUserService {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String eamil);
}
