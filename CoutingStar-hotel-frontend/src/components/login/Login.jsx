import React, { useContext, useState } from "react";
import "./login.css";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../utils/ApiFunction";
import { AuthContext } from "../utils/AuthProvider";

const Login = ({ onClose }) => { // Nhận props onClose
  const [errorMessage, setErrorMessage] = useState("");
  const [login, setLogin] = useState({
    email: "",
    password: "",
  });

  const navigate = useNavigate();
  const { handleLogin } = useContext(AuthContext);

  const handleInputChange = (e) => {
    setLogin({ ...login, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const success = await loginUser(login);
    if (success) {
      const token = success.token;
      handleLogin(token);
      onClose(); // Gọi hàm onClose để đóng modal
      navigate("/"); // Điều hướng sau khi đăng nhập thành công
    } else {
      setErrorMessage("Invalid username or password. Please try again.");
    }
    setTimeout(() => {
      setErrorMessage("");
    }, 4000);
  };

  return (
    <div
      className="loginModal modal fade show" // Thêm class 'show' để đảm bảo modal hiển thị
      style={{ display: 'block' }} // Đặt display block khi modal hiển thị
      tabIndex="-1"
      aria-labelledby="loginModalLabel"
      aria-hidden="false" // Thay đổi thành false khi modal hiển thị
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <form onSubmit={handleSubmit}>
            <div className="modal-header">
              <h5 className="modal-title" id="loginModalLabel">
                Đăng nhập
              </h5>
              <button
                type="button"
                className="btn-close"
                onClick={onClose} // Gọi hàm onClose để đóng modal
                aria-label="Close"
              ></button>
            </div>

            <div className="modal-body">
              <div className="mb-3">
                <label htmlFor="email" className="form-label">
                  Email:
                </label>
                <input
                  type="email"
                  className="form-control"
                  id="email"
                  name="email"
                  value={login.email}
                  onChange={handleInputChange}
                  placeholder="Nhập Email"
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="password" className="form-label">
                  Mật khẩu:
                </label>
                <input
                  type="password"
                  className="form-control"
                  id="password"
                  name="password"
                  value={login.password}
                  onChange={handleInputChange}
                  placeholder="Nhập mật khẩu"
                  required
                />
              </div>
              {errorMessage && (
                <div className="error text-danger">{errorMessage}</div>
              )}
            </div>

            <div className="modal-footer">
              <button
                type="button"
                className="white-btn"
                onClick={onClose} // Gọi hàm onClose để đóng modal
                aria-label="Close"
              >
                Đóng
              </button>
              <button type="submit" className="main-btn">
                Đăng nhập
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Login;
