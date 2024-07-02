import React, { useState } from "react";
import "./hotelBookingManagement.css";
import BookingDelete from "../../components/bookingRoom/bookingDelete/BookingDelete";
import BookingUpdate from "../../components/bookingRoom/bookingUpdate/BookingUpdate";

const HotelBookingManagement = () => {
  const [bookings, setBookings] = useState([
    {
      bookingId: 1,
      roomId: 101,
      roomType: "Deluxe",
      checkInDate: "2024-07-01",
      checkOutDate: "2024-07-05",
      confirmationCode: "ABC123",
      email: "example1@example.com",
      phoneNumber: "1234567890",
      guestName: "John Doe",
      totalPeople: 2,
    },
    {
      bookingId: 2,
      roomId: 102,
      roomType: "Standard",
      checkInDate: "2024-07-02",
      checkOutDate: "2024-07-06",
      confirmationCode: "DEF456",
      email: "example2@example.com",
      phoneNumber: "0987654321",
      guestName: "Jane Smith",
      totalPeople: 1,
    },
    // ... more bookings
  ]);

  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
  const [selectedBooking, setSelectedBooking] = useState(null);

  const handleOpenDeleteModal = (booking) => {
    setSelectedBooking(booking);
    setIsDeleteModalOpen(true);
  };

  const handleCloseDeleteModal = () => {
    setIsDeleteModalOpen(false);
  };

  const handleDeleteBooking = (bookingToDelete) => {
    setBookings(bookings.filter(booking => booking.bookingId !== bookingToDelete.bookingId));
    setIsDeleteModalOpen(false);
  };

  const handleOpenUpdateModal = (booking) => {
    setSelectedBooking(booking);
    setIsUpdateModalOpen(true);
  };

  const handleCloseUpdateModal = () => {
    setIsUpdateModalOpen(false);
  };

  const handleUpdateBooking = (updatedBooking) => {
    setBookings(bookings.map(booking =>
      booking.bookingId === updatedBooking.bookingId ? updatedBooking : booking
    ));
    setIsUpdateModalOpen(false);
  };

  return (
    <div className="bookingListContainer">
      <h1>Hotel Booking Management</h1>
      <table className="bookingListTable">
        <thead>
          <tr>
            <th>ID Đặt Phòng</th>
            <th>ID Phòng</th>
            <th>Loại Phòng</th>
            <th>Ngày Nhận Phòng</th>
            <th>Ngày Trả Phòng</th>
            <th>Mã Xác Nhận</th>
            <th>Email Đăng Ký</th>
            <th>Số Điện Thoại</th>
            <th>Tên Người Đặt</th>
            <th>Tổng Số Người</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {bookings.map((booking, index) => (
            <tr key={index}>
              <td>{booking.bookingId}</td>
              <td>{booking.roomId}</td>
              <td>{booking.roomType}</td>
              <td>{booking.checkInDate}</td>
              <td>{booking.checkOutDate}</td>
              <td>{booking.confirmationCode}</td>
              <td>{booking.email}</td>
              <td>{booking.phoneNumber}</td>
              <td>{booking.guestName}</td>
              <td>{booking.totalPeople}</td>
              <td>
                <button className="updateButton" onClick={() => handleOpenUpdateModal(booking)}>Chỉnh sửa</button>
                <button className="deleteButton" onClick={() => handleOpenDeleteModal(booking)}>Xóa</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {isDeleteModalOpen && selectedBooking && (
        <BookingDelete
          booking={selectedBooking}
          handleDeleteBooking={handleDeleteBooking}
          onClose={handleCloseDeleteModal}
        />
      )}
      {isUpdateModalOpen && selectedBooking && (
        <BookingUpdate
          booking={selectedBooking}
          handleUpdateBooking={handleUpdateBooking}
          onClose={handleCloseUpdateModal}
        />
      )}
    </div>
  );
};

export default HotelBookingManagement;
