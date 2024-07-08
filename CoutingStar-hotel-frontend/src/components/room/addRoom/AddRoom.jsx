import React, { useState } from "react";
import "./addRoom.css";
import { addRoom } from "../../utils/ApiFunction";

const AddRoom = ({ onClose }) => {
  const [newRoom, setNewRoom] = useState({
    photo: null,
    roomType: "",
    roomPrice: "",
    roomDescription: ""
  });

  const [successMessage, setSuccessMessage] = useState("");
  const [imagePreview, setImagePreview] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleRoomInputChange = (e) => {
    const name = e.target.name;
    let value = e.target.value;
    if (name === "roomPrice") {
      if (!isNaN(value)) {
        value = parseInt(value);
      } else {
        value = "";
      }
    }
    setNewRoom({ ...newRoom, [name]: value });
  };

  const handleImageChange = (e) => {
    const selectedImage = e.target.files[0];
    setNewRoom({ ...newRoom, photo: selectedImage });
    setImagePreview(URL.createObjectURL(selectedImage));
  }

  const hotelId = localStorage.getItem("userHotelId")
  console.log(hotelId)

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const success = await addRoom(newRoom.photo, newRoom.roomType, newRoom.roomPrice, newRoom.roomDescription, hotelId);
      if (success !== undefined) {
        setSuccessMessage("A new room was added successfully!")
        setNewRoom({ photo: null, roomType: "", roomPrice: "", roomDescription: "" })
        setImagePreview("")
        setErrorMessage("")
      } else {
        setErrorMessage("Error adding new room")
      }
    } catch (error) {
      setErrorMessage(error.message)
    }
    setTimeout(() => {
      setSuccessMessage("")
      setErrorMessage("")
    }, 3000)
  }

  return (
    <div className="modal">
      <form className="modalContent" onSubmit={handleSubmit}>
        <div className="modalHeader">
          <h2>Thêm phòng mới</h2>
          <span className="close" onClick={onClose}>&times;</span>
        </div>
        <div className="modalBody">
          <input
            type="text"
            name="roomType"
            placeholder="Loại phòng"
            value={newRoom.roomType}
            onChange={handleRoomInputChange}
          />
          <input
            type="text"
            name="roomDescription"
            placeholder="Miêu tả"
            value={newRoom.roomDescription}
            onChange={handleRoomInputChange}
          />
          <input
            type="text"
            name="roomPrice"
            placeholder="Giá tiền"
            value={newRoom.roomPrice}
            onChange={handleRoomInputChange}
          />
          <input
            type="file"
            name="photo"
            accept="image/*"
            onChange={handleImageChange}
          />
          {imagePreview && (
            <div className="imagePreview">
              <img className="inputImg" src={imagePreview} alt="Preview" />
            </div>
          )}
        </div>
        <div className="modalFooter">
          <button className="addButton" type="submit">Thêm phòng</button>
        </div>
        {successMessage && <p className="successMessage">{successMessage}</p>}
        {errorMessage && <p className="errorMessage">{errorMessage}</p>}
      </form>
    </div>
  );
};

export default AddRoom;
