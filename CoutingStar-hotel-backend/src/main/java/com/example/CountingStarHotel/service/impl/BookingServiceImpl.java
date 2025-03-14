package com.example.CountingStarHotel.service.impl;

import com.example.CountingStarHotel.DTO.request.bookedroom.SaveBookingRequest;
import com.example.CountingStarHotel.DTO.request.bookedroom.UpdateBookedRoom;
import com.example.CountingStarHotel.DTO.response.bookedroom.BookingResponse;
import com.example.CountingStarHotel.DTO.response.common.PageResponse;
import com.example.CountingStarHotel.entity.BookedRoom;
import com.example.CountingStarHotel.entity.RedeemedDiscount;
import com.example.CountingStarHotel.entity.Room;
import com.example.CountingStarHotel.entity.User;
import com.example.CountingStarHotel.exception.ApplicationException;
import com.example.CountingStarHotel.exception.ErrorCode;
import com.example.CountingStarHotel.mapper.BookedRoomMapper;
import com.example.CountingStarHotel.repository.BookingRepository;
import com.example.CountingStarHotel.repository.RedeemedDiscountRepository;
import com.example.CountingStarHotel.service.BookingService;
import com.example.CountingStarHotel.service.RedeemedDiscountService;
import com.example.CountingStarHotel.service.RoomService;
import com.example.CountingStarHotel.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingServiceImpl implements BookingService {
    BookingRepository bookingRepository;
    RoomService roomService;
    UserService userService;
    RedeemedDiscountService redeemedDiscountService;
    RedeemedDiscountRepository redeemedDiscountRepository;

    @Override
    public PageResponse<BookingResponse> getAllBookings(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<BookedRoom> bookedRoomPage = bookingRepository.findAll(pageable);

        List<BookedRoom> bookedRoomList = bookedRoomPage.getContent();

        return PageResponse.<BookingResponse>builder()
                .currentPage(pageNo)
                .pageSize(pageable.getPageSize())
                .totalPages(bookedRoomPage.getTotalPages())
                .totalElements(bookedRoomPage.getTotalElements())
                .data(BookedRoomMapper.bookingResponses(bookedRoomList))
                .build();
    }

    @Override
    public void cancelBooking(Long bookingId) {
        BookedRoom bookedRoom = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID: " + bookingId));

        bookedRoom.setIsCancelled(true);
        bookingRepository.save(bookedRoom);
    }

    @Override
    public BookingResponse saveBooking(Long roomId, SaveBookingRequest request, Long userId, Long redeemedDiscountId) {
        try {
            BookedRoom bookedRoom = createBookedRoom(request);
            validateBookingDates(bookedRoom);
            handleRoomBooking(roomId, bookedRoom);
            handleUserBooking(userId, bookedRoom);
            applyDiscountIfValid(redeemedDiscountId, bookedRoom, roomId);
            bookingRepository.save(bookedRoom);
            return BookedRoomMapper.toBookingResponse(bookedRoom);
        } catch (ApplicationException e) {
            throw new ApplicationException(ErrorCode.INVALID_BOOKING_REQUEST);
        }
    }

    private void applyDiscountIfValid(Long redeemedDiscountId, BookedRoom bookedRoom, Long roomId) {
        if (redeemedDiscountId == null) return;
        Room room = roomService.getRoomById(roomId);
        BigDecimal totalAmount = calculateTotalAmount(bookedRoom, room.getRoomPrice());
        totalAmount = applyDiscountIfValid(redeemedDiscountId, totalAmount, bookedRoom);
        bookedRoom.setTotalAmount(totalAmount);
    }

    private BookedRoom createBookedRoom(SaveBookingRequest request) {
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setCheckInDate(request.getCheckInDate());
        bookedRoom.setCheckOutDate(request.getCheckOutDate());
        bookedRoom.setGuestFullName(request.getGuestFullName());
        bookedRoom.setGuestEmail(request.getGuestEmail());
        bookedRoom.setNumOfAdults(request.getNumOfAdults());
        bookedRoom.setNumOfChildren(request.getNumOfChildren());
        bookedRoom.setTotalNumOfGuest(request.getTotalNumOfGuest());
        bookedRoom.setGuestPhoneNumber(request.getGuestPhoneNumber());
        validateBookingDates(bookedRoom);
        bookedRoom.setBookingDay(LocalDate.now());
        return bookedRoom;
    }

    private void validateBookingDates(BookedRoom bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
            throw new ApplicationException(ErrorCode.INVALID_BOOKING_REQUEST);
        }
    }

    private void handleRoomBooking(Long roomId, BookedRoom bookedRoom) {
        Room room = roomService.getRoomById(roomId);
        room.addBooking(bookedRoom);
        bookedRoom.setRoom(room);
    }

    private void handleUserBooking(Long userId, BookedRoom bookingRequest) {
        if (userId == null) return;
        User user = userService.getUserById(userId);
        user.addBooking(bookingRequest);
    }

    private BigDecimal calculateTotalAmount(BookedRoom bookingRequest, BigDecimal roomPrice) {
        return getBookedRoomTotalAmount(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(), roomPrice);
    }

    private BigDecimal applyDiscountIfValid(Long redeemedDiscountId, BigDecimal totalAmount, BookedRoom bookingRequest) {
        RedeemedDiscount redeemedDiscount = redeemedDiscountService.findRedeemedDiscountById(redeemedDiscountId);
        if (redeemedDiscount.isUsed()) {
            throw new ApplicationException(ErrorCode.INVALID_DISCOUNT_REQUEST);
        }

        return handleDiscountAndReturnTotalAmount(redeemedDiscount, totalAmount, bookingRequest);
    }

    private BigDecimal getBookedRoomTotalAmount(LocalDate checkInDate, LocalDate checkOutDate, BigDecimal roomPrice) {
        long bookingDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        BigDecimal daysBigDecimal = BigDecimal.valueOf(bookingDays);
        return daysBigDecimal.multiply(roomPrice);
    }

    private BigDecimal handleDiscountAndReturnTotalAmount(RedeemedDiscount redeemedDiscount, BigDecimal totalAmount, BookedRoom bookingRequest) {
        BigDecimal discountPercent = BigDecimal.valueOf(redeemedDiscount.getDiscount().getPercentDiscount());
        BigDecimal discountAmount = totalAmount.multiply(discountPercent)
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        totalAmount = totalAmount.subtract(discountAmount);
        redeemedDiscount.setUsed(true);
        bookingRequest.addDiscount(redeemedDiscount);
        redeemedDiscountRepository.save(redeemedDiscount);
        return totalAmount;
    }

    @Override
    public BookingResponse findByBookingConfirmationCode(String confirmationCode) {
        BookedRoom bookedRoom = bookingRepository.findByBookingConfirmationCode(confirmationCode)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_BOOKING_REQUEST));

        return BookedRoomMapper.toBookingResponse(bookedRoom);
    }

    @Override
    public PageResponse<BookingResponse> getBookingsByUserId(Integer pageNo, Integer pageSize, Long userId) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<BookedRoom> bookedRoomPage = bookingRepository.findByUserId(pageable, userId);

        List<BookedRoom> bookedRoomList = bookedRoomPage.getContent();

        return PageResponse.<BookingResponse>builder()
                .currentPage(pageNo)
                .pageSize((pageable.getPageSize()))
                .totalPages(bookedRoomPage.getTotalPages())
                .totalElements(bookedRoomPage.getTotalElements())
                .data(BookedRoomMapper.bookingResponses(bookedRoomList))
                .build();
    }

    @Override
    public PageResponse<BookingResponse> getAllBookingsByHotelId(Integer pageNo, Integer pageSize, Long hotelId) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<BookedRoom> bookedRoomPage = bookingRepository.findByHotelId(pageable, hotelId);

        List<BookedRoom> bookedRoomList = bookedRoomPage.getContent();

        return PageResponse.<BookingResponse>builder()
                .currentPage(pageNo)
                .pageSize(pageable.getPageSize())
                .totalPages(bookedRoomPage.getTotalPages())
                .totalElements(bookedRoomPage.getTotalElements())
                .data(BookedRoomMapper.bookingResponses(bookedRoomList))
                .build();
    }

    @Override
    public BookingResponse findBookingResponseById(Long id) {
        BookedRoom bookedRoom = bookingRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_BOOKING_REQUEST));
        return BookedRoomMapper.toBookingResponse(bookedRoom);
    }

    @Override
    public BookedRoom findBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_BOOKING_REQUEST));
    }

    @Override
    public BookingResponse updateBooked(Long id, UpdateBookedRoom request) {
        BookedRoom bookedRoom = findBookingById(id);
        bookedRoom.setCheckInDate(request.getCheckInDate());
        bookedRoom.setCheckOutDate(request.getCheckOutDate());
        bookedRoom.setGuestEmail(request.getGuestEmail());
        bookedRoom.setGuestPhoneNumber(request.getGuestPhoneNumber());
        bookedRoom.setGuestFullName(request.getGuestFullName());
        bookedRoom.setTotalNumOfGuest(request.getTotalNumOfGuest());
        bookedRoom.setBookingDay(LocalDate.now());
        bookingRepository.save(bookedRoom);
        return BookedRoomMapper.toBookingResponse(bookedRoom);
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
    public PageResponse<BookingResponse> getAllBookingByKeywordAndHotelId(Integer pageNo, Integer pageSize, Long hotelId, String keyword) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<BookedRoom> bookedRoomPage = bookingRepository.getAllBookingByKeywordAndHotelId(pageable, hotelId, keyword);

        List<BookedRoom> bookedRoomList = bookedRoomPage.getContent();

        return PageResponse.<BookingResponse>builder()
                .currentPage(pageNo)
                .pageSize(pageable.getPageSize())
                .totalPages(bookedRoomPage.getTotalPages())
                .totalElements(bookedRoomPage.getTotalElements())
                .data(BookedRoomMapper.bookingResponses(bookedRoomList))
                .build();
    }

    @Override
    public BookedRoom findRoomUserHasBookedAndNotComment(Long hotelId, Long userId) {
        Pageable pageable = PageRequest.of(0, 1);
        List<BookedRoom> bookedRooms = bookingRepository.findRoomsUserHasBookedAndNotComment(hotelId, userId, pageable);

        if (bookedRooms.isEmpty()) {
            throw new RuntimeException("No booked room found for user without a rating in the specified hotel");
        }

        return bookedRooms.get(0);
    }

    @Override
    public String softDelete(Long bookingId) {
        BookedRoom bookedRoom = findBookingById(bookingId);
        LocalDateTime deletedAt = LocalDateTime.now();
        bookedRoom.setDeletedAt(deletedAt);
        bookingRepository.save(bookedRoom);
        return "BookedRoom with ID " + bookingId + " has been soft deleted at " + deletedAt;
    }
}
