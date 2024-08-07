import React, { useContext, useState } from "react";
import "./navbar.css";
import { AuthContext } from "../utils/AuthProvider";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUserCircle } from "@fortawesome/free-solid-svg-icons";
import { useNavigate, Link as RouterLink } from "react-router-dom";
import { Link as ScrollLink } from "react-scroll";

const Navbar = ({ onLoginClick, onRegisterClick }) => {
  const [showAccount, setShowAccount] = useState(false);
  const navigate = useNavigate();

  const handleAccountClick = () => {
    setShowAccount(!showAccount);
  };

  const userRole = localStorage.getItem("userRole");
  const userId = localStorage.getItem("userId");
  const hotelId = localStorage.getItem("userHotelId");

  const auth = useContext(AuthContext);

  const handleLogout = () => {
    auth.handleLogout();
    navigate("/", { state: { message: "You have been logged out!" } });
  };

  return (
    <div className="navbar">
      <div className="navContainer">
        <span className="logo">
          <RouterLink className="logo" to={`/`}>
            CoutingStar
          </RouterLink>
        </span>
        <div className="navItems">
          {userId ? (
            <div className="accountContainer">
              <button className="userButton" onClick={handleAccountClick}>
                <FontAwesomeIcon icon={faUserCircle} />
              </button>
              {showAccount && (
                <div className="dropdown">
                  <ul>
                    <li className="navButtonLi">
                      <RouterLink to={`/user/${userId}`}>Người dùng</RouterLink>
                    </li>
                    {userRole === "ROLE_HOTEL_OWNER" && (
                      <>
                        <li className="navButtonLi">
                          <RouterLink
                            to={`hotels/hotel/${hotelId}`}
                          >
                            Khách sạn của bạn
                          </RouterLink>
                        </li>
                        <li className="navButtonLi">
                          <RouterLink
                            to={`/hotel/${hotelId}/hotelRoomManagement`}
                          >
                            Quản lý phòng
                          </RouterLink>
                        </li>
                        <li className="navButtonLi">
                          <RouterLink
                            to={`/hotel/${hotelId}/hotelBookingManagement`}
                          >
                            Quản lý đặt phòng
                          </RouterLink>
                        </li>
                        <li className="navButtonLi">
                          <RouterLink
                            to={`/hotel/${hotelId}/hotelInformationManagement`}
                          >
                            Quản lý khách sạn
                          </RouterLink>
                        </li>
                      </>
                    )}
                    <li className="navButtonLi">
                      <button onClick={handleLogout}>Đăng xuất</button>
                    </li>
                  </ul>
                </div>
              )}
            </div>
          ) : (
            <>
              <button className="postHotelButton">
                <RouterLink
                  className="hotelRegistrationButton"
                  to={`/hotelRegistration`}
                >
                  Đăng phòng của bạn
                </RouterLink>
              </button>
              <button className="navButton" onClick={onRegisterClick}>
                <ScrollLink
                  to="register"
                  smooth={true}
                  duration={500}
                  className="navLink"
                >
                  Đăng ký
                </ScrollLink>
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
