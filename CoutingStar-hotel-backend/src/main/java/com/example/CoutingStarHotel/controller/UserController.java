package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.request.user.UpdateUserRequest;
import com.example.CoutingStarHotel.DTO.request.user.UploadUserRequest;
import com.example.CoutingStarHotel.DTO.response.common.PageResponse;
import com.example.CoutingStarHotel.DTO.response.common.ResponseData;
import com.example.CoutingStarHotel.DTO.response.user.UserResponse;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    @PostMapping("/register-user")
    public ResponseData<UserResponse> registerUser(@Valid @RequestBody UploadUserRequest request) {
        var result = userService.registerUser(request);
        return ResponseData.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success register user")
                .data(result)
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

    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseData<UserResponse> getUserByEmail(@PathVariable String email) {
        var result = userService.getUser(email);
        return ResponseData.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/list/non-admins")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<PageResponse<UserResponse>> getAllUsersExceptAdmin(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        var result = userService.getAllUserExceptAdminRole(pageNo, pageSize);
        return ResponseData.<PageResponse<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<PageResponse<UserResponse>> searchUsers(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "8") Integer pageSize) {
        var result = userService.searchUserByKeyWord(pageNo, pageSize, keyword);
        return ResponseData.<PageResponse<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<UserResponse> getUserById(@PathVariable Long userId) {
        var result = userService.getUserByUserId(userId);
        return ResponseData.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @PutMapping("/update/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<UserResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request) {
        var result = userService.updateUser(userId, request);
        return ResponseData.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') or hasRole('ROLE_HOTEL_OWNER') and #email == principal.username)")
    public ResponseData<String> deleteUser(@PathVariable("userId") String email) {
        userService.deleteUser(email);
        return ResponseData.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data("success")
                .build();
    }

    @PutMapping("/soft-delete/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<String> softDelete(@PathVariable Long userId) {
        var result = userService.softDelete(userId);
        return ResponseData.<String>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }
}