import React, { useEffect, useState } from "react";
import "./hotelBookingManagement.css";
import { getBookingByHotelId, getAllBookingByKeywordAndHotelId } from "../../../components/utils/ApiFunction";
import { useParams } from "react-router-dom";
import DeleteBooking from "./deleteBooking/DeleteBooking";
import UpdateBooking from "./updateBooking/UpdateBooking";

const HotelBookingManagement = () => {
  const [bookings, setBookings] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
  const [selectedBookingId, setSelectedBookingId] = useState(null);
  const [errorMessage, setErrorMessage] = useState("");
  const { hotelId } = useParams();

  const fetchBookings = async (keyword = "") => {
    try {
      let response;
      if (keyword) {
        response = await getAllBookingByKeywordAndHotelId(0, 8, hotelId, keyword);
      } else {
        response = await getBookingByHotelId(0, 8, hotelId);
      }
      setBookings(response.content || []);
    } catch (error) {
      console.error("Error fetching bookings:", error.message);
      setErrorMessage(error.message);
    }
  };

  useEffect(() => {
    fetchBookings();
  }, [hotelId]);

  const handleSearch = () => {
    fetchBookings(searchKeyword);
  };

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
    <div className="bookingManagement">
      <h1>Quản lý đặt phòng</h1>

      <div className="searchBooking-bar">
        <div className="row">
          <div className="col-md-5">
            <div className="mb-3">
              <label htmlFor="booking-keyword" className="form-label">
                Nhập từ khóa tìm đặt phòng:
              </label>
              <input
                type="text"
                className="form-control"
                id="booking-keyword"
                placeholder="Nhập từ khóa"
                value={searchKeyword}
                onChange={(e) => setSearchKeyword(e.target.value)}
              />
            </div>
          </div>
          <div className="col-md-2 d-flex align-items-center">
            <button className="main-btn" onClick={handleSearch}>
              Tìm <i className="bi bi-search"></i>
            </button>
          </div>
        </div>
      </div>

      <table className="table table-striped">
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
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {bookings.length > 0 ? (
            bookings.map((booking) => (
              <tr key={booking.bookingId}>
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
                    className="btn btn-primary btn-sm"
                    onClick={() => handleOpenUpdateModal(booking.bookingId)}
                  >
                    Chỉnh sửa
                  </button>
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() => handleOpenDeleteModal(booking.bookingId)}
                  >
                    Xóa
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="11">Không có dữ liệu đặt phòng</td>
            </tr>
          )}
        </tbody>
      </table>

      {isDeleteModalOpen && selectedBookingId && (
        <DeleteBooking
          bookingId={selectedBookingId}
          handleDeleteBooking={handleDeleteBooking}
          onClose={handleCloseDeleteModal}
        />
      )}
      {isUpdateModalOpen && selectedBookingId && (
        <UpdateBooking
          bookingId={selectedBookingId}
          handleUpdateBooking={handleUpdateBooking}
          onClose={handleCloseUpdateModal}
        />
      )}
      {errorMessage && <p className="error">{errorMessage}</p>}
    </div>
  );
};

export default HotelBookingManagement;
