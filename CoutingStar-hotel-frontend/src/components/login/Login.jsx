import React, { useContext, useState } from "react";
import "./login.css";
import { useNavigate } from "react-router-dom";
import { loginUser } from '../utils/ApiFunction';
import { AuthContext } from "../utils/AuthProvider";

const Login = ({ onClose }) => {
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
      navigate("/");
    } else {
      setErrorMessage("Invalid username or password. Please try again.");
    }
    setTimeout(() => {
      setErrorMessage("");
    }, 4000);
  };

  return (
    <div className="modal_overlay" onClick={onClose}>
      <form
        onSubmit={handleSubmit}
        className="signInForm"
        onClick={(e) => e.stopPropagation()}
      >
        <h1>Đăng nhập</h1>
        <input
          type="email"
          name="email"
          value={login.email}
          onChange={handleInputChange}
          placeholder="Nhập Email"
        />
        <input
          type="password"
          name="password"
          value={login.password}
          onChange={handleInputChange}
          placeholder="Nhập mật khẩu"
        />
        <button type="submit">Đăng nhập</button>
        {errorMessage && <div className="error">{errorMessage}</div>}
      </form>
    </div>
  );
};

export default Login;
