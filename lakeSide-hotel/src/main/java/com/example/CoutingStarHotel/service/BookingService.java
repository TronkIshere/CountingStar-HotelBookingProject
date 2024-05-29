package com.example.CoutingStarHotel.service;

import com.example.CoutingStarHotel.exeption.InvalidBookingRequestException;
import com.example.CoutingStarHotel.exeption.ResourceNotFoundException;
import com.example.CoutingStarHotel.model.BookedRoom;
import com.example.CoutingStarHotel.model.Room;
import com.example.CoutingStarHotel.model.User;
import com.example.CoutingStarHotel.repository.BookingRepository;
import com.example.CoutingStarHotel.repository.UserRepository;
import com.example.CoutingStarHotel.security.user.CurrentHotelUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService{
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final IRoomService roomService;
    private final IUserService userService;
    @Autowired
    private CurrentHotelUserDetails currentHotelUserDetails;


    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }
    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }

        //Long userId = currentHotelUserDetails.getCurrentUserId();
        Long userId = userService.getCurrentUserId();
        System.out.println("User ID: " + userId);

        Room room = roomService.getRoomById(roomId).get();
        User user = userService.getUserById(userId).get();

        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
        if (roomIsAvailable){
            room.addBooking(bookingRequest);
            user.addBooking(bookingRequest);
            bookingRepository.save(bookingRequest);
        } else {
            throw new InvalidBookingRequestException("Sorry, This room is not available for the selected dates;");
        }
        return bookingRequest.getBookingConfirmationCode();
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with booking code :"+confirmationCode));
    }

    @Override
    public List<BookedRoom> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }


}
