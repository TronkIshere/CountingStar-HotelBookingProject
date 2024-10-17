import React from "react";
import { deleteRoom } from "../../../utils/ApiFunction";
import "./deleteRoom.css";

const DeleteRoom = ({ roomId, handleDeleteRoom, onClose }) => {
  const handleDelete = async () => {
    try {
      await deleteRoom(roomId);
      handleDeleteRoom(roomId);
      onClose();
    } catch (error) {
      console.error("Error deleting room:", error);
    }
  };

  return (
    <div className="deleteRoom modal fade show" style={{ display: "block" }}>
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Xác nhận xóa phòng</h5>
            <button
              type="button"
              className="btn-close"
              onClick={onClose}
              aria-label="Close"
            ></button>
          </div>
          <div className="modal-body">
            <p>Bạn có chắc chắn muốn xóa phòng không?</p>
          </div>
          <div className="modal-footer">
            <button className="white-btn" onClick={handleDelete}>
              Xóa
            </button>
            <button className="main-btn" onClick={onClose}>
              Hủy
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DeleteRoom;
