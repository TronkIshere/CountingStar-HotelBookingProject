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
          <h2>Confirm room deletion</h2>
          <span className="close" onClick={onClose}>&times;</span>
        </div>
        <div className="modalBody">
          <p>Are you sure you want to delete your booking?</p>
        </div>
        <div className="modalFooter">
          <button className="deleteButton" onClick={handleDeleteBookingById}>Delete</button>
          <button className="cancelButton" onClick={onClose}>Cancle</button>
        </div>
      </div>
    </div>
  );
};

export default BookingDelete;
