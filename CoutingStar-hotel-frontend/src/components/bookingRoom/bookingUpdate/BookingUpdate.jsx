import React, { useEffect, useState } from "react";
import "./bookingUpdate.css";
import { getBookingByBookingId, updateBooking } from "../../utils/ApiFunction";
import moment from "moment/moment";

const BookingUpdate = ({ bookingId, handleUpdateBooking, onClose }) => {
  const [booking, setBooking] = useState([
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

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const response = await getBookingByBookingId(bookingId);
        setBooking(response);
      } catch (error) {
        console.error("Error fetching bookings:", error.message);
        setErrorMessage(error.message);
      }
    };

    fetchBookings();
  }, [bookingId]);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setBooking({ ...booking, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await updateBooking(bookingId, booking);
      console.log(response);
      if (response.status === 200) {
        setSuccessMessage("Chỉnh sửa phòng thành công!!!");
        const updatedBookingData = await getBookingByBookingId(BookingId);
        setBooking(updatedBookingData);
        handleUpdateBooking(updatedBookingData);
        setErrorMessage("");
      } else {
        setErrorMessage("Đã xảy ra lỗi!!!");
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
  };

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  return (
    <div className="modal">
      <form onSubmit={handleSubmit} className="modalContent">
        <div className="modalHeader">
          <h2>Chỉnh sửa thông tin đặt phòng</h2>
          <span className="close" onClick={onClose}>
            &times;
          </span>
        </div>
        <div className="modalBody">
          <label>
            Ngày nhận phòng
            <input
              type="date"
              name="checkInDate"
              value={moment(booking.checkInDate).format("YYYY-MM-DD")}
              onChange={handleInputChange}
            />
          </label>
          <label>
            Ngày trả phòng
            <input
              type="date"
              name="checkOutDate"
              value={moment(booking.checkOutDate).format("YYYY-MM-DD")}
              onChange={handleInputChange}
            />
          </label>
          <label>
            Email đăng ký
            <input
              type="email"
              name="guestEmail"
              placeholder="Email đăng ký"
              value={booking.guestEmail}
              onChange={handleInputChange}
            />
          </label>
          <label>
            Số điện thoại
            <input
              type="tel"
              name="guestPhoneNumber"
              placeholder="Số điện thoại"
              value={booking.guestPhoneNumber}
              onChange={handleInputChange}
            />
          </label>
          <label>
            Tên người đặt
            <input
              type="text"
              name="guestFullName"
              placeholder="Tên người đặt"
              value={booking.guestFullName}
              onChange={handleInputChange}
            />
          </label>
          <label>
            Tổng số người
            <input
              type="number"
              name="totalNumOfGuest"
              placeholder="Tổng số người"
              value={booking.totalNumOfGuest}
              onChange={handleInputChange}
            />
          </label>
        </div>
        {successMessage && (
          <div className="alert alert-success" role="alert">
            {successMessage}
          </div>
        )}
        {errorMessage && (
          <div className="alert alert-danger" role="alert">
            {errorMessage}
          </div>
        )}
        <div className="modalFooter">
          <button className="updateButton" type="submit">
            Cập nhật
          </button>
          <button className="cancelButton" onClick={onClose}>
            Hủy
          </button>
        </div>
      </form>
    </div>
  );
};

export default BookingUpdate;
