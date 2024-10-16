import React, { useState } from "react";
import "./userDelete.css";
import { toast } from "react-toastify";
import { deleteUser } from "../../../../utils/ApiFunction";

const UserDelete = ({ userId }) => {
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleDelete = async () => {
    try {
      await deleteUser(userId);
      setSuccessMessage("Người dùng đã được xóa thành công!");
      setErrorMessage("");
      toast.success("Người dùng đã được xóa thành công!");
    } catch (error) {
      setErrorMessage("Lỗi khi xóa người dùng.");
      setSuccessMessage("");
      toast.error("Lỗi khi xóa người dùng.");
    }
  };

  return (
    <div
      className="userDelete modal fade"
      id="deleteUserModel"
      tabIndex="-1"
      aria-labelledby="deleteUserModalLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="deleteUserModalLabel">
              Xóa người dùng
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>

          <div className="modal-body">
            <h3>Bạn có chắc muốn xóa người dùng với Id là {userId} không?</h3>
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
              className="btn btn-danger"
              onClick={handleDelete}
            >
              Xóa
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserDelete;
