import React, { useContext, useEffect, useState } from "react";
import "./adminPage.css";
import DashBoard from "../../components/adminComponent/dashboard/Dashboard";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../components/utils/AuthProvider";
import UserManagement from "../../components/adminComponent/userManagement/UserManagement";
import HotelManagement from "../../components/adminComponent/hotelManagement/HotelManagement";
import DiscountManagement from "../../components/adminComponent/discountManagement/DiscountManagement";

const Admin = () => {
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
      case "dashboard":
        return <DashBoard />;
      case "userManagement":
        return <UserManagement />;
      case "hotelManagement":
        return <HotelManagement />;
      case "discountManagement":
        return <DiscountManagement />;
      default:
        return <DashBoard />;
    }
  };

  return (
    <div className="adminPage my-4">
      <div className="container-fluid">
        <div className="row">
          <div className="col-12 col-sm-12 col-md-2 col-lg-2">
            <ul className="sidebar-list flex-grow-1">
              <div className="sidebar-list-header">Quản lý</div>
              <li
                className="sidebar-item"
                onClick={() => setActiveComponent("dashboard")}
              >
                Bảng doanh thu
              </li>
              <li
                className="sidebar-item"
                onClick={() => setActiveComponent("userManagement")}
              >
                Quản lý người dùng
              </li>
              <li
                className="sidebar-item"
                onClick={() => setActiveComponent("hotelManagement")}
              >
                Quản lý khách sạn
              </li>
              <li
                className="sidebar-item"
                onClick={() => setActiveComponent("discountManagement")}
              >
                Quản lý mã giảm giá
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

export default Admin;
