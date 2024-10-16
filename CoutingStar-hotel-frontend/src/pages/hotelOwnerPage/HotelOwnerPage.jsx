import React, { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./hotelOwnerPage.css";
import HotelDashBoard from "../../components/hotelManagementComponent/hotelDashBroad/HotelDashBroard";
import { AuthContext } from "../../components/utils/AuthProvider";
import HotelRoomManagement from "../../components/hotelManagementComponent/hotelRoomManagement/HotelRoomManagement";

const HotelOwnerPage = () => {
  const [activeComponent, setActiveComponent] = useState(null);
  const navigate = useNavigate();
  const { user } = useContext(AuthContext);
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    if (!userId) {
      navigate("/");
    }
  }, [userId, navigate]);

  const renderComponent = () => {
    switch (activeComponent) {
      case "hotelDashBoard":
        return <HotelDashBoard />;
      case "hotelRoomManagement":
        return <HotelRoomManagement />;
      default:
        return <HotelDashBoard />;
    }
  };
  return (
    <div className="hotelOwnerPage my-4">
      <div className="container-fluid">
        <div className="row">
          <div className="col-12 col-sm-12 col-md-2 col-lg-2">
            <ul className="sidebar-list flex-grow-1">
              <div className="sidebar-list-header">Quản lý</div>
              <li
                className="sidebar-item"
                onClick={() => setActiveComponent("hotelDashBoard")}
              >
                Bảng Tổng quan
              </li>
              <li
                className="sidebar-item"
                onClick={() => setActiveComponent("hotelBookingManagement")}
              >
                Quản lý đặt phòng
              </li>
              <li
                className="sidebar-item"
                onClick={() => setActiveComponent("hotelIfnomationManagement")}
              >
                Quản lý thông tin khách sạn
              </li>
              <li
                className="sidebar-item"
                onClick={() => setActiveComponent("hotelRoomManagement")}
              >
                Quản lý phòng
              </li>
            </ul>
          </div>
          <div className="col-12 col-sm-12 col-md-10 col-lg-10">
            {renderComponent()}
          </div>
        </div>
      </div>
    </div>
  );
};

export default HotelOwnerPage;
