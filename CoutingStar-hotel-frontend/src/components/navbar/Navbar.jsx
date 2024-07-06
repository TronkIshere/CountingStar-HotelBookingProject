import React, { useContext, useState } from "react";
import "./navbar.css";
import { Link } from "react-scroll";
import { AuthContext } from "../utils/AuthProvider";

const Navbar = ({ onLoginClick, onRegisterClick }) => {
  const [showAccount, setShowAccount] = useState(false);
  const { user } = useContext(AuthContext);

  const handleAccountClick = () => {
    setShowAccount(!showAccount);
  };

  const isLoggedIn = user !== null;
  const userRole = localStorage.getItem("userRole");

  return (
    <div className="navbar">
      <div className="navContainer">
        <span className="logo">CoutingStar</span>
        <div className="navItems">

          {isLoggedIn ? (
            <div>Đã đăng nhập</div>
          ) : (
            <>
              <button className="postHotelButton">Đăng phòng của bạn</button>
              <button className="navButton" onClick={onRegisterClick}>
                <Link to="register" smooth={true} duration={500}>
                  Đăng ký
                </Link>
              </button>
              <button onClick={onLoginClick} className="navButton">
                Đăng nhập
              </button>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default Navbar;
