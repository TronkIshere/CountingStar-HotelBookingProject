package com.example.CoutingStarHotel.security.user;

import com.example.CoutingStarHotel.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentHotelUserDetails {
    @Autowired
    private JwtUtils jwtUtils;

    public Long getCurrentUserId() {
        String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return jwtUtils.getUserIdFromToken(token);
    }
}
