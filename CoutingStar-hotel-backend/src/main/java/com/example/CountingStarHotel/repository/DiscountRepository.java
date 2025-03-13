package com.example.CountingStarHotel.repository;

import com.example.CountingStarHotel.entity.Discount;
import com.example.CountingStarHotel.entity.RedeemedDiscount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    @Query("SELECT d FROM Discount d WHERE d.expirationDate >= :currentDate")
    Page<Discount> getDiscountNotExpired(Pageable pageable, LocalDate currentDate);

    @Query("SELECT rd FROM RedeemedDiscount rd WHERE rd.user.id = :userId AND rd.discount.expirationDate >= :currentDate")
    List<RedeemedDiscount> getAllRedeemedDiscountNotExpiredByUserId(Long userId, LocalDate currentDate);

    @Query("SELECT d FROM Discount d WHERE lower(d.discountName) LIKE lower(concat('%', :keyword, '%')) OR " +
            "lower(d.discountDescription) LIKE lower(concat('%', :keyword, '%')) OR " +
            "cast(d.id as string) LIKE lower(concat('%', :keyword, '%')) ")
    Page<Discount> getDiscountByKeyword(Pageable pageable, String keyword);

    @Query("SELECT d FROM Discount d WHERE d.discountName = :name")
    Discount findByName(String name);
}
