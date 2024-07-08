import React from "react";
import "./deleteRoom.css";
import { deleteRoom } from "../../utils/ApiFunction";

const DeleteRoom = ({ room, handleDeleteRoom, onClose }) => {
  const handleDelete = async () => {
    try {
      await deleteRoom(room.id);
      handleDeleteRoom(room);
    } catch (error) {
      console.error("Error deleting room:", error);
    }
    onClose();
  };

  return (
    <div className="modal">
      <div className="modalContent">
        <div className="modalHeader">
          <h2>Xác nhận xóa phòng</h2>
          <span className="close" onClick={onClose}>&times;</span>
        </div>
        <div className="modalBody">
          <p>Bạn có chắc chắn muốn xóa phòng <strong>{room.roomType}</strong> không?</p>
        </div>
        <div className="modalFooter">
          <button className="deleteButton" onClick={handleDelete}>Xóa</button>
          <button className="cancelButton" onClick={onClose}>Hủy</button>
        </div>
      </div>
    </div>
  );
};

export default DeleteRoom;
