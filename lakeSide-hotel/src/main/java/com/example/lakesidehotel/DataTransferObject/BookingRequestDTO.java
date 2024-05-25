package com.example.lakesidehotel.DataTransferObject;

import com.example.lakesidehotel.model.BookedRoom;

public class BookingRequestDTO {
    private Long userId;
    private BookedRoom booking;

    // Getters and setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BookedRoom getBooking() {
        return booking;
    }

    public void setBooking(BookedRoom booking) {
        this.booking = booking;
    }
}
