package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.DTO.request.user.LoginRequest;
import com.example.CoutingStarHotel.DTO.response.jwt.JwtResponse;

public interface AuthenticationService {
    JwtResponse signIn(LoginRequest request);
}
