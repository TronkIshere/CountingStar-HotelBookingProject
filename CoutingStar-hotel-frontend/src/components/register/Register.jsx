import React from "react";
import "./register.css";

const Register = () => {
  return (
    <div className="mail">
      <h1 className="mailTitle">Save time, save money!</h1>
      <span className="mailDesc">Đăng ký và hưởng thêm ưu đãi</span>
      <div className="mailInputContainer">
        <input type="text" placeholder="Nhập Email" />
        <input type="text" placeholder="Nhập tên" />
        <input type="text" placeholder="Nhập họ" />
        <input type="text" placeholder="Nhập mật khẩu" />
        <input type="text" placeholder="Nhập số điện thoại" />
        <button className="RegisterButton">Đăng ký</button>
      </div>
    </div>
  );
};

export default Register;
