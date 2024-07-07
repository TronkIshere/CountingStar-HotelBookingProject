import React, { useState } from "react";
import "./register.css";
import { registerUser } from "../utils/ApiFunction";

const Register = () => {
  const [registration, setRegistration] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    phoneNumber: ""
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const handleInputChange = (e) => {
    setRegistration({ ...registration, [e.target.name]: e.target.value });
  };

  const handleRegistration = async (e) => {
    e.preventDefault();
    try {
      const result = await registerUser(registration);
      setSuccessMessage(result);
      setErrorMessage("");
      setRegistration({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        phoneNumber: "",
      });
    } catch (error) {
      console.error('Registration error:', error);
      let errorMessage = 'Registration error: ';
      if (error.response && error.response.data) {
        errorMessage += JSON.stringify(error.response.data);
      } else {
        errorMessage += error.message;
      }
      setSuccessMessage("");
      setErrorMessage(errorMessage);
    }
    setTimeout(() => {
      setErrorMessage("");
      setSuccessMessage("");
    }, 5000);
  };

  return (
    <form id="register" className="mail" onSubmit={handleRegistration}>
      <h1 className="mailTitle">Save time, save money!</h1>
      <span className="mailDesc">Đăng ký và hưởng thêm ưu đãi</span>
      <div className="mailInputContainer">
        <input
          type="email"
          placeholder="Nhập Email"
          name="email"
          value={registration.email}
          onChange={handleInputChange}
          required
        />
        <input
          type="text"
          placeholder="Nhập mật khẩu"
          name="password"
          value={registration.password}
          onChange={handleInputChange}
          required
        />
        <input
          type="text"
          placeholder="Nhập họ và tên đệm"
          name="lastName"
          value={registration.lastName}
          onChange={handleInputChange}
          required
        />
        <input
          type="text"
          placeholder="Nhập số điện thoại"
          name="phoneNumber"
          value={registration.phoneNumber}
          onChange={handleInputChange}
          required
        />
        <input
          type="text"
          placeholder="Nhập tên"
          name="firstName"
          value={registration.firstName}
          onChange={handleInputChange}
          required
        />
        <button className="RegisterButton" type="submit">
          Đăng ký
        </button>
      </div>
      {errorMessage && <div className="errorMessage">{errorMessage}</div>}
      {successMessage && <div className="successMessage">{successMessage}</div>}
    </form>
  );
};

export default Register;