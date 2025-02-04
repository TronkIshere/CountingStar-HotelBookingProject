package com.example.CoutingStarHotel.services.impl;

import com.example.CoutingStarHotel.entities.BookedRoom;
import com.example.CoutingStarHotel.entities.RedeemedDiscount;
import com.example.CoutingStarHotel.entities.Room;
import com.example.CoutingStarHotel.entities.User;
import com.example.CoutingStarHotel.exception.InvalidBookingRequestException;
import com.example.CoutingStarHotel.exception.ResourceNotFoundException;
import com.example.CoutingStarHotel.repositories.BookingRepository;
import com.example.CoutingStarHotel.repositories.RedeemedDiscountRepository;
import com.example.CoutingStarHotel.services.BookingService;
import com.example.CoutingStarHotel.services.RedeemedDiscountService;
import com.example.CoutingStarHotel.services.RoomService;
import com.example.CoutingStarHotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final UserService userService;
    private final RedeemedDiscountService redeemedDiscountService;
    private final RedeemedDiscountRepository redeemedDiscountRepository;

    @Override
    public Page<BookedRoom> getAllBookings(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return bookingRepository.findAll(pageable);
    }
    @Override
    public void cancelBooking(Long bookingId) {
        BookedRoom bookedRoom = bookingRepository.findById(bookingId).get();
        bookedRoom.setIsCancelled(true);
        bookingRepository.save(bookedRoom);
    }

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public BookedRoom saveBooking(Long roomId, LocalDate checkInDate, LocalDate checkOutDate, String guestFullName, String guestEmail, int numOfAdults, int numOfChildren, int totalNumOfGuest, String guestPhoneNumber, Long userId, Long redeemedDiscountId) {
        try {
            BookedRoom bookedRoom = createBookedRoom(checkInDate, checkOutDate, guestFullName, guestEmail, numOfAdults, numOfChildren, totalNumOfGuest, guestPhoneNumber);
            validateBookingDates(bookedRoom);
            Room room = getRoom(roomId);
            handleRoomBooking(roomId, bookedRoom);
            if (userId != null) {
                handleUserBooking(userId, bookedRoom);
            }
            if (redeemedDiscountId != null) {
                BigDecimal totalAmount = calculateTotalAmount(bookedRoom, room.getRoomPrice());
                totalAmount = applyDiscountIfValid(redeemedDiscountId, totalAmount, bookedRoom);
                bookedRoom.setTotalAmount(totalAmount);
            }
            bookedRoom.setBookingDay(LocalDate.now());
            return bookingRepository.save(bookedRoom);
        } catch (InvalidBookingRequestException e) {
            throw new InvalidBookingRequestException("Có lỗi trong việc đặt phòng!" + e.getMessage());
        }
    }

    private void handleRoomBooking(Long roomId, BookedRoom bookedRoom) {
        Room room = getRoom(roomId);
        room.addBooking(bookedRoom);
        bookedRoom.setRoom(room);
    }

    private BookedRoom createBookedRoom(LocalDate checkInDate, LocalDate checkOutDate, String guestFullName, String guestEmail,
                                        int numOfAdults, int numOfChildren, int totalNumOfGuest, String guestPhoneNumber) {
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setCheckInDate(checkInDate);
        bookedRoom.setCheckOutDate(checkOutDate);
        bookedRoom.setGuestFullName(guestFullName);
        bookedRoom.setGuestEmail(guestEmail);
        bookedRoom.setNumOfAdults(numOfAdults);
        bookedRoom.setNumOfChildren(numOfChildren);
        bookedRoom.setTotalNumOfGuest(totalNumOfGuest);
        bookedRoom.setGuestPhoneNumber(guestPhoneNumber);
        validateBookingDates(bookedRoom);
        bookedRoom.setBookingDay(LocalDate.now());
        return bookedRoom;
    }

    private void validateBookingDates(BookedRoom bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
            throw new InvalidBookingRequestException("Check-in date must come before check-out date");
        }
    }

    private Room getRoom(Long roomId) {
        return roomService.getRoomById(roomId).orElseThrow(() ->
                new InvalidBookingRequestException("Room not found"));
    }

    private void validateRoomAvailability(BookedRoom bookingRequest, Room room) {
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, room.getBookings());
        if (!roomIsAvailable) {
            throw new InvalidBookingRequestException("Phòng hiện đã đặt trong khoảng thời gian này, xin hãy đặt trong khoảng thời gian khác!");
        }
        room.addBooking(bookingRequest);
    }

    private BigDecimal calculateTotalAmount(BookedRoom bookingRequest, BigDecimal roomPrice) {
        return getBookedRoomTotalAmount(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), roomPrice);
    }

    private BigDecimal applyDiscountIfValid(Long redeemedDiscountId, BigDecimal totalAmount, BookedRoom bookingRequest) {
        RedeemedDiscount redeemedDiscount = redeemedDiscountService.findRedeemedDiscountById(redeemedDiscountId);
        if (redeemedDiscount.isUsed()) {
            throw new InvalidBookingRequestException("Mã giảm giá đã được sử dụng, hãy chọn cái khác!");
        }

        return handleDiscountAndReturnTotalAmount(redeemedDiscount, totalAmount, bookingRequest);
    }

    private BigDecimal getBookedRoomTotalAmount(LocalDate checkInDate, LocalDate checkOutDate, BigDecimal roomPrice){
        long bookingDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        BigDecimal daysBigDecimal = BigDecimal.valueOf(bookingDays);
        BigDecimal totalAmount = daysBigDecimal.multiply(roomPrice);
        return totalAmount;
    }

    private BigDecimal handleDiscountAndReturnTotalAmount(RedeemedDiscount redeemedDiscount, BigDecimal totalAmount, BookedRoom bookingRequest){
        BigDecimal discountPercent = BigDecimal.valueOf(redeemedDiscount.getDiscount().getPercentDiscount());
        BigDecimal discountAmount = totalAmount.multiply(discountPercent).divide(BigDecimal.valueOf(100));
        totalAmount = totalAmount.subtract(discountAmount);
        redeemedDiscount.setUsed(true);
        bookingRequest.addDiscount(redeemedDiscount);
        redeemedDiscountRepository.save(redeemedDiscount);
        return totalAmount;
    }

    private void handleUserBooking(Long userId, BookedRoom bookingRequest){
        User user = userService.getUserById(userId).get();
        user.addBooking(bookingRequest);
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(confirmationCode)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with booking code :"+confirmationCode));
    }

    @Override
    public Page<BookedRoom> getBookingsByUserId(Integer pageNo, Integer pageSize, Long userId) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return bookingRepository.findByUserId(pageable, userId);
    }

    @Override
    public Page<BookedRoom> getAllBookingsByHotelId(Integer pageNo, Integer pageSize, Long hotelId) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return bookingRepository.findByHotelId(pageable, hotelId);
    }

    @Override
    public BookedRoom findByBookingId(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with bookingId :"+ bookingId));
    }

    @Override
    public BookedRoom updateBooked(Long bookingId, LocalDate checkInDate, LocalDate checkOutDate, String guestEmail, String guestPhoneNumber, String guestFullName, int totalNumOfGuest) {
        BookedRoom bookedRoom = bookingRepository.findById(bookingId).get();
        bookedRoom.setCheckInDate(checkInDate);
        bookedRoom.setCheckOutDate(checkOutDate);
        bookedRoom.setGuestEmail(guestEmail);
        bookedRoom.setGuestPhoneNumber(guestPhoneNumber);
        bookedRoom.setGuestFullName(guestFullName);
        bookedRoom.setTotalNumOfGuest(totalNumOfGuest);
        bookedRoom.setBookingDay(LocalDate.now());
        return bookingRepository.save(bookedRoom);
    }

    @Override
    public int getTotalNumberOfBookedRooms() {
        return bookingRepository.getTotalNumberOfBookedRooms();
    }

    @Override
    public double getPercentageOfBookedRoomsIncreasedDuringTheMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfThisMonth = today.withDayOfMonth(1);
        LocalDate firstDayOfNextMonth = firstDayOfThisMonth.plusMonths(1);

        int totalBookedRooms = bookingRepository.getTotalNumberOfBookedRooms();
        int BookedRoomsAddedThisMonth = bookingRepository.getBookedRoomsAddedDuringPeriod(firstDayOfThisMonth, firstDayOfNextMonth);

        return (BookedRoomsAddedThisMonth * 100.0) / totalBookedRooms;
    }

    @Override
    public Page<BookedRoom> getAllBookingByKeywordAndHotelId(Integer pageNo, Integer pageSize, Long hotelId, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return bookingRepository.getAllBookingByKeywordAndHotelId(pageable, hotelId, keyword);
    }

    private boolean getAllRoomAndCheckRoomIsAvailable(Room room, BookedRoom bookingRequest) {
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
        return roomIsAvailable;
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
