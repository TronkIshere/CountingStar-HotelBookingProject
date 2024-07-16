import React, { useState } from "react";
import "./hotelRegistration.css";
import {
  registerHotelOwner,
  addHotel,
} from "../../components/utils/ApiFunction";

const HotelRegistration = () => {
  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    phoneNumber: "",
  });

  const [hotel, setHotel] = useState({
    hotelName: "",
    city: "",
    hotelLocation: "",
    description: "",
    hotelPhone: "",
    photo: null,
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [imagePreview, setImagePreview] = useState("");

  const handleRegistration = async (e) => {
    e.preventDefault();
    try {
      const hotelOwner = await registerHotelOwner(user);
  
      try {
        const response = await addHotel(
          hotelOwner.id,
          hotel.hotelName,
          hotel.city,
          hotel.hotelLocation,
          hotel.description,
          hotel.hotelPhone,
          hotel.photo
        );
  
        if (response.status === 200 || response.status === 201) {
          setSuccessMessage(response.data || "Hotel registered successfully!");
        } else {
          setErrorMessage(response.data || "Failed to register hotel.");
        }
      } catch (addHotelError) {
        let errorMessage = "Failed to register hotel: ";
        if (addHotelError.response && addHotelError.response.data) {
          errorMessage += JSON.stringify(addHotelError.response.data);
        } else {
          errorMessage += addHotelError.message;
        }
        setErrorMessage(errorMessage);
      }
    } catch (registerHotelOwnerError) {
      let errorMessage = "Failed to register hotel owner: ";
      if (registerHotelOwnerError.response && registerHotelOwnerError.response.data) {
        errorMessage += JSON.stringify(registerHotelOwnerError.response.data);
      } else {
        errorMessage += registerHotelOwnerError.message;
      }
      setErrorMessage(errorMessage);
    }
  
    setTimeout(() => {
      setErrorMessage("");
      setSuccessMessage("");
    }, 5000);
  };

  const handleUserChange = (e) => {
    const { name, value } = e.target;
    setUser((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleHotelChange = (e) => {
    const { name, value } = e.target;
    setHotel((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleImageChange = (e) => {
    const selectedImage = e.target.files[0];
    setHotel({ ...hotel, photo: selectedImage });
    setImagePreview(URL.createObjectURL(selectedImage));
  };

  return (
    <div className="container">
      <form
        onSubmit={handleRegistration}
        className="form"
        encType="multipart/form-data"
      >
        <h2>Thông tin chủ khách sạn</h2>
        <div className="form-group">
          <label>Họ và tên đệm:</label>
          <input
            type="text"
            name="firstName"
            value={user.firstName}
            onChange={handleUserChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Tên:</label>
          <input
            type="text"
            name="lastName"
            value={user.lastName}
            onChange={handleUserChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Mật khẩu:</label>
          <input
            type="password"
            name="password"
            value={user.password}
            onChange={handleUserChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Số điện thoại:</label>
          <input
            type="tel"
            name="phoneNumber"
            value={user.phoneNumber}
            onChange={handleUserChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Email:</label>
          <input
            type="email"
            name="email"
            value={user.email}
            onChange={handleUserChange}
            required
          />
        </div>

        <h2>Thông tin khách sạn</h2>
        <div className="form-group">
          <label>Tên khách sạn:</label>
          <input
            type="text"
            name="hotelName"
            value={hotel.hotelName}
            onChange={handleHotelChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Thành phố:</label>
          <select
            name="city"
            className="selectBar"
            value={hotel.city}
            onChange={handleHotelChange}
            required
          >
            <option value="" disabled>
              Khách sạn bạn ở thành phố nào?
            </option>
            <option value="Hồ Chí Minh">Hồ Chí Minh</option>
            <option value="Hà Nội">Hà Nội</option>
            <option value="Đà Lạt">Đà Lạt</option>
            <option value="Nha Trang">Nha Trang</option>
            <option value="Vũng Tàu">Vũng Tàu</option>
          </select>
        </div>
        <div className="form-group">
          <label>Địa chỉ:</label>
          <input
            type="text"
            name="hotelLocation"
            value={hotel.hotelLocation}
            onChange={handleHotelChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Mô tả:</label>
          <textarea
            name="description"
            value={hotel.description}
            onChange={handleHotelChange}
            required
          ></textarea>
        </div>
        <div className="form-group">
          <label>Số điện thoại khách sạn:</label>
          <input
            type="tel"
            name="hotelPhone"
            value={hotel.hotelPhone}
            onChange={handleHotelChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Ảnh của khách sạn:</label>
          <input
            type="file"
            name="photo"
            onChange={handleImageChange}
            required
          />
          {imagePreview && (
            <div className="imagePreview">
              <img className="inputImg" src={imagePreview} alt="Preview" />
            </div>
          )}
        </div>

        <button type="submit" className="postHotelButton">
          Đăng ký khách sạn
        </button>

        {errorMessage && <div className="error-message">{errorMessage}</div>}
        {successMessage && (
          <div className="success-message">{successMessage}</div>
        )}
      </form>
    </div>
  );
};

export default HotelRegistration;
