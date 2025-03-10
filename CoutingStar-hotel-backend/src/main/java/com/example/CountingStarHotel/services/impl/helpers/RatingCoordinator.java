package com.example.CountingStarHotel.services.impl.helpers;

import com.example.CountingStarHotel.entities.Rating;
import com.example.CountingStarHotel.repositories.RatingRepository;
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
