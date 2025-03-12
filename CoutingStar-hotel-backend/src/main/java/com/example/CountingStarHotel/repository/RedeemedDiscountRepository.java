package com.example.CountingStarHotel.repository;

import com.example.CountingStarHotel.entity.RedeemedDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RedeemedDiscountRepository extends JpaRepository<RedeemedDiscount, Long> {
    @Query("SELECT CASE WHEN COUNT(rd) > 0 THEN true ELSE false END FROM RedeemedDiscount rd WHERE rd.user.id = :userId AND rd.discount.id = :discountId")
    boolean existsByUserIdAndDiscountId(@Param("userId") Long userId, @Param("discountId") Long discountId);

}
