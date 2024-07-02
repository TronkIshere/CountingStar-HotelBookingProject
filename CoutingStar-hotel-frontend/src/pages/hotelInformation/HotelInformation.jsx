import React, { useState, useEffect } from "react";
import "./hotelInformation.css";

const HotelInformation = ({ hotelData }) => {
  const [hotel, setHotel] = useState({
    hotelName: "Khách Sạn Mỹ Hạnh",
    city: "Hồ Chí Minh",
    address: "688/91 Quang Trung, Gò Vấp",
    description:
      "Nằm cách Cổng St. Florian ở Krakow 5 phút đi bộ, Tower Street Apartments cung cấp chỗ ở được trang bị máy điều hòa và wifi miễn phí. Các căn hộ có sàn gỗ cứng và có bếp nhỏ đầy đủ tiện nghi với lò vi sóng, TV màn hình phẳng, và phòng tắm riêng với vòi sen và máy sấy tóc. Một chiếc tủ lạnh là cũng được cung cấp, cũng như một ấm trà điện và một máy pha cà phê. máy móc. Các điểm tham quan nổi tiếng gần căn hộ bao gồm Hội trường Vải, Quảng trường Chợ Chính và Tháp Tòa thị chính. Gần nhất sân bay là John Paul II International Kraków–Balice, 16,1 km từ Tower Street Apartments và nơi lưu trú cung cấp dịch vụ trả phí dịch vụ đưa đón sân bay.",
    hotelPhone: "0359256696",
    hotelImage: "",
  });

  useEffect(() => {
    // Populate the form with existing hotel data when component mounts
    if (hotelData) {
      setHotel(hotelData);
    }
  }, [hotelData]);

  const handleHotelChange = (e) => {
    const { name, value } = e.target;
    setHotel((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Updated Hotel Info:", hotel);
    // Here you would typically send the updated hotel data to your server
  };

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
            onChange={handleHotelChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Thành phố:</label>
          <input
            type="text"
            name="city"
            value={hotel.city}
            onChange={handleHotelChange}
            required
          />
        </div>
        <div className="form-group">
          <label>Địa chỉ:</label>
          <input
            type="text"
            name="address"
            value={hotel.address}
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
          <label>Số điện thoại:</label>
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
            name="hotelImage"
            value={hotel.hotelImage}
            onChange={handleHotelChange}
          />
        </div>

        <button type="submit" className="postHotelButton">
          Lưu
        </button>
      </form>
    </div>
  );
};

export default HotelInformation;
