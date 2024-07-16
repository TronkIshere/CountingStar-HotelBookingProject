import React, { useState, useEffect } from "react";
import "./hotelInformationManagement.css";
import { getHotelById, updateHotel } from "../../components/utils/ApiFunction";
import { useParams } from "react-router-dom";

const HotelInformationManagement = () => {
  const [hotel, setHotel] = useState({
    hotelName: "",
    city: "",
    hotelLocation: "",
    hotelDescription: "",
    phoneNumber: "",
    photo: "",
  });

  const [imagePreview, setImagePreview] = useState("")
  const [successMessage, setSuccessMessage] = useState("")
  const [errorMessage, setErrorMessage] = useState("")

  const handleImageChange = (e) => {
    const selectedImage = e.target.files[0];
    setHotel({ ...hotel, photo: selectedImage })
    setImagePreview(URL.createObjectURL(selectedImage))
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target
    setHotel({ ...hotel, [name]: value })
  };

  const { hotelId } = useParams();

  useEffect(() => {
    const fetchRoom = async () => {
      try {
        const hotelData = await getHotelById(hotelId)
        setHotel(hotelData)
        setImagePreview(hotelData.photo)
      } catch (error) {
        console.log(error)
      }
    }

    fetchRoom()
  }, [hotelId])

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      const response = await updateHotel(hotelId, hotel)
      if (response.status === 200) {
        setSuccessMessage("Chỉnh sửa khách sạn thành công!!!")
        const updatedhotelData = await getRoomById(hotelId)
        setHotel(updatedhotelData)
        setImagePreview(updatedhotelData.photo)
        handleUpdateRoom(updatedhotelData)
        setErrorMessage("")
      } else {
        setErrorMessage("Đã xảy ra lỗi!!!")
      }
    } catch (error) {
      setErrorMessage(error.message)
    }
  }

  return (
    <div className="container">
      <form onSubmit={handleSubmit} className="form">
        <h2>Chỉnh sửa thông tin khách sạn</h2>
        <div className="form-group">
          <label>Tên khách sạn:</label>
          <input
            type="text"
            name="hotelName"
            value={hotel.hotelName}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Thành phố:</label>
          <input
            type="text"
            name="city"
            value={hotel.city}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Địa chỉ:</label>
          <input
            type="text"
            name="address"
            value={hotel.hotelLocation}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Mô tả:</label>
          <textarea
            name="description"
            value={hotel.hotelDescription}
            onChange={handleInputChange}
            required
          ></textarea>
        </div>
        <div className="form-group">
          <label>Số điện thoại:</label>
          <input
            type="tel"
            name="hotelPhone"
            value={hotel.phoneNumber}
            onChange={handleInputChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Ảnh của khách sạn:</label>
          <input
            type="file"
            name="hotelImage"
            onChange={handleImageChange}
          />
          {imagePreview && (
            <img
              src={`data:image/jpeg;base64,${imagePreview}`}
              alt="Room preview"
              className="imagePreview"
            />
          )}
        </div>

        <button type="submit" className="postHotelButton">
          Lưu
        </button>
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

export default HotelInformationManagement;