package com.example.CountingStarHotel.services;

import org.springframework.security.core.Authentication;

public interface JwtService {
    String generateJwtTokenForUser(Authentication authentication);

    String getUserNameFromToken(String token);

    boolean validateToken(String token);
}
