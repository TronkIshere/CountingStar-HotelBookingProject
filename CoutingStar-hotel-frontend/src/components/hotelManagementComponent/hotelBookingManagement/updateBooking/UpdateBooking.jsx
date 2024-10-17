import React, { useEffect, useState } from "react";
import {
  getBookingByBookingId,
  updateBooking,
} from "../../../utils/ApiFunction";
import moment from "moment";
import "./updateBooking.css";

const UpdateBooking = ({ bookingId, onClose }) => {
  const [booking, setBooking] = useState({
    bookingId: "",
    checkInDate: "",
    checkOutDate: "",
    bookingConfirmationCode: "",
    totalNumOfGuest: "",
    room: { id: "", roomType: "" },
    guestEmail: "",
    guestPhoneNumber: "",
    guestFullName: "",
  });
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  useEffect(() => {
    const fetchBookingData = async () => {
      try {
        const response = await getBookingByBookingId(bookingId);
        setBooking(response);
      } catch (error) {
        console.error("Error fetching booking:", error);
        setErrorMessage(error.message);
      }
    };
    if (bookingId) {
      fetchBookingData();
    }
  }, [bookingId]);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setBooking({ ...booking, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await updateBooking(bookingId, booking);
      if (response.status === 200) {
        setSuccessMessage("Cập nhật đặt phòng thành công!");
        const updatedBookingData = await getBookingByBookingId(bookingId);
        setBooking(updatedBookingData);
      } else {
        setErrorMessage("Đã xảy ra lỗi trong quá trình cập nhật!");
      }
    } catch (error) {
      setErrorMessage(error.message);
    }

    setTimeout(() => {
      setErrorMessage("");
      setSuccessMessage("");
      onClose(); // Close the modal after submission
    }, 5000);
  };

  return (
    <div className="updateBooking modal fade show" style={{ display: "block" }}>
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Chỉnh sửa đặt phòng</h5>
            <button
              type="button"
              className="btn-close"
              onClick={onClose}
              aria-label="Close"
            ></button>
          </div>
          <div className="modal-body">
            <form onSubmit={handleSubmit}>
              <div className="row mb-3">
                <div className="col-12 col-sm-12 col-md-6 col-lg-6">
                  <label className="form-label">Ngày nhận phòng:</label>
                  <input
                    type="date"
                    className="form-control"
                    name="checkInDate"
                    value={
                      booking.checkInDate
                        ? moment(booking.checkInDate).format("YYYY-MM-DD")
                        : ""
                    }
                    onChange={handleInputChange}
                    required
                  />
                </div>
                <div className="col-12 col-sm-12 col-md-6 col-lg-6">
                  <label className="form-label">Ngày trả phòng:</label>
                  <input
                    type="date"
                    className="form-control"
                    name="checkOutDate"
                    value={
                      booking.checkOutDate
                        ? moment(booking.checkOutDate).format("YYYY-MM-DD")
                        : ""
                    }
                    onChange={handleInputChange}
                    required
                  />
                </div>
              </div>
              <div className="row mb-3">
                <div className="col-12 col-sm-12 col-md-6 col-lg-6">
                  <label className="form-label">Email đăng ký:</label>
                  <input
                    type="email"
                    className="form-control"
                    name="guestEmail"
                    placeholder="Email đăng ký"
                    value={booking.guestEmail}
                    onChange={handleInputChange}
                    required
                  />
                </div>

                <div className="col-12 col-sm-12 col-md-6 col-lg-6">
                  <label className="form-label">Số điện thoại:</label>
                  <input
                    type="tel"
                    className="form-control"
                    name="guestPhoneNumber"
                    placeholder="Số điện thoại"
                    value={booking.guestPhoneNumber}
                    onChange={handleInputChange}
                    required
                  />
                </div>
              </div>
              <div className="row mb-3">
                <div className="col-12 col-sm-12 col-md-6 col-lg-6">
                  <label className="form-label">Tên người đặt:</label>
                  <input
                    type="text"
                    className="form-control"
                    name="guestFullName"
                    placeholder="Tên người đặt"
                    value={booking.guestFullName}
                    onChange={handleInputChange}
                    required
                  />
                </div>

                <div className="col-12 col-sm-12 col-md-6 col-lg-6">
                  <label className="form-label">Tổng số người:</label>
                  <input
                    type="number"
                    className="form-control"
                    name="totalNumOfGuest"
                    value={booking.totalNumOfGuest}
                    onChange={handleInputChange}
                    required
                  />
                </div>
              </div>

              <div className="row mb-3">
                <div className="col-12 col-sm-12 col-md-6 col-lg-6">
                  <label className="form-label">Mã xác nhận:</label>
                  <input
                    type="text"
                    className="form-control"
                    name="bookingConfirmationCode"
                    value={booking.bookingConfirmationCode}
                    onChange={handleInputChange}
                    required
                  />
                </div>

                <div className="col-12 col-sm-12 col-md-6 col-lg-6">
                  <label className="form-label">ID phòng:</label>
                  <input
                    type="text"
                    className="form-control"
                    name="room.id"
                    value={booking.room.id}
                    readOnly
                  />
                </div>
              </div>
              <div className="row mb-3">
                <div className="col-12 col-sm-12 col-md-6 col-lg-6">
                  <label className="form-label">Loại phòng:</label>
                  <input
                    type="text"
                    className="form-control"
                    name="room.roomType"
                    value={booking.room.roomType}
                    readOnly
                  />
                </div>
              </div>

              {errorMessage && <p className="error">{errorMessage}</p>}
              {successMessage && <p className="success">{successMessage}</p>}

              <div className="modal-footer">
                <button type="button" className="white-btn" onClick={onClose}>
                  Đóng
                </button>
                <button type="submit" className="main-btn">
                  Cập nhật
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UpdateBooking;
