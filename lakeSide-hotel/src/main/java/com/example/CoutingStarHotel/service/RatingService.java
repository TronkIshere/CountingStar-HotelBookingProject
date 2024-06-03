package com.example.CoutingStarHotel.service;

import com.example.CoutingStarHotel.model.BookedRoom;
import com.example.CoutingStarHotel.model.Rating;
import com.example.CoutingStarHotel.model.Room;
import com.example.CoutingStarHotel.model.User;
import com.example.CoutingStarHotel.repository.BookingRepository;
import com.example.CoutingStarHotel.repository.RatingRepository;
import com.example.CoutingStarHotel.repository.RoomRepository;
import com.example.CoutingStarHotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService implements IRatingService{
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    @Override
    public Rating saveRating(Long bookedRoomId, int star, String comment, Date rateDay, Long userId) {
        Rating rating = new Rating();
        rating.setStar(star);
        rating.setComment(comment);
        rating.setRateDay(rateDay);

        User user = userRepository.findById(userId).get();
        user.addComment(rating);

        BookedRoom bookedRoom = bookingRepository.findById(bookedRoomId).get();
        bookedRoom.addComment(rating);

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRatingByRoomId(Long roomId) {
        Room room = roomRepository.findById(roomId).get();
        List<BookedRoom> bookedRoomList = room.getBookings();
        List<Rating> ratingList = new ArrayList<>();
        for (BookedRoom bookedRoom : bookedRoomList) {
            ratingList.add(bookedRoom.getRatings());
        }
        return ratingList;
    }

    @Override
    public Rating updateRating(Long ratingId, int star, String comment) {
        Rating rating = ratingRepository.findById(ratingId).get();
        if(star != 0) rating.setStar(star);
        if(comment != null) rating.setComment(comment);
        return ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }
}
