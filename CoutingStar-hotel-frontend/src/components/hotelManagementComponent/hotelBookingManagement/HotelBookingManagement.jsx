import React, { useEffect, useState } from "react";
import "./hotelBookingManagement.css";
import {
  getBookingByHotelId,
  getAllBookingByKeywordAndHotelId,
} from "../../../components/utils/ApiFunction";
import { useParams } from "react-router-dom";
import DeleteBooking from "./deleteBooking/DeleteBooking";
import UpdateBooking from "./updateBooking/UpdateBooking";

const HotelBookingManagement = () => {
  const [bookings, setBookings] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const { hotelId } = useParams();
  const [currentBookingId, setBookingId] = useState(null);
  const [isUpdateModalOpen, setUpdateModalOpen] = useState(false);
  const [isDeleteModalOpen, setDeleteModalOpen] = useState(false);

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

  const handleEditClick = (id) => {
    setBookingId(id);
    setUpdateModalOpen(true);
  };

  const handleDeleteClick = (id) => {
    setBookingId(id);
    setDeleteModalOpen(true);
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
                    onClick={() => handleEditClick(booking.bookingId)}
                  >
                    Chỉnh sửa
                  </button>
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() => handleDeleteClick(booking.bookingId)}
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
      {errorMessage && <p className="error">{errorMessage}</p>}

      {isUpdateModalOpen && (
        <UpdateBooking
          bookingId={currentBookingId}
          onClose={() => setUpdateModalOpen(false)}
        />
      )}
      {isDeleteModalOpen && (
        <DeleteBooking
          bookingId={currentBookingId}
          onClose={() => setDeleteModalOpen(false)}
        />
      )}
    </div>
  );
};

export default HotelBookingManagement;
