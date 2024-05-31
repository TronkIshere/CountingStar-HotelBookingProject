package com.example.CoutingStarHotel.repository;

import com.example.CoutingStarHotel.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
