import React, { useState } from "react";
import "./roomList.css";
import BookingForm from "../../bookingForm/BookingForm";

const RoomList = () => {
  const [selectedRoom, setSelectedRoom] = useState(null);
  const handleBookClick = (room) => {
    setSelectedRoom(room);
  };
  const handleCloseModal = () => {
    setSelectedRoom(null);
  };
  const rooms = [
    {
      type: "Deluxe",
      description: "A spacious room with a beautiful view.",
      price: "$150",
      details:
        "King size bed, Free WiFi, Air conditioning, Mini bar, Balcony with sea view",
      rating: "4.5/5",
    },
    {
      type: "Standard",
      description: "A comfortable room for a great stay.",
      price: "$100",
      details: "Queen size bed, Free WiFi, TV, Private bathroom",
      rating: "4.0/5",
    },
    {
      type: "Suite",
      description: "A luxurious suite with all amenities.",
      price: "$250",
      details: "King size bed, Free WiFi, Jacuzzi, Kitchenette, Living room",
      rating: "4.8/5",
    },
    {
      type: "Single",
      description: "A cozy room perfect for solo travelers.",
      price: "$80",
      details: "Single bed, Free WiFi, TV, Shared bathroom",
      rating: "3.8/5",
    },
    {
      type: "Double",
      description: "A room with two beds for friends or family.",
      price: "$120",
      details: "Two single beds, Free WiFi, TV, Private bathroom",
      rating: "4.2/5",
    },
  ];
  return (
    <div className="roomListContainer">
      <h1>Room List</h1>
      <table className="roomListTable">
        <thead>
          <tr>
            <th>Loại phòng</th>
            <th>Miêu tả</th>
            <th>Giá tiền</th>
            <th>Đánh giá</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {rooms.map((room, index) => (
            <tr key={index}>
              <td>{room.type}</td>
              <td>{room.description}</td>
              <td>{room.price}</td>
              <td>{room.rating}</td>
              <td>
                <button
                  className="bookButton"
                  onClick={() => handleBookClick(room)}
                >
                  Đặt
                </button>
                <div className="noCreditCard">Không cần thẻ tín dụng</div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {selectedRoom && (
        <BookingForm room={selectedRoom} onClose={handleCloseModal} />
      )}
    </div>
  );
};

export default RoomList;
