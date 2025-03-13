package com.example.CountingStarHotel.service.impl.helpers;

import com.example.CountingStarHotel.entity.Rating;
import com.example.CountingStarHotel.repository.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RatingCoordinator {
    private final RatingRepository repository;

    public List<Rating> getAllRatingByRoomId(Long roomId) {
        return repository.getAllRatingByRoomId(roomId);
    }
}
