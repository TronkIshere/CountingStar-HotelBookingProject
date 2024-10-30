import React, { useState } from "react";
import "./deleteDiscount.css";
import { toast } from "react-toastify";
import { deleteDiscount } from "../../../../utils/ApiFunction";

const DeleteDiscount = ({ discountId }) => {
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleDelete = async () => {
    try {
      await deleteDiscount(discountId);
      setSuccessMessage("Discount has been successfully deleted!");
      setErrorMessage("");
      toast.success("Discount has been successfully deleted!");
    } catch (error) {
      setErrorMessage("Error deleting the discount.");
      setSuccessMessage("");
      toast.error("Error deleting the discount.");
    }
  };

  return (
    <div
      className="deleteDiscount modal fade"
      id="deleteDiscountModal"
      tabIndex="-1"
      aria-labelledby="deleteDiscountModalLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="deleteDiscountModalLabel">
              Xóa mã giảm giá
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>

          <div className="modal-body">
            <h3>
              Bạn có chắc là muốn xóa mã giảm giá với ID là {discountId} không?
            </h3>
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
            <button type="button" className="white-btn" data-bs-dismiss="modal">
              Hủy
            </button>
            <button type="button" className="main-btn" onClick={handleDelete}>
              Xóa
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DeleteDiscount;
