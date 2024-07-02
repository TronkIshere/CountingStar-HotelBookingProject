import React, { useState } from "react";
import "./addRoom.css";

const AddRoom = ({ handleAddRoom, onClose }) => {
  const [newRoom, setNewRoom] = useState({
    type: "",
    description: "",
    price: "",
    details: "",
    image: null,
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewRoom((prevRoom) => ({
      ...prevRoom,
      [name]: value,
    }));
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setNewRoom((prevRoom) => ({
      ...prevRoom,
      image: file,
    }));
  };

  const handleSubmit = () => {
    handleAddRoom(newRoom);
    setNewRoom({
      type: "",
      description: "",
      price: "",
      details: "",
      image: null,
    });
  };

  return (
    <div className="modal">
      <div className="modalContent">
        <div className="modalHeader">
          <h2>Thêm phòng mới</h2>
          <span className="close" onClick={onClose}>&times;</span>
        </div>
        <div className="modalBody">
          <input
            type="text"
            name="type"
            placeholder="Loại phòng"
            value={newRoom.type}
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="description"
            placeholder="Miêu tả"
            value={newRoom.description}
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="price"
            placeholder="Giá tiền"
            value={newRoom.price}
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
          <button className="addButton" onClick={handleSubmit}>Thêm phòng</button>
        </div>
      </div>
    </div>
  );
};

export default AddRoom;
