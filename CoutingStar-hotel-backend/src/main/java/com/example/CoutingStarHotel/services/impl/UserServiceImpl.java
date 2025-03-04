package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.DTO.request.user.LoginRequest;
import com.example.CoutingStarHotel.DTO.request.user.UpdateUserRequest;
import com.example.CoutingStarHotel.DTO.request.user.UploadUserRequest;
import com.example.CoutingStarHotel.DTO.response.jwt.JwtResponse;
import com.example.CoutingStarHotel.DTO.response.common.PageResponse;
import com.example.CoutingStarHotel.DTO.response.user.UserResponse;
import com.example.CoutingStarHotel.entities.Role;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.mapper.UserMapper;
import com.example.CoutingStarHotel.repositories.UserRepository;
import com.example.CoutingStarHotel.security.jwt.JwtUtils;
import com.example.CoutingStarHotel.security.user.HotelUserDetails;
import com.example.CoutingStarHotel.services.UserService;
import com.example.CoutingStarHotel.services.impl.helpers.RoleCoordinator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    RoleCoordinator roleCoordinator;
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;

    @Override
    public UserResponse registerUser(UploadUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UsernameNotFoundException(request.getEmail() + " already exists");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role userRole = roleCoordinator.getRoleByName("ROLE_USER");
        user.setRoles(Collections.singletonList(userRole));
        user.setRegisterDay(LocalDate.now());
        userRepository.save(user);
        return UserMapper.toUserResponse(user);
    }

    @Override
    public User registerHotelOwner(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UsernameNotFoundException(user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleCoordinator.getRoleByName("ROLE_HOTEL_OWNER");
        user.setRoles(Collections.singletonList(userRole));
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        UserResponse theUser = getUser(email);
        if (theUser != null) {
            userRepository.deleteByEmail(email);
        }
    }

    @Override
    public UserResponse getUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserMapper.toUserResponse(user);
    }

    @Override
    public int getTotalNumberOfUsers() {
        return userRepository.getTotalNumberOfUsers();
    }

    @Override
    public double getPercentageOfUsersIncreasedDuringTheMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfThisMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfNextMonth = firstDayOfThisMonth.plusMonths(1);

        int totalUsers = userRepository.getTotalNumberOfUsers();
        int UsersAddedThisMonth = userRepository.getUsersAddedDuringPeriod(firstDayOfThisMonth, firstDayOfNextMonth);

        return (UsersAddedThisMonth * 100.0) / totalUsers;
    }

    @Override
    public PageResponse<UserResponse> getAllUserExceptAdminRole(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<User> userPage = userRepository.getAllUserExceptAdminRole(pageable);

        List<User> userList = userPage.getContent();

        return PageResponse.<UserResponse>builder()
                .currentPage(pageNo)
                .pageSize(pageable.getPageSize())
                .totalPages(userPage.getTotalPages())
                .totalElements(userPage.getTotalElements())
                .data(UserMapper.userResponses(userList))
                .build();
    }

    @Override
    public PageResponse<UserResponse> searchUserByKeyWord(Integer pageNo, Integer pageSize, String keyWord) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<User> userPage = userRepository.searchUserByKeyWord(pageable, keyWord);

        List<User> userList = userPage.getContent();

        return PageResponse.<UserResponse>builder()
                .currentPage(pageNo)
                .pageSize(pageable.getPageSize())
                .totalPages(userPage.getTotalPages())
                .totalElements(userPage.getTotalElements())
                .data(UserMapper.userResponses(userList))
                .build();
    }

    @Override
    public UserResponse getUserByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = getUserById(userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(user);
        return UserMapper.toUserResponse(user);
    }

    @Override
    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with ID: " + userId));
    }

    @Override
    public JwtResponse getJwtResponse(LoginRequest request) {
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtTokenForUser(authentication);
        HotelUserDetails userDetails = (HotelUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();

        return JwtResponse.builder()
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .token(jwt)
                .roles(roles)
                .build();
    }

    @Override
    public String softDelete(Long userId) {
        LocalDateTime deleteAt = LocalDateTime.now();
        User user = getUserById(userId);
        user.setDeletedAt(deleteAt);
        userRepository.save(user);
        return "User with ID " + userId + " has been delete at " + deleteAt;
    }
}
