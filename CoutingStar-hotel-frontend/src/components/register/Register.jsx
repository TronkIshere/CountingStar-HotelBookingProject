import React, { useState } from "react";
import "./register.css";
import { registerUser } from "../utils/ApiFunction";

const Register = () => {
  const [registration, setRegistration] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    phoneNumber: "",
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
      console.log(registration);
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
      console.error("Registration error:", error);
      let errorMessage = "Registration error: ";
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
    <form id="register" className="register" onSubmit={handleRegistration}>
      <h1 className="registerTitle">Save time, save money!</h1>
      <span className="registerDesc">Đăng ký và hưởng thêm ưu đãi</span>
      <div className="registerInputContainer">
        <div className="row">
          <div className="col-12 col-sm-6">
            <input
              id="email"
              className="form-control"
              type="email"
              placeholder="Nhập Email"
              name="email"
              value={registration.email}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="col-12 col-sm-6">
            <input
              className="form-control"
              type="password"
              placeholder="Nhập mật khẩu"
              name="password"
              value={registration.password}
              onChange={handleInputChange}
              required
            />
          </div>
        </div>

        <div className="row mt-3">
          <div className="col-12 col-sm-6">
            <input
              className="form-control"
              type="text"
              placeholder="Nhập họ và tên đệm"
              name="lastName"
              value={registration.lastName}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="col-12 col-sm-6">
            <input
              className="form-control"
              type="text"
              placeholder="Nhập số điện thoại"
              name="phoneNumber"
              value={registration.phoneNumber}
              onChange={handleInputChange}
              required
            />
          </div>
        </div>

        <div className="row mt-3">
          <div className="col-12 col-sm-6 col-md-6">
            <input
              className="form-control"
              type="text"
              placeholder="Nhập tên"
              name="firstName"
              value={registration.firstName}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="col-12 col-sm-6 col-md-6">
            <button className="registerButton white-btn" type="submit">
              Đăng ký
            </button>
          </div>
        </div>
      </div>

      {errorMessage && <div className="errorMessage">{errorMessage}</div>}
      {successMessage && <div className="successMessage">{successMessage}</div>}
    </form>
  );
};

export default Register;
