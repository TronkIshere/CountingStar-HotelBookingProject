import React, { useState } from "react";
import "./bookingUpdate.css";

const BookingUpdate = ({ booking, handleUpdateBooking, onClose }) => {
  const [updatedBooking, setUpdatedBooking] = useState({ ...booking });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUpdatedBooking((prevBooking) => ({
      ...prevBooking,
      [name]: value,
    }));
  };

  const handleSubmit = () => {
    handleUpdateBooking(updatedBooking);
    onClose();
  };

  return (
    <div className="modal">
      <div className="modalContent">
        <div className="modalHeader">
          <h2>Chỉnh sửa thông tin đặt phòng</h2>
          <span className="close" onClick={onClose}>&times;</span>
        </div>
        <div className="modalBody">
          <input
            type="date"
            name="checkInDate"
            value={updatedBooking.checkInDate}
            onChange={handleChange}
          />
          <input
            type="date"
            name="checkOutDate"
            value={updatedBooking.checkOutDate}
            onChange={handleChange}
          />
          <input
            type="text"
            name="confirmationCode"
            placeholder="Mã xác nhận"
            value={updatedBooking.confirmationCode}
            onChange={handleChange}
          />
          <input
            type="email"
            name="email"
            placeholder="Email đăng ký"
            value={updatedBooking.email}
            onChange={handleChange}
          />
          <input
            type="tel"
            name="phoneNumber"
            placeholder="Số điện thoại"
            value={updatedBooking.phoneNumber}
            onChange={handleChange}
          />
          <input
            type="text"
            name="guestName"
            placeholder="Tên người đặt"
            value={updatedBooking.guestName}
            onChange={handleChange}
          />
          <input
            type="number"
            name="totalPeople"
            placeholder="Tổng số người"
            value={updatedBooking.totalPeople}
            onChange={handleChange}
          />
        </div>
        <div className="modalFooter">
          <button className="updateButton" onClick={handleSubmit}>Cập nhật</button>
          <button className="cancelButton" onClick={onClose}>Hủy</button>
        </div>
      </div>
    </div>
  );
};

export default BookingUpdate;
