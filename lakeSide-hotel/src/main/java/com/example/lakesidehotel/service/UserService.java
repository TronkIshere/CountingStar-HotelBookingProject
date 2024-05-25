package com.example.lakesidehotel.service;

import com.example.lakesidehotel.model.Role;
import com.example.lakesidehotel.model.User;
import com.example.lakesidehotel.repository.RoleReponsitory;
import com.example.lakesidehotel.repository.UserRepository;
import com.example.lakesidehotel.security.user.HotelUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleReponsitory roleReponsitory;
    @Override
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new UsernameNotFoundException(user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        Role userRole = roleReponsitory.findByName("ROLE_USER").get();
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
        User theUser = getUser(email);
        if (theUser != null){
            userRepository.deleteByEmail(email);
        }
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("======authentication test========");
        System.out.println(authentication.getDetails());

        if (authentication != null && authentication.getPrincipal() instanceof HotelUserDetails) {
            HotelUserDetails userDetails = (HotelUserDetails) authentication.getPrincipal();
            return userDetails.getId();
        }
        return 4L; // Hoặc ném một ngoại lệ nếu không tìm thấy thông tin người dùng
    }
}
