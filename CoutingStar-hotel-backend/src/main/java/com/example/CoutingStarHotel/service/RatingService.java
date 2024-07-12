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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService implements IRatingService{
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    @Override
    public Rating saveRating(Long hotelId, Long userId, int star, String comment, LocalDate rateDay) {
        Rating rating = new Rating();
        rating.setStar(star);
        rating.setComment(comment);
        rating.setRateDay(rateDay);

        Long bookedRoomIdForAddComment = bookingRepository.findRoomUserHasBookedAndNotComment(hotelId, userId);

        User user = userRepository.findById(userId).get();
        user.addComment(rating);

        BookedRoom bookedRoom = bookingRepository.findById(bookedRoomIdForAddComment).get();
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

    public boolean checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(Long userId, Long hotelId) {
        return hasBookedRoom(userId, hotelId) && !hasCommented(userId, hotelId);
    }

    public boolean hasBookedRoom(Long userId, Long hotelId){
        return bookingRepository.hasBookedRoom(userId, hotelId);
    }

    public boolean hasCommented(Long userId, Long hotelId){
        return bookingRepository.hasCommented(userId, hotelId);
    }
}
