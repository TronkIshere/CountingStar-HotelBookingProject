import React, { useContext, useState } from "react";
import { AuthContext } from "../utils/AuthProvider";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUserCircle } from "@fortawesome/free-solid-svg-icons";
import { useNavigate, Link as RouterLink } from "react-router-dom";
import "./navbar.css";

const Navbar = ({ onLoginClick, onRegisterClick }) => {
  const [showAccount, setShowAccount] = useState(false);
  const navigate = useNavigate();
  const auth = useContext(AuthContext);

  const handleAccountClick = () => {
    setShowAccount(!showAccount);
  };

  const handleLogout = () => {
    auth.handleLogout();
    navigate("/", { state: { message: "You have been logged out!" } });
  };

  const userRole = localStorage.getItem("userRole");
  const userId = localStorage.getItem("userId");
  const hotelId = localStorage.getItem("userHotelId");

  return (
    <header>
      <nav className="navbar navbar-expand-lg navigation-wrap">
        <div className="container">
          <a className="navbar-brand" href="/">
            <h1 className="navbar-brand-logo">CountingStar</h1>
          </a>
          <div className="d-flex align-items-center">
            {userId ? (
              <div className="dropdown">
                <button
                  className="btn"
                  id="accountDropdown"
                  onClick={handleAccountClick}
                  aria-expanded={showAccount}
                >
                  <FontAwesomeIcon icon={faUserCircle} className="userIcon" />
                </button>
                <ul
                  className={`dropdown-menu dropdown-menu-right ${showAccount ? "show-dropdown" : ""}`}
                  aria-labelledby="accountDropdown"
                >
                  <li>
                    <RouterLink className="dropdown-item" to={`/user/${userId}`}>
                      Người dùng
                    </RouterLink>
                  </li>
                  {userRole === "ROLE_HOTEL_OWNER" && (
                    <>
                      <li>
                        <RouterLink className="dropdown-item" to={`hotels/hotel/${hotelId}`}>
                          Khách sạn của bạn
                        </RouterLink>
                      </li>
                      <li>
                        <RouterLink className="dropdown-item" to={`/hotel/${hotelId}/hotelRoomManagement`}>
                          Quản lý phòng
                        </RouterLink>
                      </li>
                      <li>
                        <RouterLink className="dropdown-item" to={`/hotel/${hotelId}/hotelBookingManagement`}>
                          Quản lý đặt phòng
                        </RouterLink>
                      </li>
                      <li>
                        <RouterLink className="dropdown-item" to={`/hotel/${hotelId}/hotelInformationManagement`}>
                          Quản lý khách sạn
                        </RouterLink>
                      </li>
                      <li>
                        <RouterLink className="dropdown-item" to={`/hotel/hotelOwner`}>
                          Xem doanh thu
                        </RouterLink>
                      </li>
                    </>
                  )}
                  <li>
                    <button className="dropdown-item" onClick={handleLogout}>
                      Đăng xuất
                    </button>
                  </li>
                </ul>
              </div>
            ) : (
              <>
                <RouterLink to={`/hotelRegistration`} className="hotelRegistration-btn me-4">
                  Đăng phòng của bạn
                </RouterLink>
                <button className="white-btn me-3 nav-btn" onClick={onRegisterClick}>
                  Đăng ký
                </button>
                <button className="white-btn nav-btn" onClick={onLoginClick}>
                  Đăng nhập
                </button>
              </>
            )}
          </div>
        </div>
      </nav>
    </header>
  );
};

export default Navbar;
