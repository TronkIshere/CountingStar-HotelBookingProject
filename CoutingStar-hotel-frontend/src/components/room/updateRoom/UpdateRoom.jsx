import React, { useState, useEffect } from "react";
import "./updateRoom.css";
import { getRoomById, updateRoom } from "../../utils/ApiFunction";

const UpdateRoom = ({ roomId, handleUpdateRoom, onClose }) => {
  const [room, setRoom] = useState({
    roomType: "",
    roomPrice: "",
    roomDescription: "",
    photo: "",
  })

  const [imagePreview, setImagePreview] = useState("")
  const [successMessage, setSuccessMessage] = useState("")
  const [errorMessage, setErrorMessage] = useState("")

  const handleImageChange = (e) => {
    const selectedImage = e.target.files[0]
    setRoom({ ...room, photo: selectedImage })
    setImagePreview(URL.createObjectURL(selectedImage))
  }

  const handleInputChange = (event) => {
    const { name, value } = event.target
    setRoom({ ...room, [name]: value })
  };

  useEffect(() => {
    const fetchRoom = async () => {
      try {
        const roomData = await getRoomById(roomId)
        setRoom(roomData)
        setImagePreview(roomData.photo)
      } catch (error) {
        console.log(error)
      }
    };

    fetchRoom()
  }, [roomId])

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      const response = await updateRoom(roomId, room)
      console.log(response)
      if (response.status === 200) {
        setSuccessMessage("Chỉnh sửa phòng thành công!!!")
        const updatedRoomData = await getRoomById(roomId)
        setRoom(updatedRoomData)
        setImagePreview(updatedRoomData.photo)
        handleUpdateRoom(updatedRoomData)
        setErrorMessage("")
      } else {
        setErrorMessage("Đã xảy ra lỗi!!!")
      }
    } catch (error) {
      setErrorMessage(error.message)
    }
  };

  return (
    <div className="modal">
      <form className="modalContent" onSubmit={handleSubmit}>
        <div className="modalHeader">
          <h2>Chỉnh sửa phòng</h2>
          <span className="close" onClick={onClose}>
            &times;
          </span>
        </div>
        <div className="modalBody">
          <label>
            Loại phòng
            <input
              type="text"
              name="roomType"
              placeholder="Loại phòng"
              value={room.roomType}
              onChange={handleInputChange}
            />
          </label>
          <label>
            Miêu tả
            <input
              type="text"
              name="roomDescription"
              placeholder="Miêu tả"
              value={room.roomDescription}
              onChange={handleInputChange}
            />
          </label>
          <label>
            Giá tiền
            <input
              type="text"
              name="roomPrice"
              placeholder="Giá tiền"
              value={room.roomPrice}
              onChange={handleInputChange}
            />
          </label>
          <label>
            Hình ảnh
            <input type="file" name="photo" onChange={handleImageChange} />
          </label>
          {imagePreview && (
            <img
              src={`data:image/jpeg;base64,${imagePreview}`}
              alt="Room preview"
              className="imagePreview"
            />
          )}
        </div>
        <div className="modalFooter">
          <button className="updateButton" type="submit">
            Cập nhật phòng
          </button>
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
      </form>
    </div>
  )
}

export default UpdateRoom;
