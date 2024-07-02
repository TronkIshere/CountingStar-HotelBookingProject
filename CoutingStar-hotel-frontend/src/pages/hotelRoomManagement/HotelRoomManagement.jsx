import React, { useState } from "react";
import "./hotelRoomManagement.css";
import AddRoom from "../../components/room/addRoom/AddRoom";
import UpdateRoom from "../../components/room/updateRoom/UpdateRoom";
import DeleteRoom from "../../components/room/deleteRoom/DeleteRoom";

const HotelRoomManagement = () => {
  const [rooms, setRooms] = useState([
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
  ]);

  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
  const [selectedRoom, setSelectedRoom] = useState(null);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);

  const handleAddRoom = (newRoom) => {
    setRooms([...rooms, newRoom]);
    setIsAddModalOpen(false);
  };

  const handleOpenAddModal = () => {
    setIsAddModalOpen(true);
  };

  const handleCloseAddModal = () => {
    setIsAddModalOpen(false);
  };

  const handleOpenUpdateModal = (room) => {
    setSelectedRoom(room);
    setIsUpdateModalOpen(true);
  };

  const handleCloseUpdateModal = () => {
    setIsUpdateModalOpen(false);
  };

  const handleUpdateRoom = (updatedRoom) => {
    const updatedRooms = rooms.map((room) =>
      room.type === updatedRoom.type ? updatedRoom : room
    );
    setRooms(updatedRooms);
    setIsUpdateModalOpen(false);
  };

  const handleOpenDeleteModal = (room) => {
    setSelectedRoom(room);
    setIsDeleteModalOpen(true);
  };

  const handleCloseDeleteModal = () => {
    setIsDeleteModalOpen(false);
  };

  const handleDeleteRoom = (roomToDelete) => {
    setRooms(rooms.filter((room) => room.type !== roomToDelete.type));
    setIsDeleteModalOpen(false);
  };

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
              <td>{room.type}</td>
              <td>{room.description}</td>
              <td>{room.price}</td>
              <td>{room.rating}</td>
              <td>
                <button
                  className="editButton"
                  onClick={() => handleOpenUpdateModal(room)}
                >
                  Chỉnh sửa
                </button>
                <button
                  className="deleteButton"
                  onClick={() => handleOpenDeleteModal(room)}
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
      {isUpdateModalOpen && selectedRoom && (
        <UpdateRoom
          room={selectedRoom}
          handleUpdateRoom={handleUpdateRoom}
          onClose={handleCloseUpdateModal}
        />
      )}
      {isDeleteModalOpen && selectedRoom && (
        <DeleteRoom
          room={selectedRoom}
          handleDeleteRoom={handleDeleteRoom}
          onClose={handleCloseDeleteModal}
        />
      )}
    </div>
  );
};

export default HotelRoomManagement;
