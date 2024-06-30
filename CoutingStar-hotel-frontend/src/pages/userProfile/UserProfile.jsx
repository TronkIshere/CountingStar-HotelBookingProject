import React, { useEffect, useState } from "react";
import "./userProfile.css";

const UserProfile = () => {
  const [user, setUser] = useState(null);
  const [errorMessage, setErrorMessage] = useState("");
  const [message, setMessage] = useState("");
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const fetchedUser = {
          id: "12345",
          firstName: "Nguyễn Hữu",
          lastName: "Trọng",
          email: "john.doe@example.com",
          phoneNumber: "0359256696",
          roles: "user",
        };
        const fetchedBookings = [
          {
            bookingId: "B123",
            room: { id: "R1", roomType: "Single" },
            checkInDate: "2024-06-01",
            checkOutDate: "2024-06-07",
            bookingConfirmationCode: "CONF123",
            status: "On-going",
          },
          {
            bookingId: "B124",
            room: { id: "R2", roomType: "Double" },
            checkInDate: "2024-07-01",
            checkOutDate: "2024-07-07",
            bookingConfirmationCode: "CONF124",
            status: "Completed",
          },
          {
            bookingId: "B125",
            room: { id: "R3", roomType: "Suite" },
            checkInDate: "2024-08-01",
            checkOutDate: "2024-08-07",
            bookingConfirmationCode: "CONF125",
            status: "Cancelled",
          },
          {
            bookingId: "B126",
            room: { id: "R4", roomType: "Single" },
            checkInDate: "2024-09-01",
            checkOutDate: "2024-09-07",
            bookingConfirmationCode: "CONF126",
            status: "On-going",
          },
          {
            bookingId: "B127",
            room: { id: "R5", roomType: "Double" },
            checkInDate: "2024-10-01",
            checkOutDate: "2024-10-07",
            bookingConfirmationCode: "CONF127",
            status: "Completed",
          },
          {
            bookingId: "B128",
            room: { id: "R6", roomType: "Suite" },
            checkInDate: "2024-11-01",
            checkOutDate: "2024-11-07",
            bookingConfirmationCode: "CONF128",
            status: "Cancelled",
          },
          {
            bookingId: "B129",
            room: { id: "R7", roomType: "Single" },
            checkInDate: "2024-12-01",
            checkOutDate: "2024-12-07",
            bookingConfirmationCode: "CONF129",
            status: "On-going",
          },
          {
            bookingId: "B130",
            room: { id: "R8", roomType: "Double" },
            checkInDate: "2024-12-01",
            checkOutDate: "2024-12-07",
            bookingConfirmationCode: "CONF130",
            status: "On-going",
          },
        ];
        setUser(fetchedUser);
        setBookings(fetchedBookings);
      } catch (error) {
        setErrorMessage("Error fetching user data");
      }
    };

    fetchUserData();
  }, []);

  const handleDeleteAccount = () => {
    alert("Account closed.");
  };

  return (
    <div className="container">
      {errorMessage && <p className="text-danger">{errorMessage}</p>}
      {message && <p className="text-danger">{message}</p>}
      {user ? (
        <div className="content">
          <div className="profile">
            <img
              src="https://themindfulaimanifesto.org/wp-content/uploads/2020/09/male-placeholder-image.jpeg"
              alt="Profile"
              className="profile-pic"
            />
            <h2>{user.firstName} {user.lastName}</h2>
            <div className="profile-info">
              <div className="profile-row">
                <strong>ID:</strong> <span>{user.id}</span>
              </div>
              <div className="profile-row">
                <strong>Email:</strong> <span>{user.email}</span>
              </div>
              <div className="profile-row">
                <strong>Số điện thoại:</strong> <span>{user.phoneNumber}</span>
              </div>
              <div className="profile-row">
                <strong>vai trò:</strong> <span>{user.roles}</span>
              </div>
            </div>
            <button className="bookButton" onClick={handleDeleteAccount}>
              Đóng tài khoản
            </button>
          </div>
          <div className="booking-history">
            <h4>Lịch sử đặt phòng</h4>
            {bookings.length > 0 ? (
              <table className="roomListTable">
                <thead>
                  <tr>
                    <th>ID phòng đã đặt</th>
                    <th>ID phòng</th>
                    <th>Loại phòng</th>
                    <th>Ngày đặt</th>
                    <th>Ngày trả phòng</th>
                    <th>Mã xác nhận</th>
                    <th>Trạng thái</th>
                  </tr>
                </thead>
                <tbody>
                  {bookings.map((booking, index) => (
                    <tr key={index}>
                      <td>{booking.bookingId}</td>
                      <td>{booking.room.id}</td>
                      <td>{booking.room.roomType}</td>
                      <td>
                        {new Date(booking.checkInDate).toLocaleDateString()}
                      </td>
                      <td>
                        {new Date(booking.checkOutDate).toLocaleDateString()}
                      </td>
                      <td>{booking.bookingConfirmationCode}</td>
                      <td className="text-success">{booking.status}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            ) : (
              <p>You have not made any bookings yet.</p>
            )}
          </div>
        </div>
      ) : (
        <p>Loading user data...</p>
      )}
    </div>
  );
};

export default UserProfile;
