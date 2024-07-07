import React, { useContext, useState } from "react";
import "./navbar.css";
import { Link } from "react-scroll";
import { AuthContext } from "../utils/AuthProvider";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser, faUserCircle } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";

const Navbar = ({ onLoginClick, onRegisterClick }) => {
  const [showAccount, setShowAccount] = useState(false);
  const { user } = useContext(AuthContext);

  const handleAccountClick = () => {
    setShowAccount(!showAccount);
  };

  const isLoggedIn = user !== null;
  const userRole = localStorage.getItem("userRole");

  const auth = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    auth.handleLogout();
    window.location.reload();
    navigate("/", { state: { message: " You have been logged out!" } });
  };

  return (
    <div className="navbar">
      <div className="navContainer">
        <span className="logo">CoutingStar</span>
        <div className="navItems">
          {isLoggedIn ? (
            <div className="accountContainer">
              <button className="userButton" onClick={handleAccountClick}>
                <FontAwesomeIcon icon={faUserCircle} />
              </button>
              {showAccount && (
                <div className="dropdown">
                  <ul>
                    <li>
                      <button>Người dùng</button>
                    </li>
                    {userRole === "ROLE_HOTEL_OWNER" && (
                      <li>
                        <button>Quản lý khách sạn</button>
                      </li>
                    )}
                    <li>
                      <button onClick={handleLogout}>Đăng xuất</button>
                    </li>
                  </ul>
                </div>
              )}
            </div>
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
