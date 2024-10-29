import React, { useEffect, useState } from "react";
import "./userProfile.css";
import {
  getUserByEmail,
  getBookingsByUserId,
  deleteUser,
} from "../../components/utils/ApiFunction";
import { useNavigate } from "react-router-dom";
import { Pagination } from "react-bootstrap";

const UserProfile = () => {
  const [user, setUser] = useState({
    id: "",
    email: "",
    firstName: "",
    lastName: "",
    phoneNumber: "",
    roles: [{ name: "" }],
  });

  const [bookings, setBookings] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [message, setMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const userEmail = localStorage.getItem("userEmail");
  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const userData = await getUserByEmail(userEmail);
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
        const response = await getBookingsByUserId(currentPage, 8, userId);
        setBookings(response.content);
        setTotalPages(response.totalPages);
      } catch (error) {
        console.error("Error fetching bookings:", error.message);
        setErrorMessage(error.message);
      }
    };

    fetchBookings();
  }, [userId, currentPage]);

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

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  return (
    <div className="container-fluid my-4">
      {errorMessage && <p className="text-danger">{errorMessage}</p>}
      {message && <p className="text-danger">{message}</p>}
      {user ? (
        <div className="row">
          <div className="col-12 col-sm-12 col-md-4 col-lg-4">
            <div className="profile w-100">
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
                  <strong>Số điện thoại:</strong>{" "}
                  <span>{user.phoneNumber}</span>
                </div>
                <div className="profile-row">
                  <strong>Vai trò:</strong>
                  <span>
                    <ul className="rolesList">
                      {user.roles.map((role) => (
                        <li key={role.id}>{role.name}</li>
                      ))}
                    </ul>
                  </span>
                </div>
              </div>
              <button className="bookButton" onClick={handleDeleteAccount}>
                Đóng tài khoản
              </button>
            </div>
          </div>

          <div className="col-12 col-sm-12 col-md-8 col-lg-8">
            <div className="booking-history w-100">
              <h5>Lịch sử đặt phòng</h5>
              {bookings.length > 0 ? (
                <table className="roomListTable mb-3">
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
              <Pagination>
                {[...Array(totalPages)].map((_, index) => (
                  <Pagination.Item
                    key={index}
                    active={index === currentPage}
                    onClick={() => handlePageChange(index)}
                  >
                    {index + 1}
                  </Pagination.Item>
                ))}
              </Pagination>
            </div>
          </div>
        </div>
      ) : (
        <p>Đang lấy dữ liệu người dùng...</p>
      )}
    </div>
  );
};

export default UserProfile;
