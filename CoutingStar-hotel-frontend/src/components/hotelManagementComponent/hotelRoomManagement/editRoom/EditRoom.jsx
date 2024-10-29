import React, { useState, useEffect } from "react";
import { getRoomById, updateRoom } from "../../../utils/ApiFunction";
import "./editRoom.css";

const EditRoom = ({ roomId, handleUpdateRoom, onClose }) => {
  const [room, setRoom] = useState({
    roomType: "",
    roomPrice: "",
    roomDescription: "",
    photo: "",
  });

  const [imagePreview, setImagePreview] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleImageChange = (e) => {
    const selectedImage = e.target.files[0];
    setRoom({ ...room, photo: selectedImage });
    setImagePreview(URL.createObjectURL(selectedImage));
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setRoom({ ...room, [name]: value });
  };

  useEffect(() => {
    const fetchRoom = async () => {
      try {
        const roomData = await getRoomById(roomId);
        setRoom(roomData);
        setImagePreview(roomData.photo);
      } catch (error) {
        console.log(error);
      }
    };

    fetchRoom();
  }, [roomId]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await updateRoom(roomId, room);
      if (response.status === 200) {
        setSuccessMessage("Cập nhật phòng thành công!");
        const updatedRoomData = await getRoomById(roomId);
        setRoom(updatedRoomData);
        setImagePreview(updatedRoomData.photo);
        handleUpdateRoom(updatedRoomData);
        setErrorMessage("");
      } else {
        setErrorMessage("Đã xảy ra lỗi!");
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
  };

  return (
    <div className="editRoom modal fade show" style={{ display: "block" }}>
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Chỉnh sửa phòng</h5>
            <button
              type="button"
              className="btn-close"
              onClick={onClose}
              aria-label="Close"
            ></button>
          </div>
          <div className="modal-body">
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="roomType" className="form-label">
                  Loại phòng
                </label>
                <input
                  type="text"
                  id="roomType"
                  name="roomType"
                  className="form-control"
                  placeholder="Loại phòng"
                  value={room.roomType}
                  onChange={handleInputChange}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="roomDescription" className="form-label">
                  Miêu tả
                </label>
                <input
                  type="text"
                  id="roomDescription"
                  name="roomDescription"
                  className="form-control"
                  placeholder="Miêu tả"
                  value={room.roomDescription}
                  onChange={handleInputChange}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="roomPrice" className="form-label">
                  Giá tiền
                </label>
                <input
                  type="text"
                  id="roomPrice"
                  name="roomPrice"
                  className="form-control"
                  placeholder="Giá tiền"
                  value={room.roomPrice}
                  onChange={handleInputChange}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="photo" className="form-label">
                  Hình ảnh
                </label>
                <input
                  type="file"
                  id="photo"
                  name="photo"
                  className="form-control"
                  onChange={handleImageChange}
                />
              </div>
              {imagePreview && (
                <img
                  src={`data:image/png;base64, ${imagePreview}`}
                  alt="Room preview"
                  className="img-fluid mb-3"
                />
              )}
              {errorMessage && <p className="text-danger">{errorMessage}</p>}
              {successMessage && (
                <p className="text-success">{successMessage}</p>
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

export default EditRoom;
