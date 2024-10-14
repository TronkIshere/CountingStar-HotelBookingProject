import React, { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faBed,
  faHouse,
  faPaperPlane,
  faPencil,
  faUserCircle,
} from "@fortawesome/free-solid-svg-icons";
import PieChart from "../../components/dashBoard/PieChart";
import "./hotelDashBoard.css";
import {
  getDataForHotelDashBoardMonthIncreased,
  getDataForHotelOwnerPieChart,
} from "../../components/utils/ApiFunction";

const HotelDashBoard = () => {
  const userHotelId = localStorage.getItem("userHotelId");
  const [data, setData] = useState([]);
  const [dashboardData, setDashboardData] = useState(null);

  useEffect(() => {
    const PieChartData = async () => {
      try {
        const response = await getDataForHotelOwnerPieChart(userHotelId);
        const formattedData = response.map((item) => ({
          id: item.roomName,
          label: item.roomName,
          value: item.roomRevenue,
          color: `hsl(${Math.floor(Math.random() * 360)}, 70%, 50%)`,
        }));
        setData(formattedData);
      } catch (error) {
        console.error("Error fetching Data:", error.message);
      }
    };

    PieChartData();
  }, []);

  useEffect(() => {
    const fetchDashBoardData = async () => {
      try {
        const dataForHotelOwnerPieChart =
          await getDataForHotelDashBoardMonthIncreased(userHotelId);
        console.log(dataForHotelOwnerPieChart);
        setDashboardData(dataForHotelOwnerPieChart);
      } catch (error) {
        console.log(error);
      }
    };

    fetchDashBoardData();
  }, []);

  return (
    <div className="hotelOwnerContainer">
      <h1>DashBoard Chủ Khách Sạn</h1>
      <div className="hotelOwnerContainerSpilt">
        {/* Overview Section on the left */}
        <div className="hotelOwnerOverViewThisMonth">
          <div className="hotelOwnerOverViewThisMonthItem">
            <div className="hotelOwnerOverViewInfo">
              <FontAwesomeIcon icon={faBed} />
              <div className="hotelOwnerInfoNumber">
                {dashboardData.totalBookedRoomInSpecificHotel}
              </div>
              <div className="hotelOwnerInfoText">Tổng số phòng đã đặt</div>
            </div>
            <div className="hotelOwnerOverViewNumber">
              +{dashboardData.percentageOfBookedIncreasedDuringTheMonth}%
            </div>
          </div>

          <div className="hotelOwnerOverViewThisMonthItem">
            <div className="hotelOwnerOverViewInfo">
              <FontAwesomeIcon icon={faPencil} />
              <div className="hotelOwnerInfoNumber">
                {dashboardData.totalRatingInSpecificHotel}
              </div>
              <div className="hotelOwnerInfoText">Tổng bình luận</div>
            </div>
            <div className="hotelOwnerOverViewNumber">
              +{dashboardData.percentageOfRatingIncreasedDuringTheMonth}%
            </div>
          </div>

          <div className="hotelOwnerOverViewThisMonthItem">
            <div className="hotelOwnerOverViewInfo">
              <FontAwesomeIcon icon={faPaperPlane} />
              <div className="hotelOwnerInfoNumber">
                {dashboardData.totalRevenueInSpecificHotel.toLocaleString(
                  "vi-VN",
                  { style: "currency", currency: "VND" }
                )}
              </div>
              <div className="hotelOwnerInfoText">Tổng số Doanh Thu</div>
            </div>
            <div className="hotelOwnerOverViewNumber">
              +{dashboardData.percentageOfRevenueIncreasedDuringTheMonth}%
            </div>
          </div>
        </div>

        {/* Chart Section on the right */}
        <div className="hotelOwnerChartsGroup">
          <div className="hotelOwnerChartContainer">
            <p>Tổng số khách sạn của từng thành phố</p>
            <PieChart data={data} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default HotelDashBoard;
