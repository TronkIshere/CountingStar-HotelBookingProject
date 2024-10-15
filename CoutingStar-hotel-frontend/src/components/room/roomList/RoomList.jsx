import React, { useEffect, useState } from "react";
import "./roomList.css";
import { getRoomsByHotelId } from "../../utils/ApiFunction";
import BookingForm from "../../bookingRoom/bookingForm/BookingForm";

const RoomList = ({ hotelId }) => {
  const [selectedRoom, setSelectedRoom] = useState(null);
  const [rooms, setRooms] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setIsLoading(true);
    getRoomsByHotelId(hotelId)
      .then((response) => {
        setRooms(response);
        setIsLoading(false);
      })
      .catch((error) => {
        setError(error);
        setIsLoading(false);
      });
  }, [hotelId]);

  if (isLoading) {
    return <div>Đang kiểm tra phòng...</div>;
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
                <td>{room.averageNumberOfRoomStars} / 5</td>
                <td className="lastTd">
                  <button
                    type="button"
                    className="btn btn-primary"
                    data-bs-toggle="modal"
                    data-bs-target="#bookingModal"
                    onClick={() => setSelectedRoom(room.roomId)}
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
      <BookingForm roomId={selectedRoom} />
    </div>
  );
};

export default RoomList;
