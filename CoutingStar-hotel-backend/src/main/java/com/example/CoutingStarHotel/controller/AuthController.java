package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.request.user.LoginRequest;
import com.example.CoutingStarHotel.DTO.response.common.ResponseData;
import com.example.CoutingStarHotel.DTO.response.jwt.JwtResponse;
import com.example.CoutingStarHotel.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseData<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest request) {
        JwtResponse jwtResponse = authenticationService.signIn(request);
        return ResponseData.<JwtResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(jwtResponse)
                .build();
    }
}
