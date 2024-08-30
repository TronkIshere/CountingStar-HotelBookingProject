package com.example.CoutingStarHotel.repositories;

import com.example.CoutingStarHotel.entities.RedeemedDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RedeemedDiscountRepository extends JpaRepository<RedeemedDiscount, Long> {
    @Query("SELECT COUNT(rd) > 0 FROM RedeemedDiscount rd WHERE rd.user.id = :userId AND rd.discount.id = :discountId")
    boolean existsByUserIdAndDiscountId(@Param("userId") Long userId, @Param("discountId") Long discountId);
}
