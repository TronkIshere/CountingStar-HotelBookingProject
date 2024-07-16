import React, { useState, useEffect } from "react";
import "./hotelRoomManagement.css";
import AddRoom from "../../components/room/addRoom/AddRoom";
import UpdateRoom from "../../components/room/updateRoom/UpdateRoom";
import DeleteRoom from "../../components/room/deleteRoom/DeleteRoom";
import { getRoomsByHotelId } from "../../components/utils/ApiFunction";
import { useParams } from "react-router-dom";

const HotelRoomManagement = () => {
  const [rooms, setRooms] = useState([]);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
  const [selectedRoomId, setSelectedRoomId] = useState(null);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);

  const { hotelId } = useParams();
  useEffect(() => {
    const fetchRooms = async () => {
      try {
        const roomsData = await getRoomsByHotelId(hotelId);
        setRooms(roomsData)
      } catch (error) {
        console.error(error)
      }
    }

    fetchRooms()
  }, [hotelId])

  const handleAddRoom = (newRoom) => {
    setRooms([...rooms, newRoom])
    setIsAddModalOpen(false)
  }

  const handleOpenAddModal = () => {
    setIsAddModalOpen(true)
  }

  const handleCloseAddModal = () => {
    setIsAddModalOpen(false)
  }

  const handleOpenUpdateModal = (roomId) => {
    setSelectedRoomId(roomId)
    setIsUpdateModalOpen(true)
  }

  const handleCloseUpdateModal = () => {
    setIsUpdateModalOpen(false)
  }

  const handleUpdateRoom = (updatedRoom) => {
    const updatedRooms = rooms.map((room) =>
      room.id === updatedRoom.id ? updatedRoom : room
    )
    setRooms(updatedRooms)
    setIsUpdateModalOpen(false)
  }

  const handleOpenDeleteModal = (roomId) => {
    setSelectedRoomId(roomId);
    setIsDeleteModalOpen(true)
  }

  const handleCloseDeleteModal = () => {
    setIsDeleteModalOpen(false)
  }

  const handleDeleteRoom = (roomId) => {
    setRooms(rooms.filter((room) => room.id !== roomId))
    setIsDeleteModalOpen(false)
  }

  return (
    <div className="roomListContainer">
      <h1>Quản lý phòng</h1>
      <button className="addButton" onClick={handleOpenAddModal}>
        Thêm phòng mới
      </button>
      <table className="roomListTable">
        <thead>
          <tr>
            <th>Loại phòng</th>
            <th>Miêu tả</th>
            <th>Giá tiền</th>
            <th>Đánh giá</th>
            <th>Hành động</th>
          </tr>
        </thead>
        <tbody>
          {rooms.map((room, index) => (
            <tr key={index}>
              <td>{room.roomType}</td>
              <td>{room.roomDescription}</td>
              <td>{room.roomPrice}</td>
              <td>{room.averageNumberOfRoomStars} / 5</td>
              <td>
                <button
                  className="editButton"
                  onClick={() => handleOpenUpdateModal(room.id)}
                >
                  Chỉnh sửa
                </button>
                <button
                  className="deleteButton"
                  onClick={() => handleOpenDeleteModal(room.id)}
                >
                  Xóa
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {isAddModalOpen && (
        <AddRoom handleAddRoom={handleAddRoom} onClose={handleCloseAddModal} />
      )}
      {isUpdateModalOpen && selectedRoomId && (
        <UpdateRoom
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
  );
};

export default HotelRoomManagement;
