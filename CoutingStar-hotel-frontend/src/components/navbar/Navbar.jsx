import React from "react";
import "./navbar.css";
import { Link } from "react-scroll";

const Navbar = ({ onLoginClick, onRegisterClick }) => {
  return (
    <div className="navbar">
      <div className="navContainer">
        <span className="logo">CoutingStar</span>
        <div className="navItems">
          <button className="postHotelButton">Đăng phòng của bạn</button>
          <button className="navButton">
            <Link
              to="register"
              smooth={true}
              duration={500}
              
            >
              Đăng ký
            </Link>
          </button>
          <button onClick={onLoginClick} className="navButton">
            Đăng nhập
          </button>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
