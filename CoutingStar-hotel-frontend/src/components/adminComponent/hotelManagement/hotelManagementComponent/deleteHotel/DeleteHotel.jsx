import React, { useState } from "react";
import "./deleteHotel.css";
import { toast } from "react-toastify";
import { deleteHotel } from "../../../../utils/ApiFunction";

const DeleteHotel = ({ hotelId }) => {
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleDelete = async () => {
    try {
      await deleteHotel(hotelId);
      setSuccessMessage("Khách sạn đã được xóa thành công!");
      setErrorMessage("");
      toast.success("Khách sạn đã được xóa thành công!");
    } catch (error) {
      setErrorMessage("Lỗi khi xóa khách sạn.");
      setSuccessMessage("");
      toast.error("Lỗi khi xóa khách sạn.");
    }
  };

  return (
    <div
      className="deleteHotel modal fade"
      id="deleteHotelModal"
      tabIndex="-1"
      aria-labelledby="deleteHotelModalLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="deleteHotelModalLabel">
              Xóa khách sạn
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>

          <div className="modal-body">
            <h3>Bạn có chắc muốn xóa khách sạn với Id là {hotelId} không!</h3>
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

          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Đóng
            </button>
            <button
              type="button"
              className="btn btn-danger"
              onClick={handleDelete}
            >
              Xóa
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DeleteHotel;
