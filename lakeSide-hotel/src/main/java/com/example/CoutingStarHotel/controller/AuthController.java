package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.exeption.UserAlreadyExistsException;
import com.example.CoutingStarHotel.model.User;
import com.example.CoutingStarHotel.request.LoginRequest;
import com.example.CoutingStarHotel.response.JwtResponse;
import com.example.CoutingStarHotel.security.jwt.JwtUtils;
import com.example.CoutingStarHotel.security.user.HotelUserDetails;
import com.example.CoutingStarHotel.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@EnableAsync
public class AuthController {
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        try{
            userService.registerUser(user);
            return ResponseEntity.ok("Registration successful!");

        }catch (UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<?>> authenticateUser(@Valid @RequestBody LoginRequest request) {
        // Call an async method to handle authentication
        return handleAuthentication(request);
    }

    @Async
    public CompletableFuture<ResponseEntity<?>> handleAuthentication(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtTokenForUser(authentication);
            HotelUserDetails userDetails = (HotelUserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority).toList();
            return CompletableFuture.completedFuture(ResponseEntity.ok(new JwtResponse(
                    userDetails.getId(),
                    userDetails.getEmail(),
                    jwt,
                    roles)));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }
}
