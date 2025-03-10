package com.example.CountingStarHotel.repositories;

import com.example.CountingStarHotel.entities.RedeemedDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RedeemedDiscountRepository extends JpaRepository<RedeemedDiscount, Long> {
    @Query("SELECT CASE WHEN COUNT(rd) > 0 THEN true ELSE false END FROM RedeemedDiscount rd WHERE rd.user.id = :userId AND rd.discount.id = :discountId")
    boolean existsByUserIdAndDiscountId(@Param("userId") Long userId, @Param("discountId") Long discountId);

}
