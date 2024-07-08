import React, { useState, useEffect } from "react"
import "./updateRoom.css"
import { getRoomById, updateRoom } from "../../utils/ApiFunction"

const UpdateRoom = ({ roomId, handleUpdateRoom, onClose }) => {
  const [room, setRoom] = useState({
    photo: "",
    roomType: "",
    roomPrice: "",
    roomDescription: ""
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
  }

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
      if (response.status === 200) {
        setSuccessMessage("Room updated successfully!")
        const updatedRoomData = await getRoomById(roomId)
        setRoom(updatedRoomData)
        setImagePreview(updatedRoomData.photo)
        handleUpdateRoom(updatedRoomData)
        setErrorMessage("")
      } else {
        setErrorMessage("Error updating room")
      }
    } catch (error) {
      console.error(error)
      setErrorMessage(error.message)
    }
  }
  
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
            name="roomType"
            placeholder="Loại phòng"
            value={room.roomType}
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="roomDescription"
            placeholder="Miêu tả"
            value={room.roomDescription}
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="roomPrice"
            placeholder="Giá tiền"
            value={room.roomPrice}
            onChange={handleInputChange}
          />
          <input
            type="file"
            name="photo"
            onChange={handleImageChange}
          />
          {imagePreview && <img src={`data:image/jpeg;base64,${imagePreview}`} alt="Room preview" className="imagePreview" />}
        </div>
        <div className="modalFooter">
          <button className="updateButton" onClick={handleSubmit}>Cập nhật phòng</button>
        </div>
      </div>
    </div>
  );
};

export default UpdateRoom;
