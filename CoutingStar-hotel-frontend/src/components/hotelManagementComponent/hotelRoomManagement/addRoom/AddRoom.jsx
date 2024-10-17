import React, { useState } from "react";
import { addRoom } from "../../../utils/ApiFunction";

const AddRoom = ({ onClose }) => {
  const [newRoom, setNewRoom] = useState({
    photo: null,
    roomType: "",
    roomPrice: "",
    roomDescription: "",
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
  };

  const hotelId = localStorage.getItem("userHotelId");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const success = await addRoom(
        newRoom.photo,
        newRoom.roomType,
        newRoom.roomPrice,
        newRoom.roomDescription,
        hotelId
      );
      if (success !== undefined) {
        setSuccessMessage("Thêm phòng mới thành công!");
        setNewRoom({
          photo: null,
          roomType: "",
          roomPrice: "",
          roomDescription: "",
        });
        setImagePreview("");
        setErrorMessage("");
      } else {
        setErrorMessage("Có lỗi khi thêm phòng mới");
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  return (
    <div
      className="modal fade show"
      style={{ display: "block" }}
      tabIndex="-1"
      role="dialog"
    >
      <div className="modal-dialog" role="document">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Thêm phòng mới</h5>
            <button
              type="button"
              className="btn-close"
              onClick={onClose}
            ></button>
          </div>
          <div className="modal-body">
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label">Loại phòng</label>
                <input
                  type="text"
                  className="form-control"
                  name="roomType"
                  placeholder="Loại phòng"
                  value={newRoom.roomType}
                  onChange={handleRoomInputChange}
                />
              </div>
              <div className="mb-3">
                <label className="form-label">Miêu tả</label>
                <input
                  type="text"
                  className="form-control"
                  name="roomDescription"
                  placeholder="Miêu tả"
                  value={newRoom.roomDescription}
                  onChange={handleRoomInputChange}
                />
              </div>
              <div className="mb-3">
                <label className="form-label">Giá tiền</label>
                <input
                  type="text"
                  className="form-control"
                  name="roomPrice"
                  placeholder="Giá tiền"
                  value={newRoom.roomPrice}
                  onChange={handleRoomInputChange}
                />
              </div>
              <div className="mb-3">
                <label className="form-label">Hình ảnh</label>
                <input
                  type="file"
                  className="form-control"
                  name="photo"
                  accept="image/*"
                  onChange={handleImageChange}
                />
                {imagePreview && (
                  <div className="mt-2">
                    <img
                      className="img-thumbnail"
                      src={imagePreview}
                      alt="Preview"
                    />
                  </div>
                )}
              </div>
              {successMessage && (
                <div className="alert alert-success">{successMessage}</div>
              )}
              {errorMessage && (
                <div className="alert alert-danger">{errorMessage}</div>
              )}
              <div className="modal-footer">
                <button type="button" className="white-btn" onClick={onClose}>
                  Đóng
                </button>
                <button type="submit" className="main-btn">
                  Cập nhật
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddRoom;
