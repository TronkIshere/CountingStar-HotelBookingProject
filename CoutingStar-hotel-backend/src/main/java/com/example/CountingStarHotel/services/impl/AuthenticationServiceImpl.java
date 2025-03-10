package com.example.CountingStarHotel.services.impl;

import com.example.CountingStarHotel.DTO.request.user.LoginRequest;
import com.example.CountingStarHotel.DTO.response.jwt.JwtResponse;
import com.example.CountingStarHotel.entities.User;
import com.example.CountingStarHotel.services.AuthenticationService;
import com.example.CountingStarHotel.services.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    @Override
    public JwtResponse signIn(LoginRequest request) {
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtTokenForUser(authentication);
        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();

        return JwtResponse.builder()
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .token(jwt)
                .roles(roles)
                .build();
    }
}
