import React, { useEffect, useState } from "react";
import "./roomList.css";
import BookingForm from "../../bookingForm/BookingForm";
import { getRoomsByHotelId } from "../../utils/ApiFunction";

const RoomList = ({ hotelId }) => {
  const [selectedRoom, setSelectedRoom] = useState(null)
  const [rooms, setRooms] = useState([])
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState(null)

  const handleBookClick = (roomId) => {
    setSelectedRoom(roomId)
  };

  const handleCloseModal = () => {
    setSelectedRoom(null)
  };

  useEffect(() => {
    setIsLoading(true);
    getRoomsByHotelId(hotelId)
      .then((response) => {
        console.log("Rooms data:", response)
        setRooms(response)
        setIsLoading(false)
      })
      .catch((error) => {
        console.error("Error fetching rooms:", error)
        setError(error)
        setIsLoading(false)
      });
  }, [hotelId])

  if (isLoading) {
    return <div>Đang kiểm tra phòng...</div>
  }


  return (
    <div className="roomListContainer">
      <h1>Danh sách phòng</h1>
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
          {rooms.length > 0 ? (
            rooms.map((room, index) => (
              <tr key={index}>
                <td>{room.roomType}</td>
                <td>{room.roomDescription}</td>
                <td>${room.roomPrice}</td>
                <td>Đánh giá</td>
                <td className="lastTd">
                  <button
                    className="bookButton"
                    onClick={() => handleBookClick(room.id)}
                  >
                    Đặt
                  </button>
                  <div className="noCreditCard">Không cần thẻ tín dụng</div>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="5">Không có phòng nào</td>
            </tr>
          )}
        </tbody>
      </table>
      {selectedRoom && (
        <BookingForm roomId={selectedRoom} onClose={handleCloseModal} />
      )}
    </div>
  );
};

export default RoomList;
