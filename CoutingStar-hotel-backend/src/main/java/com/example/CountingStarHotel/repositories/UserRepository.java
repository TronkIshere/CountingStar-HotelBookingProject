package com.example.CountingStarHotel.repositories;

import com.example.CountingStarHotel.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT u FROM User u WHERE NOT EXISTS (SELECT r FROM u.roles r WHERE r.name = 'ROLE_ADMIN')")
    Page<User> getAllUserExceptAdminRole(Pageable pageable);

    @Query("SELECT u FROM User u WHERE lower(u.firstName) LIKE lower(concat('%', :keyword, '%')) OR " +
            "lower(u.lastName) LIKE lower(concat('%', :keyword, '%')) OR " +
            "lower(u.phoneNumber) LIKE lower(concat('%', :keyword, '%')) OR " +
            "cast(u.id as string) LIKE lower(concat('%', :keyword, '%'))")
    Page<User> searchUserByKeyWord(Pageable pageable, String keyword);

}
