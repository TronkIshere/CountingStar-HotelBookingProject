import React from "react";
import LineChart from "../../components/dashBoard/LineChart";
import "./admin.css";
import PieChart from "../../components/dashBoard/PieChart";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBed, faHouse, faPencil, faUserCircle } from "@fortawesome/free-solid-svg-icons";

const Admin = () => {
  return (
    <div className="container">
      <h1>CoutingStar DashBoard</h1>

      <div className="overViewThisMonth">
        <div className="overViewThisMonthItem">
          <div className="overViewInfo">
            <FontAwesomeIcon icon={faUserCircle} />
            <div className="infoNumber">12,500</div>
            <div className="infoText">Tổng số tài khoản</div>
          </div>
          <div className="overViewNumber">
            +15%
          </div>
        </div>

        <div className="overViewThisMonthItem">
          <div className="overViewInfo">
            <FontAwesomeIcon icon={faHouse} />
            <div className="infoNumber">1,200</div>
            <div className="infoText">Tổng số khách sạn</div>
          </div>
          <div className="overViewNumber">
            +12%
          </div>
        </div>

        <div className="overViewThisMonthItem">
          <div className="overViewInfo">
            <FontAwesomeIcon icon={faBed} />
            <div className="infoNumber">12,200</div>
            <div className="infoText">Tổng số phòng đã đặt</div>
          </div>
          <div className="overViewNumber">
            +1,2%
          </div>
        </div>

        <div className="overViewThisMonthItem">
          <div className="overViewInfo">
            <FontAwesomeIcon icon={faPencil} />
            <div className="infoNumber">556</div>
            <div className="infoText">Bài viết đã được đăng</div>
          </div>
          <div className="overViewNumber">
            +1,2%
          </div>
        </div>
      </div>

      <div className="chartsGroup">
        <div className="chartContainer">
          <p>Doanh thu của từng thành phố</p>
          <LineChart />
        </div>
        <div className="chartContainer">
          <p>Tổng số khách sạn của từng thành phố</p>
          <PieChart />
        </div>
      </div>
    </div>
  );
};

export default Admin;
