import React, { useState, useEffect } from "react";
import "./userEdit.css";
import { getUserByUserId, updateUser } from "../../../../utils/ApiFunction"; 

const UserEdit = ({ userId }) => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    if (userId) {
      const fetchUser = async () => {
        try {
          const userData = await getUserByUserId(userId);
          setFirstName(userData.firstName);
          setLastName(userData.lastName);
          setEmail(userData.email);
          setPhoneNumber(userData.phoneNumber);
        } catch (error) {
          setErrorMessage("Lỗi khi tải thông tin người dùng.");
        }
      };
      fetchUser();
    }
  }, [userId]);

  const handleUpdate = async () => {
    try {
      await updateUser(userId, firstName, lastName, email, phoneNumber);
      setSuccessMessage("Người dùng đã được chỉnh sửa thành công!");
      setErrorMessage("");
    } catch (error) {
      setErrorMessage("Lỗi khi cập nhật thông tin người dùng.");
      setSuccessMessage("");
    }
  };

  return (
    <div
      className="userEdit modal fade"
      id="editUserModal"
      tabIndex="-1"
      aria-labelledby="editUserModalLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="editUserModalLabel">
              Sửa thông tin người dùng
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>

          <div className="modal-body">
            <div className="mb-3">
              <label htmlFor="firstName" className="form-label">
                Tên:
              </label>
              <input
                type="text"
                className="form-control"
                id="firstName"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="lastName" className="form-label">
                Họ:
              </label>
              <input
                type="text"
                className="form-control"
                id="lastName"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="email" className="form-label">
                Email:
              </label>
              <input
                type="email"
                className="form-control"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="phoneNumber" className="form-label">
                Số điện thoại:
              </label>
              <input
                type="tel"
                className="form-control"
                id="phoneNumber"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
              />
            </div>
          </div>

          {successMessage && (
            <div className="alert alert-success">{successMessage}</div>
          )}
          {errorMessage && (
            <div className="alert alert-danger">{errorMessage}</div>
          )}

          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Đóng
            </button>
            <button
              type="button"
              className="btn btn-primary"
              onClick={handleUpdate}
            >
              Cập nhật
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserEdit;
