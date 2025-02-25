package com.example.CoutingStarHotel.data;

import com.example.CoutingStarHotel.entities.Role;
import com.example.CoutingStarHotel.repositories.RoleRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer{
    @Bean
    public ApplicationRunner initData(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                roleRepository.save(new Role("ROLE_USER"));
                roleRepository.save(new Role("ROLE_HOTEL_OWNER"));
                roleRepository.save(new Role("ROLE_ADMIN"));
                System.out.println("Dummy data inserted!");
            }
        };
    }
}