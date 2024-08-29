import React from "react";
import "./deleteDiscount.css";
import { deleteDiscount } from "../../utils/ApiFunction";

const DeleteDiscount = ({ discountId, onClose, handleDeleteDiscount }) => {
  const handleConfirmDelete = async () => {
    try {
      await deleteDiscount(discountId);
      handleDeleteDiscount(discountId);
      onClose();
    } catch (error) {
      console.error("Error deleting discount:", error.message);
    }
  };

  return (
    <div className="modal">
      <div className="modalContent">
        <h2>Xóa mã giảm giá</h2>
        <p>Bạn có chắc chắn muốn xóa mã giảm giá này không?</p>
        <div className="modalActions">
          <button onClick={handleConfirmDelete} className="deleteButton">
            Xóa
          </button>
          <button onClick={onClose} className="cancelButton">
            Hủy bỏ
          </button>
        </div>
      </div>
    </div>
  );
};

export default DeleteDiscount;
