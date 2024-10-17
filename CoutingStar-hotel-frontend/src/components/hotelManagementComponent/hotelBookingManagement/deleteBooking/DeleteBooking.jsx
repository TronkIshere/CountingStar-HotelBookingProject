import React, { useEffect, useState } from "react";
import { cancelBooking, getBookingByBookingId } from "../../../utils/ApiFunction";
import "./deleteBooking.css";

const DeleteBooking = ({ bookingId, onClose }) => {
  const [bookingDetails, setBookingDetails] = useState(null);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  useEffect(() => {
    const fetchBookingDetails = async () => {
      try {
        const response = await getBookingByBookingId(bookingId);
        setBookingDetails(response);
      } catch (error) {
        console.error("Error fetching booking details:", error);
        setErrorMessage(error.message);
      }
    };

    fetchBookingDetails();
  }, [bookingId]);

  const handleDelete = async () => {
    try {
      const response = await cancelBooking(bookingId);
      if (response.status === 200) {
        setSuccessMessage("Xóa đặt phòng thành công!");
        setTimeout(() => {
          onClose();
        }, 2000);
      } else {
        setErrorMessage("Đã xảy ra lỗi trong quá trình xóa!");
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
  };

  return (
    <div className="deleteBooking modal fade show" style={{ display: "block" }}>
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Xóa đặt phòng</h5>
            <button
              type="button"
              className="btn-close"
              onClick={onClose}
              aria-label="Close"
            ></button>
          </div>
          <div className="modal-body">
            {bookingDetails && (
              <p>
                Bạn có chắc chắn muốn xóa đặt phòng với ID{" "}
                <strong>{bookingDetails.bookingId}</strong> -{" "}
                <strong>{bookingDetails.guestFullName}</strong> không?
              </p>
            )}
            {errorMessage && <p className="error">{errorMessage}</p>}
            {successMessage && <p className="success">{successMessage}</p>}
          </div>
          <div className="modal-footer">
            <button className="btn btn-danger" onClick={handleDelete}>
              Xóa
            </button>
            <button className="btn btn-secondary" onClick={onClose}>
              Đóng
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DeleteBooking;
