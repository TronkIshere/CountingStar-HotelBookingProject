package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.entities.*;
import com.example.CoutingStarHotel.repositories.BookingRepository;
import com.example.CoutingStarHotel.repositories.RatingRepository;
import com.example.CoutingStarHotel.repositories.RoomRepository;
import com.example.CoutingStarHotel.repositories.UserRepository;
import com.example.CoutingStarHotel.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

        Pageable pageable = PageRequest.of(0, 1);
        List<BookedRoom> bookedRooms = bookingRepository.findRoomUserHasBookedAndNotComment(hotelId, userId, pageable);

        if (!bookedRooms.isEmpty()) {
            Long bookedRoomIdForAddComment = bookedRooms.get(0).getBookingId();

            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            user.addComment(rating);

            BookedRoom saveBookedRoom = bookingRepository.findById(bookedRoomIdForAddComment)
                    .orElseThrow(() -> new RuntimeException("BookedRoom not found"));
            saveBookedRoom.addComment(rating);

            return ratingRepository.save(rating);
        } else {
            throw new RuntimeException("No booked room found for user without a rating in the specified hotel");
        }
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
    public String checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(Long userId, Long hotelId) {
        Pageable pageable = PageRequest.of(0, 1);
        List<BookedRoom> bookedRooms = bookingRepository.findRoomUserHasBookedAndNotComment(hotelId, userId, pageable);

        if (!bookedRooms.isEmpty()) {
            return bookedRooms.get(0).getRoom().getRoomType();
        } else {
            return "No booked room found without a comment for this user in the specified hotel.";
        }
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
