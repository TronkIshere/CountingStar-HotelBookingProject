import React from "react";
import "./navbar.css";

const Navbar = ({ onLoginClick }) => {
  return (
    <div className="navbar">
      <div className="navContainer">
        <span className="logo">CoutingStar</span>
        <div className="navItems">
          <button className="postHotelButton">Đăng phòng của bạn</button>
          <button className="navButton">Đăng ký</button>
          <button onClick={onLoginClick} className="navButton">
            Đăng nhập
          </button>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
