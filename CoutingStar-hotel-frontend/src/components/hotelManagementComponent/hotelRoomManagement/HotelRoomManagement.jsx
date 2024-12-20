import React, { useState, useEffect } from "react";
import "./hotelRoomManagement.css";
import {
  getRoomsByHotelId,
  getAllRoomByKeywordAndHotelId,
} from "../../utils/ApiFunction";
import { useParams } from "react-router-dom";
import AddRoom from "./addRoom/AddRoom";
import EditRoom from "./editRoom/EditRoom";
import DeleteRoom from "./deleteRoom/DeleteRoom";

const HotelRoomManagement = () => {
  const [rooms, setRooms] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
  const [selectedRoomId, setSelectedRoomId] = useState(null);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);

  const { hotelId } = useParams();

  const fetchRooms = async (pageNo = 0, pageSize = 8, keyword = "") => {
    try {
      const roomsData = keyword
        ? await getAllRoomByKeywordAndHotelId(
            pageNo,
            pageSize,
            keyword,
            hotelId
          )
        : await getRoomsByHotelId(hotelId, pageNo, pageSize);

      if (roomsData && roomsData.content) {
        setRooms(roomsData.content);
        setTotalPages(roomsData.totalPages);
      } else {
        setRooms([]);
        setTotalPages(1);
      }
    } catch (error) {
      console.error("Error fetching rooms:", error);
      setRooms([]);
      setTotalPages(1);
    }
  };

  useEffect(() => {
    fetchRooms(page, 8, searchKeyword);
  }, [page, searchKeyword, hotelId]);

  const handleSearch = () => {
    setPage(0);
    fetchRooms(0, 8, searchKeyword);
  };

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      setPage(newPage);
    }
  };

  const handleOpenAddModal = () => setIsAddModalOpen(true);
  const handleCloseAddModal = () => setIsAddModalOpen(false);

  const handleOpenUpdateModal = (roomId) => {
    setSelectedRoomId(roomId);
    setIsUpdateModalOpen(true);
  };
  const handleCloseUpdateModal = () => {
    setSelectedRoomId(null);
    setIsUpdateModalOpen(false);
  };

  const handleOpenDeleteModal = (roomId) => {
    setSelectedRoomId(roomId);
    setIsDeleteModalOpen(true);
  };
  const handleCloseDeleteModal = () => {
    setSelectedRoomId(null);
    setIsDeleteModalOpen(false);
  };

  const handleAddRoom = (newRoom) => {
    setRooms((prevRooms) => [...prevRooms, newRoom]);
    handleCloseAddModal();
  };

  const handleUpdateRoom = (updatedRoom) => {
    setRooms((prevRooms) =>
      prevRooms.map((room) => (room.id === updatedRoom.id ? updatedRoom : room))
    );
    handleCloseUpdateModal();
  };

  const handleDeleteRoom = (roomId) => {
    setRooms((prevRooms) => prevRooms.filter((room) => room.id !== roomId));
    handleCloseDeleteModal();
  };

  return (
    <div className="roomManagement">
      <div className="container">
        <div className="searchRoom-bar">
          <div className="row">
            <div className="col-md-5">
              <div className="mb-3">
                <label htmlFor="room-keyword" className="form-label">
                  Nhập từ khóa tìm phòng:
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="room-keyword"
                  placeholder="Nhập từ khóa"
                  value={searchKeyword}
                  onChange={(e) => setSearchKeyword(e.target.value)}
                />
              </div>
            </div>
            <div className="col-md-2 d-flex align-items-center">
              <button className="main-btn" onClick={handleSearch}>
                Tìm <i className="bi bi-search"></i>
              </button>
            </div>
          </div>
          <button className="main-btn" onClick={handleOpenAddModal}>
            Thêm phòng mới
          </button>
        </div>

        <div className="roomList">
          <table className="table table-striped">
            <thead>
              <tr>
                <th scope="col">Loại phòng</th>
                <th scope="col">Miêu tả</th>
                <th scope="col">Giá tiền</th>
                <th scope="col">Đánh giá</th>
                <th scope="col">Hành động</th>
              </tr>
            </thead>
            <tbody>
              {rooms.map((room) => (
                <tr key={room.id}>
                  <td>{room.roomType}</td>
                  <td>{room.roomDescription}</td>
                  <td>{room.roomPrice}</td>
                  <td>
                    {typeof room.averageNumberOfRoomStars === "number"
                      ? room.averageNumberOfRoomStars.toFixed(2) + " / 5"
                      : "Chưa có đánh giá"}
                  </td>

                  <td>
                    <button
                      className="btn btn-primary btn-sm"
                      onClick={() => handleOpenUpdateModal(room.id)}
                    >
                      Chỉnh sửa
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => handleOpenDeleteModal(room.id)}
                    >
                      Xóa
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

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
                  <li
                    key={i}
                    className={`page-item ${page === i ? "active" : ""}`}
                  >
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

        {isAddModalOpen && (
          <AddRoom
            handleAddRoom={handleAddRoom}
            onClose={handleCloseAddModal}
          />
        )}
        {isUpdateModalOpen && selectedRoomId && (
          <EditRoom
            roomId={selectedRoomId}
            handleUpdateRoom={handleUpdateRoom}
            onClose={handleCloseUpdateModal}
          />
        )}
        {isDeleteModalOpen && selectedRoomId && (
          <DeleteRoom
            roomId={selectedRoomId}
            handleDeleteRoom={handleDeleteRoom}
            onClose={handleCloseDeleteModal}
          />
        )}
      </div>
    </div>
  );
};

export default HotelRoomManagement;
