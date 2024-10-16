import React, { useContext, useState } from "react";
import { AuthContext } from "../utils/AuthProvider";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUserCircle } from "@fortawesome/free-solid-svg-icons";
import { useNavigate, Link as RouterLink } from "react-router-dom";
import { Link as ScrollLink } from "react-scroll";
import "./navbar.css";
import Login from "../login/Login";

const Navbar = () => {
  const [showAccount, setShowAccount] = useState(false);
  const [isNavbarOpen, setIsNavbarOpen] = useState(false);
  const navigate = useNavigate();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleOpenModal = () => {
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

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

  const toggleNavbar = () => {
    setIsNavbarOpen((prev) => !prev);
  };

  return (
    <header>
      <nav className="navbar navbar-expand-lg navigation-wrap">
        <div className="container">
          <a className="navbar-brand" href="/">
            <h1 className="navbar-brand-logo">CountingStar</h1>
          </a>
          <button
            className="navbar-toggler"
            type="button"
            aria-controls="navbarSupportedContent"
            aria-expanded={isNavbarOpen}
            aria-label="Toggle navigation"
            onClick={toggleNavbar}
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div
            className={`collapse navbar-collapse ${isNavbarOpen ? "show" : ""}`}
            id="navbarSupportedContent"
          >
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              {userRole === "ROLE_ADMIN" && (
                <li className="nav-item">
                  <RouterLink className="nav-link admin-btn" to={`/user/admin`}>
                    Admin
                  </RouterLink>
                </li>
              )}
            </ul>

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
                    className={`dropdown-menu dropdown-menu-right ${
                      showAccount ? "show-dropdown" : ""
                    }`}
                    aria-labelledby="accountDropdown"
                  >
                    <li>
                      <RouterLink
                        className="dropdown-item"
                        to={`/user/${userId}`}
                      >
                        Người dùng
                      </RouterLink>
                    </li>
                    {userRole === "ROLE_HOTEL_OWNER" && (
                      <>
                        <li>
                          <RouterLink
                            className="dropdown-item"
                            to={`hotels/hotel/${hotelId}`}
                          >
                            Khách sạn của bạn
                          </RouterLink>
                        </li>
                        <li>
                          <RouterLink
                            className="dropdown-item"
                            to={`/hotels/hotelOwnerPage/${hotelId}`}
                          >
                            Quản lý khách sạn
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
                  <RouterLink
                    to={`/hotelRegistration`}
                    className="hotelRegistration-btn me-4"
                  >
                    Đăng phòng của bạn
                  </RouterLink>
                  <button className="white-btn me-3 nav-btn">Đăng ký</button>
                  <button onClick={handleOpenModal} className="white-btn nav-btn">
                    Đăng nhập
                  </button>
                  {isModalOpen && <Login onClose={handleCloseModal} />}
                </>
              )}
            </div>
          </div>
        </div>
      </nav>
    </header>
  );
};

export default Navbar;
