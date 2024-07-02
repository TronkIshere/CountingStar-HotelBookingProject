import React from "react";
import "./bookingDelete.css";

const BookingDelete = ({ booking, handleDeleteBooking, onClose }) => {
  return (
    <div className="modal">
      <div className="modalContent">
        <div className="modalHeader">
          <h2>Xác nhận xóa đặt phòng</h2>
          <span className="close" onClick={onClose}>&times;</span>
        </div>
        <div className="modalBody">
          <p>Bạn có chắc chắn muốn xóa đặt phòng <strong>{booking.bookingId}</strong> không?</p>
        </div>
        <div className="modalFooter">
          <button className="deleteButton" onClick={() => handleDeleteBooking(booking)}>Xóa</button>
          <button className="cancelButton" onClick={onClose}>Hủy</button>
        </div>
      </div>
    </div>
  );
};

export default BookingDelete;
