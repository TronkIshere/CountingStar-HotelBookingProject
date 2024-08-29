package com.example.CoutingStarHotel.repositories;

import com.example.CoutingStarHotel.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    @Query("SELECT d FROM Discount d WHERE d.expirationDate >= :currentDate")
    List<Discount> getDiscountNotExpired(LocalDate currentDate);
}
