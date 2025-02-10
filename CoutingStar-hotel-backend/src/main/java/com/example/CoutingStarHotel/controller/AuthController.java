package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.request.LoginRequest;
import com.example.CoutingStarHotel.DTO.response.JwtResponse;
import com.example.CoutingStarHotel.DTO.response.ResponseData;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.services.UserService;
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
    private final UserService userService;

    @PostMapping("/register-user")
    public ResponseData<String> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseData.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(registeredUser.getEmail())
                .build();
    }

    @PostMapping("/register-hotelOwner")
    public ResponseData<Long> registerHotelOwnerAndReturnId(@RequestBody User user) {
        User registeredUser = userService.registerHotelOwner(user);
        return ResponseData.<Long>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(registeredUser.getId())
                .build();
    }

    @PostMapping("/login")
    public ResponseData<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest request) {
        JwtResponse jwtResponse = userService.getJwtResponse(request);
        return ResponseData.<JwtResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(jwtResponse)
                .build();
    }
}
