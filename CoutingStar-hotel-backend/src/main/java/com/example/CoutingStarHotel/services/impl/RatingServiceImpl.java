package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.DTO.request.AddRatingRequest;
import com.example.CoutingStarHotel.DTO.request.UpdateRatingRequest;
import com.example.CoutingStarHotel.DTO.response.RatingResponse;
import com.example.CoutingStarHotel.entities.BookedRoom;
import com.example.CoutingStarHotel.entities.Rating;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.mapper.RatingMapper;
import com.example.CoutingStarHotel.repositories.RatingRepository;
import com.example.CoutingStarHotel.services.BookingService;
import com.example.CoutingStarHotel.services.RatingService;
import com.example.CoutingStarHotel.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingServiceImpl implements RatingService {
    RatingRepository ratingRepository;
    UserService userService;
    BookingService bookingService;

    @Override
    public RatingResponse saveRating(Long hotelId, Long userId, AddRatingRequest request) {
        Rating rating = createRating(request);
        BookedRoom bookedRoom = bookingService.findRoomUserHasBookedAndNotComment(hotelId, userId);
        handleUserRating(userId, rating);
        handleBookingRating(rating, bookedRoom);
        ratingRepository.save(rating);
        return RatingMapper.toRatingResponse(rating);
    }

    private Rating createRating(AddRatingRequest request) {
        Rating rating = new Rating();
        rating.setStar(request.getStar());
        rating.setComment(request.getComment());
        rating.setRateDay(request.getRateDay());
        return rating;
    }

    private void handleUserRating(Long userId, Rating rating) {
        User user = userService.getUserById(userId);
        user.addComment(rating);
    }

    private void handleBookingRating(Rating rating, BookedRoom bookedRoom) {
        BookedRoom saveBookedRoom = bookingService.findBookingById(bookedRoom.getId());
        saveBookedRoom.addComment(rating);
    }

    @Override
    public RatingResponse updateRating(Long ratingId, UpdateRatingRequest request) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new NoSuchElementException("Rating not found with ID: " + ratingId));
        rating.setStar(request.getStar());
        rating.setComment(request.getComment());
        ratingRepository.save(rating);
        return RatingMapper.toRatingResponse(rating);
    }

    @Override
    public void deleteRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    @Override
    public String checkIfUserHaveBookedRoomInSpecificHotelAndNotCommentInThatBookedRoom(Long userId, Long hotelId) {
        BookedRoom bookedRoom = bookingService.findRoomUserHasBookedAndNotComment(hotelId, userId);

        if (bookedRoom != null) {
            return bookedRoom.getRoom().getRoomType();
        } else {
            return "No booked room found without a comment for this user in the specified hotel.";
        }
    }

    @Override
    public List<RatingResponse> getAllRatingByHotelId(Long hotelId) {
        List<Rating> ratingList = ratingRepository.getAllRatingByHotelId(hotelId);
        return RatingMapper.ratingResponses(ratingList);
    }

    @Override
    public List<Rating> getAllRatingByRoomId(Long roomId) {
        return ratingRepository.getAllRatingByRoomId(roomId);
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
