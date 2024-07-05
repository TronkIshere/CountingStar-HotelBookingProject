import React from "react";
import "./bookingForm.css";

const BookingForm = ({ room, onClose }) => {
  if (!room) return null; // Do not render if no room is selected

  return (
    <div className="modal_overlay" onClick={onClose}>
      <div className="bookingForm" onClick={(e) => e.stopPropagation()}>
        <button className="closeButton" onClick={onClose}>×</button>
        <h1>Đặt phòng</h1>
        <div className="roomContent">
          <div className="roomImg">
            <img src={`data:image/png;base64, ${room.photo}`} alt={room.roomType} />
          </div>
          <div className="roomDetail">
            <p>
              <strong>Loại phòng:</strong> {room.roomType}
            </p>
            <p>
              <strong>Miêu tả:</strong> {room.roomDescription}
            </p>
            <p>
              <strong>Giá tiền:</strong> {room.roomPrice}
            </p>
            <p>
              <strong>Đánh giá:</strong> {room.rating}
            </p>
          </div>
        </div>
        <div className="userInfoInput">
          <input type="text" placeholder="Nhập Email" />
          <input type="text" placeholder="Nhập tên" />
          <input type="text" placeholder="Nhập họ và tên đệm" />
          <input type="text" placeholder="Nhập số điện thoại" />
          <input type="number" placeholder="Nhập số người lớn" />
          <input type="number" placeholder="Nhập số trẻ em" />
        </div>
        <button className="submitButton">Đặt phòng</button>
      </div>
    </div>
  );
};

export default BookingForm;
