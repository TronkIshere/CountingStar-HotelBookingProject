import React, { useEffect, useState } from "react";
import "./roomList.css";
import { getRoomsByHotelId } from "../utils/ApiFunction";
import BookingForm from "../bookingRoom/BookingForm";

const RoomList = ({ hotelId }) => {
  const [selectedRoom, setSelectedRoom] = useState(null);
  const [rooms, setRooms] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(0);
  const [pageSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    fetchRooms();
  }, [hotelId, page]);

  const fetchRooms = async () => {
    setIsLoading(true);
    setError(null);
    try {
      const response = await getRoomsByHotelId(hotelId, page, pageSize);
      console.log(response);
      setRooms(response.content);
      setTotalPages(response.totalPages);
      setIsLoading(false);
    } catch (error) {
      console.error("Error fetching rooms:", error);
      setError(error.message);
      setIsLoading(false);
    }
  };

  const handlePageChange = (newPage) => {
    setPage(newPage);
  };

  if (error) {
    return <div>{`Đã xảy ra lỗi: ${error}`}</div>;
  }

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
                <td>
                  {new Intl.NumberFormat("vi-VN", {
                    style: "currency",
                    currency: "VND",
                  }).format(room.roomPrice)}
                </td>

                <td>{room.averageNumberOfRoomStars} / 5</td>
                <td className="lastTd">
                  <button
                    type="button"
                    className="btn btn-primary"
                    data-bs-toggle="modal"
                    data-bs-target="#bookingModal"
                    onClick={() => setSelectedRoom(room.id)}
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
      <div className="pagination d-flex justify-content-center mt-4">
        <nav aria-label="Page navigation example">
          <ul className="pagination">
            <li className={`page-item ${page === 0 ? "disabled" : ""}`}>
              <button
                className="page-link"
                onClick={() => handlePageChange(page - 1)}
              >
                &laquo;
              </button>
            </li>
            {Array.from({ length: totalPages }, (_, i) => (
              <li key={i} className={`page-item ${page === i ? "active" : ""}`}>
                <button
                  className="page-link"
                  onClick={() => handlePageChange(i)}
                >
                  {i + 1}
                </button>
              </li>
            ))}
            <li
              className={`page-item ${
                page === totalPages - 1 ? "disabled" : ""
              }`}
            >
              <button
                className="page-link"
                onClick={() => handlePageChange(page + 1)}
              >
                &raquo;
              </button>
            </li>
          </ul>
        </nav>
      </div>
    </div>
  );
};

export default RoomList;
