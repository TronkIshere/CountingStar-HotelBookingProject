package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.DTO.request.UpdateUserRequest;
import com.example.CoutingStarHotel.DTO.response.PageResponse;
import com.example.CoutingStarHotel.DTO.response.RoomResponse;
import com.example.CoutingStarHotel.DTO.response.UserResponse;
import com.example.CoutingStarHotel.entities.Role;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.mapper.RoomMapper;
import com.example.CoutingStarHotel.mapper.UserMapper;
import com.example.CoutingStarHotel.repositories.RoleReponsitory;
import com.example.CoutingStarHotel.repositories.UserRepository;
import com.example.CoutingStarHotel.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleReponsitory roleReponsitory;
    @Override
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new UsernameNotFoundException(user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleReponsitory.findByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(userRole));
        user.setRegisterDay(LocalDate.now());
        return userRepository.save(user);
    }

    @Override
    public User registerHotelOwner(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new UsernameNotFoundException(user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleReponsitory.findByName("ROLE_HOTEL_OWNER").get();
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
        if (theUser != null){
            userRepository.deleteByEmail(email);
        }
    }

    @Override
    public UserResponse getUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserMapper.toUserResponse(user);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
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
        User user = userRepository.findById(userId).get();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(user);
        return UserMapper.toUserResponse(user);
    }
}
