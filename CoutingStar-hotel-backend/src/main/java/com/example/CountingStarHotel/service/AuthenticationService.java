package com.example.CountingStarHotel.service;

import com.example.CountingStarHotel.DTO.request.user.LoginRequest;
import com.example.CountingStarHotel.DTO.response.jwt.JwtResponse;

public interface AuthenticationService {
    JwtResponse signIn(LoginRequest request);
}
