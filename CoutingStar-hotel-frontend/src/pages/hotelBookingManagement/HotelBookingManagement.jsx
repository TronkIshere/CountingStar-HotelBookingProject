import React, { useEffect, useState } from "react";
import "./hotelBookingManagement.css";
import BookingDelete from "../../components/bookingRoom/bookingDelete/BookingDelete";
import BookingUpdate from "../../components/bookingRoom/bookingUpdate/BookingUpdate";
import { getBookingByHotelId } from "../../components/utils/ApiFunction";
import { useParams } from "react-router-dom";

const HotelBookingManagement = () => {
  const [bookings, setBookings] = useState([
    {
      bookingId: "",
      checkInDate: "",
      checkOutDate: "",
      bookingConfirmationCode: "",
      totalNumOfGuest: "",

      room: { id: "", roomType: "" },

      guestEmail: "",
      guestPhoneNumber: "",
      guestFullName: "",
    },
  ]);

  const { hotelId } = useParams();

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const response = await getBookingByHotelId(hotelId);
        setBookings(response);
      } catch (error) {
        console.error("Error fetching bookings:", error.message);
        setErrorMessage(error.message);
      }
    };

    fetchBookings();
  }, [hotelId]);

  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
  const [selectedBookingId, setSelectedBookingId] = useState(null);

  const handleOpenDeleteModal = (bookingId) => {
    setSelectedBookingId(bookingId);
    setIsDeleteModalOpen(true);
  };

  const handleCloseDeleteModal = () => {
    setIsDeleteModalOpen(false);
  };

  const handleDeleteBooking = (bookingId) => {
    setBookings(bookings.filter((booking) => booking.bookingId !== bookingId));
    setIsDeleteModalOpen(false);
  };

  const handleOpenUpdateModal = (bookingId) => {
    setSelectedBookingId(bookingId);
    setIsUpdateModalOpen(true);
  };

  const handleCloseUpdateModal = () => {
    setIsUpdateModalOpen(false);
  };

  const handleUpdateBooking = (updatedBooking) => {
    setBookings(
      bookings.map((booking) =>
        booking.bookingId === updatedBooking.bookingId
          ? updatedBooking
          : booking
      )
    );
    setIsUpdateModalOpen(false);
  };

  return (
    <div className="bookingListContainer">
      <h1>Quản lý đặt phòng</h1>
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
              <td>{booking.room.id}</td>
              <td>{booking.room.roomType}</td>
              <td>{booking.checkInDate}</td>
              <td>{booking.checkOutDate}</td>
              <td>{booking.bookingConfirmationCode}</td>
              <td>{booking.guestEmail}</td>
              <td>{booking.guestPhoneNumber}</td>
              <td>{booking.guestFullName}</td>
              <td>{booking.totalNumOfGuest}</td>
              <td>
                <button
                  className="updateButton"
                  onClick={() => handleOpenUpdateModal(booking.bookingId)}
                >
                  Chỉnh sửa
                </button>
                <button
                  className="deleteButton"
                  onClick={() => handleOpenDeleteModal(booking.bookingId)}
                >
                  Xóa
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {isDeleteModalOpen && selectedBookingId && (
        <BookingDelete
          bookingId={selectedBookingId}
          handleDeleteBooking={handleDeleteBooking}
          onClose={handleCloseDeleteModal}
        />
      )}
      {isUpdateModalOpen && selectedBookingId && (
        <BookingUpdate
          bookingId={selectedBookingId}
          handleUpdateBooking={handleUpdateBooking}
          onClose={handleCloseUpdateModal}
        />
      )}
    </div>
  );
};

export default HotelBookingManagement;
