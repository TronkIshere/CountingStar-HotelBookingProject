import React from "react";
import "./bookingDelete.css";
import { cancelBooking } from "../../utils/ApiFunction";

const BookingDelete = ({ bookingId, handleDeleteBooking, onClose }) => {
  const handleDeleteBookingById = async () => {
    try {
      console.log(bookingId)
      await cancelBooking(bookingId)
      handleDeleteBooking(bookingId)
      onClose()
    } catch (error) {
      console.error("Error deleting room:", error);
    }
    onClose()
  }

  return (
    <div className="modal">
      <div className="modalContent">
        <div className="modalHeader">
          <h2>Xác nhận xóa đặt phòng</h2>
          <span className="close" onClick={onClose}>&times;</span>
        </div>
        <div className="modalBody">
          <p>Bạn có chắc chắn muốn xóa đặt phòng không?</p>
        </div>
        <div className="modalFooter">
          <button className="deleteButton" onClick={handleDeleteBookingById}>Xóa</button>
          <button className="cancelButton" onClick={onClose}>Hủy</button>
        </div>
      </div>
    </div>
  );
};

export default BookingDelete;
