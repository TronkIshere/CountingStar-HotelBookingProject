package com.example.CoutingStarHotel.services;

import com.example.CoutingStarHotel.entities.*;
import com.example.CoutingStarHotel.repositories.BookingRepository;
import com.example.CoutingStarHotel.repositories.RatingRepository;
import com.example.CoutingStarHotel.repositories.RoomRepository;
import com.example.CoutingStarHotel.repositories.UserRepository;
import com.example.CoutingStarHotel.services.impl.RatingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService implements RatingServiceImpl {
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

    @Override
    public boolean checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(Long userId, Long hotelId) {
        return hasBookedRoom(userId, hotelId) && !hasCommented(userId, hotelId);
    }

    public boolean hasBookedRoom(Long userId, Long hotelId){
        return bookingRepository.hasBookedRoom(userId, hotelId);
    }

    public boolean hasCommented(Long userId, Long hotelId){
        return bookingRepository.hasCommented(userId, hotelId);
    }

    @Override
    public List<Rating> getAllRatingByHotelId(Long hotelId) {
        List<Rating> ratingList = ratingRepository.getAllRatingByHotelId(hotelId);
        return ratingList;
    }

    @Override
    public List<Rating> getAllRatingByRoomId(Long roomId) {
        List<Rating> ratingList = ratingRepository.getAllRatingByRoomId(roomId);
        return ratingList;
    }

    @Override
    public int getTotalNumberOfComments() {
        return ratingRepository.getTotalNumberOfComments();
    }

    @Override
    public double getPercentageOfCommentsIncreaseDuringTheMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfThisMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfNextMonth = firstDayOfThisMonth.plusMonths(1);

        int totalRatings = ratingRepository.getTotalNumberOfComments();
        int RatingsAddedThisMonth = ratingRepository.getUsersAddedDuringPeriod(firstDayOfThisMonth, firstDayOfNextMonth);

        return (RatingsAddedThisMonth * 100.0) / totalRatings;
    }
}
