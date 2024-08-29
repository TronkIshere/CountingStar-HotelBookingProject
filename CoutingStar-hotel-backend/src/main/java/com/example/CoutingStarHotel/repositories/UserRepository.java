package com.example.CoutingStarHotel.repositories;

import com.example.CoutingStarHotel.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    void deleteByEmail(String email);
    Optional<User> findByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u")
    int getTotalNumberOfUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.registerDay >= :startDate AND u.registerDay < :endDate")
    int getUsersAddedDuringPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
