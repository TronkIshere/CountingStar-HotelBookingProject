package com.example.lakesidehotel.service;

import com.example.lakesidehotel.model.Role;
import com.example.lakesidehotel.model.User;
import com.example.lakesidehotel.repository.RoleReponsitory;
import com.example.lakesidehotel.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
        System.out.println("==========Get user by emil is start working==========");
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
