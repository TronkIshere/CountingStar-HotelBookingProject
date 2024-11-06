package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.entities.*;
import com.example.CoutingStarHotel.repositories.BookingRepository;
import com.example.CoutingStarHotel.repositories.RatingRepository;
import com.example.CoutingStarHotel.repositories.RoomRepository;
import com.example.CoutingStarHotel.repositories.UserRepository;
import com.example.CoutingStarHotel.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    @Override
    public Rating saveRating(Long hotelId, Long userId, int star, String comment, LocalDate rateDay) {
        Rating rating = new Rating();
        rating.setStar(star);
        rating.setComment(comment);
        rating.setRateDay(rateDay);

        BookedRoom bookedRoom = bookingRepository.findRoomUserHasBookedAndNotComment(hotelId, userId);
        Long bookedRoomIdForAddComment = bookedRoom.getRoom().getId();

        User user = userRepository.findById(userId).get();
        user.addComment(rating);

        BookedRoom saveBookedRoom = bookingRepository.findById(bookedRoomIdForAddComment).get();
        saveBookedRoom.addComment(rating);

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
    public String checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(Long userId, Long hotelId) {bookingRepository.findRoomUserHasBookedAndNotComment(hotelId, userId);
        BookedRoom bookedRoom = bookingRepository.findRoomUserHasBookedAndNotComment(hotelId, userId);
        return bookedRoom.getRoom().getRoomType();
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

    @Override
    public int getTotalRatingInSpecificHotel(Long hotelId) {
        return ratingRepository.getTotalRatingInSpecificHotel(hotelId);
    }

    @Override
    public double getPercentageOfRatingIncreasedDuringTheMonthForHotel(Long hotelId) {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfThisMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfNextMonth = firstDayOfThisMonth.plusMonths(1);

        int totalRatings = ratingRepository.getTotalRatingInSpecificHotel(hotelId);
        int RatingsAddedThisMonth = ratingRepository.getRatingsAddedDuringThisPeriod(hotelId, firstDayOfThisMonth, firstDayOfNextMonth);

        return (RatingsAddedThisMonth * 100.0) / totalRatings;
    }
}
