import React, { useState } from "react";
import './hotelRegistration.css';

const HotelRegistration = () => {
  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    password: "",
    phoneNumber: "",
    email: ""
  });

  const [hotel, setHotel] = useState({
    hotelName: "",
    city: "",
    address: "",
    description: "",
    hotelPhone: "",
    hotelImage: ""
  });

  const handleUserChange = (e) => {
    const { name, value } = e.target;
    setUser(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleHotelChange = (e) => {
    const { name, value } = e.target;
    setHotel(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("User Info:", user);
    console.log("Hotel Info:", hotel);
  };

  return (
    <div className="container">
      <form onSubmit={handleSubmit} className="form">
        <h2>Thông tin chủ khách sạn</h2>
        <div className="form-group">
          <label>Họ và tên đệm:</label>
          <input type="text" name="firstName" value={user.firstName} onChange={handleUserChange} required />
        </div>
        <div className="form-group">
          <label>Tên:</label>
          <input type="text" name="lastName" value={user.lastName} onChange={handleUserChange} required />
        </div>
        <div className="form-group">
          <label>Mật khẩu:</label>
          <input type="password" name="password" value={user.password} onChange={handleUserChange} required />
        </div>
        <div className="form-group">
          <label>Số điện thoại:</label>
          <input type="tel" name="phoneNumber" value={user.phoneNumber} onChange={handleUserChange} required />
        </div>
        <div className="form-group">
          <label>Email:</label>
          <input type="email" name="email" value={user.email} onChange={handleUserChange} required />
        </div>
        
        <h2>Thông tin khách sạn</h2>
        <div className="form-group">
          <label>Tên khách sạn:</label>
          <input type="text" name="hotelName" value={hotel.hotelName} onChange={handleHotelChange} required />
        </div>
        <div className="form-group">
          <label>Thành phố:</label>
          <input type="text" name="city" value={hotel.city} onChange={handleHotelChange} required />
        </div>
        <div className="form-group">
          <label>Địa chỉ:</label>
          <input type="text" name="address" value={hotel.address} onChange={handleHotelChange} required />
        </div>
        <div className="form-group">
          <label>Mô tả:</label>
          <textarea name="description" value={hotel.description} onChange={handleHotelChange} required></textarea>
        </div>
        <div className="form-group">
          <label>Số điện thoại:</label>
          <input type="tel" name="hotelPhone" value={hotel.hotelPhone} onChange={handleHotelChange} required />
        </div>
        <div className="form-group">
          <label>Ảnh của khách sạn:</label>
          <input type="file" name="hotelImage" value={hotel.hotelImage} onChange={handleHotelChange} required />
        </div>
        
        <button type="submit" className="postHotelButton">Register Hotel</button>
      </form>
    </div>
  );
};

export default HotelRegistration;