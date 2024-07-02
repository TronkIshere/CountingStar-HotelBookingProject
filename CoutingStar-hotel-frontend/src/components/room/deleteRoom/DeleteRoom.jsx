import React from "react";
import "./deleteRoom.css";

const DeleteRoom = ({ room, handleDeleteRoom, onClose }) => {
  return (
    <div className="modal">
      <div className="modalContent">
        <div className="modalHeader">
          <h2>Xác nhận xóa phòng</h2>
          <span className="close" onClick={onClose}>&times;</span>
        </div>
        <div className="modalBody">
          <p>Bạn có chắc chắn muốn xóa phòng <strong>{room.type}</strong> không?</p>
        </div>
        <div className="modalFooter">
          <button className="deleteButton" onClick={() => handleDeleteRoom(room)}>Xóa</button>
          <button className="cancelButton" onClick={onClose}>Hủy</button>
        </div>
      </div>
    </div>
  );
};

export default DeleteRoom;
