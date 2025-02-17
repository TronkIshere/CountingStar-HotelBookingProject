package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.request.UpdateUserRequest;
import com.example.CoutingStarHotel.DTO.response.PageResponse;
import com.example.CoutingStarHotel.DTO.response.ResponseData;
import com.example.CoutingStarHotel.DTO.response.UserResponse;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.services.UserService;
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

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.FOUND);
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseData<UserResponse> getUserByEmail(@PathVariable("email") String email) {
        var result = userService.getUser(email);
        return ResponseData.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/getAllUserExceptAminRole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<PageResponse<UserResponse>> getAllUserExceptAminRole(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                             @RequestParam(defaultValue = "8") Integer pageSize) {
        var result = userService.getAllUserExceptAdminRole(pageNo, pageSize);
        return ResponseData.<PageResponse<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/searchUserByKeyWord/{keyWord}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<PageResponse<UserResponse>> searchUserByKeyWord(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                        @RequestParam(defaultValue = "8") Integer pageSize,
                                                                        @PathVariable String keyWord) {
        var result = userService.searchUserByKeyWord(pageNo, pageSize, keyWord);
        return ResponseData.<PageResponse<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @GetMapping("/getUserByUserId/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<UserResponse> getUserByUserId(@PathVariable Long userId) {
        var result = userService.getUserByUserId(userId);
        return ResponseData.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(result)
                .build();
    }

    @PostMapping("/updateUser/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<UserResponse> updateUser(@PathVariable Long userId,
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
}
