package com.example.CoutingStarHotel.controller;

import com.example.CoutingStarHotel.DTO.response.UserResponse;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.FOUND);
    }

    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_HOTEL_OWNER')")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){
        try{
            User theUser = userService.getUser(email);
            UserResponse userResponse = new UserResponse(theUser);
            return ResponseEntity.ok(userResponse);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }

    @GetMapping("/getAllUserExceptAminRole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUserExceptAminRole(@RequestParam(defaultValue = "0") Integer pageNo,
                                                      @RequestParam(defaultValue = "8") Integer pageSize){
        try{
            Page<User> users = userService.getAllUserExceptAdminRole(pageNo, pageSize);
            Page<UserResponse> userResponse = users.map(this::getUserDTO);
            return ResponseEntity.ok(userResponse);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }

    @GetMapping("/searchUserByKeyWord/{keyWord}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> searchUserByKeyWord(@RequestParam(defaultValue = "0") Integer pageNo,
                                                 @RequestParam(defaultValue = "8") Integer pageSize,
                                                 @PathVariable String keyWord){
        try{
            Page<User> users = userService.searchUserByKeyWord(pageNo, pageSize, keyWord);
            Page<UserResponse> userResponse = users.map(this::getUserDTO);
            return ResponseEntity.ok(userResponse);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }

    @GetMapping("/getUserByUserId/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserByUserId(@PathVariable Long userId){
        try{
            User users = userService.getUserByUserId(userId);
            return ResponseEntity.ok(getUserDTO(users));
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }

    @PostMapping("/updateUser/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long userId,
                                        @RequestParam String firstName,
                                        @RequestParam String lastName,
                                        @RequestParam String email,
                                        @RequestParam String phoneNumber){
        try{
            User users = userService.updateUser(userId, firstName, lastName, email, phoneNumber);
            return ResponseEntity.ok(users);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }

    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') or hasRole('ROLE_HOTEL_OWNER') and #email == principal.username)")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String email){
        try{
            userService.deleteUser(email);
            return ResponseEntity.ok("User deleted successfully");
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user: " + e.getMessage());
        }
    }

    private UserResponse getUserDTO(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }
}
