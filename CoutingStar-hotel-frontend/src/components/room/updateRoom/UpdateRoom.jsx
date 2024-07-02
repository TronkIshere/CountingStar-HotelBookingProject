import React, { useState, useEffect } from "react";
import "./updateRoom.css";

const UpdateRoom = ({ room, handleUpdateRoom, onClose }) => {
  const [updatedRoom, setUpdatedRoom] = useState(room);

  useEffect(() => {
    setUpdatedRoom(room);
  }, [room]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUpdatedRoom((prevRoom) => ({
      ...prevRoom,
      [name]: value,
    }));
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setUpdatedRoom((prevRoom) => ({
      ...prevRoom,
      image: file,
    }));
  };

  const handleSubmit = () => {
    handleUpdateRoom(updatedRoom);
  };

  return (
    <div className="modal">
      <div className="modalContent">
        <div className="modalHeader">
          <h2>Chỉnh sửa phòng</h2>
          <span className="close" onClick={onClose}>&times;</span>
        </div>
        <div className="modalBody">
          <input
            type="text"
            name="type"
            placeholder="Loại phòng"
            value={updatedRoom.type}
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="description"
            placeholder="Miêu tả"
            value={updatedRoom.description}
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="price"
            placeholder="Giá tiền"
            value={updatedRoom.price}
            onChange={handleInputChange}
          />
          <input
            type="file"
            name="image"
            accept="image/*"
            onChange={handleImageChange}
          />
        </div>
        <div className="modalFooter">
          <button className="updateButton" onClick={handleSubmit}>Cập nhật phòng</button>
        </div>
      </div>
    </div>
  );
};

export default UpdateRoom;
