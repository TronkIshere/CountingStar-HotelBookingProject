import React, { useEffect, useState } from "react";
import "./admin.css";
import PieChart from "../../components/dashBoard/PieChart";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBed, faHouse, faPencil, faUserCircle } from "@fortawesome/free-solid-svg-icons";
import BarChart from "../../components/dashBoard/BarChart";
import { getDataForDashBoardMonthIncreased } from "../../components/utils/ApiFunction";

const Admin = () => {
  const [dashboardData, setDashboardData] = useState(null);

  useEffect(() => {
    const fetchDashBoardData = async () => {
      try {
        const data = await getDataForDashBoardMonthIncreased();
        setDashboardData(data);
      } catch (error) {
        console.log(error);
      }
    };

    fetchDashBoardData();
  }, []);

  if (!dashboardData) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container">
      <h1>CoutingStar DashBoard</h1>

      <div className="overViewThisMonth">
        <div className="overViewThisMonthItem">
          <div className="overViewInfo">
            <FontAwesomeIcon icon={faUserCircle} />
            <div className="infoNumber">{dashboardData.totalNumberOfUsers}</div>
            <div className="infoText">Tổng số tài khoản</div>
          </div>
          <div className="overViewNumber">
            +{dashboardData.percentageOfUsersIncreasedDuringTheMonth.toFixed(2)}%
          </div>
        </div>

        <div className="overViewThisMonthItem">
          <div className="overViewInfo">
            <FontAwesomeIcon icon={faHouse} />
            <div className="infoNumber">{dashboardData.totalNumberOfHotels}</div>
            <div className="infoText">Tổng số khách sạn</div>
          </div>
          <div className="overViewNumber">
            +{dashboardData.percentageOfHotelsIncreasedDuringTheMonth.toFixed(2)}%
          </div>
        </div>

        <div className="overViewThisMonthItem">
          <div className="overViewInfo">
            <FontAwesomeIcon icon={faBed} />
            <div className="infoNumber">{dashboardData.totalNumberOfBookedRooms}</div>
            <div className="infoText">Tổng số phòng đã đặt</div>
          </div>
          <div className="overViewNumber">
            +{dashboardData.percentageOfBookedRoomsIncreasedDuringTheMonth.toFixed(2)}%
          </div>
        </div>

        <div className="overViewThisMonthItem">
          <div className="overViewInfo">
            <FontAwesomeIcon icon={faPencil} />
            <div className="infoNumber">{dashboardData.totalNumberOfComments}</div>
            <div className="infoText">Bình luận đã được viết</div>
          </div>
          <div className="overViewNumber">
            +{dashboardData.percentageOfCommentsIncreaseDuringTheMonth.toFixed(2)}%
          </div>
        </div>
      </div>

      <div className="chartsGroup">
        <div className="chartContainer">
          <p>Doanh thu của từng thành phố</p>
          <BarChart />
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
