import React, { useEffect, useState } from "react";
import "./userProfile.css";
import {
  getUserByEmail,
  getBookingsByUserId,
  deleteUser,
} from "../../components/utils/ApiFunction";
import { useNavigate } from "react-router-dom";

const UserProfile = () => {
  const [user, setUser] = useState({
    id: "",
    email: "",
    firstName: "",
    lastName: "",
    phoneNumber: "",
    roles: [{ id: "", name: "" }],
  });

  const [bookings, setBookings] = useState([
    {
      id: "",
      room: { id: "", roomType: "" },
      checkInDate: "",
      checkOutDate: "",
      bookingConfirmationCode: "",
    },
  ]);
  const [message, setMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const userEmail = localStorage.getItem("userEmail");
  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const userData = await getUserByEmail(userEmail, token);
        console.log("userData: ", userData)
        setUser(userData);
      } catch (error) {
        console.error(error);
      }
    };

    fetchUser();
  }, [userEmail]);

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const response = await getBookingsByUserId(userId, token);
        setBookings(response);
      } catch (error) {
        console.error("Error fetching bookings:", error.message);
        setErrorMessage(error.message);
      }
    };

    fetchBookings();
  }, [userEmail]);

  const handleDeleteAccount = async () => {
    const confirmed = window.confirm(
      "Are you sure you want to delete your account? This action cannot be undone."
    );
    if (confirmed) {
      await deleteUser(userEmail)
        .then((response) => {
          setMessage(response.data);
          localStorage.removeItem("token");
          localStorage.removeItem("userId");
          localStorage.removeItem("userRole");
          navigate("/");
          window.location.reload();
        })
        .catch((error) => {
          setErrorMessage(error.data);
        });
    }
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
            <h2>
              {user.firstName} {user.lastName}
            </h2>
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
                <strong>vai trò:</strong>{" "}
                <span>
                  <ul className="rolesList">
                  {user.roles.map((role) => (
                    <li key={role.id}>
                      {role.name}
                    </li>
                  ))}
                  </ul>
                  </span>
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
              <p>Bạn đã đặt phòng chưa?.</p>
            )}
          </div>
        </div>
      ) : (
        <p>Đang lấy dữ liệu người dùng...</p>
      )}
    </div>
  );
};

export default UserProfile;
