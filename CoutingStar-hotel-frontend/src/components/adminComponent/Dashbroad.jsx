import React, { useEffect, useState } from "react";
import "./dashbroad.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faBed,
  faHouse,
  faPencil,
  faUserCircle,
} from "@fortawesome/free-solid-svg-icons";
import BarChart from "../dashBoard/BarChart";
import {
  getDataForDashBoardMonthIncreased,
  getDataForAdminPieChart,
} from "../utils/ApiFunction";
import PieChart from "../dashBoard/PieChart";

const DashBoard = () => {
  const [dashboardData, setDashboardData] = useState(null);
  const [data, setData] = useState([]);

  useEffect(() => {
    const PieChartData = async () => {
      try {
        const response = await getDataForAdminPieChart();
        const formattedData = response.map((item) => ({
          id: item.cityName,
          label: item.cityName,
          value: item.quantity,
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
    <div className="dashboard">
      <h1>Bảng doanh thu</h1>

      <div className="adminOverViewThisMonth">
        <div className="row">
          <div className="col-12 col-sm-6 col-md-3 col-lg-3">
            <div className="adminOverViewThisMonthItem">
              <div className="adminOverViewInfo">
                <FontAwesomeIcon icon={faUserCircle} />
                <div className="adminInfoNumber">
                  {dashboardData.totalNumberOfUsers}
                </div>
                <div className="adminInfoText">Tổng số tài khoản</div>
              </div>
              <div className="adminOverViewNumber">
                +
                {dashboardData.percentageOfUsersIncreasedDuringTheMonth.toFixed(
                  2
                )}
                %
              </div>
            </div>
          </div>

          <div className="col-12 col-sm-6 col-md-3 col-lg-3">
            <div className="adminOverViewThisMonthItem">
              <div className="adminOverViewInfo">
                <FontAwesomeIcon icon={faHouse} />
                <div className="adminInfoNumber">
                  {dashboardData.totalNumberOfHotels}
                </div>
                <div className="adminInfoText">Tổng số khách sạn</div>
              </div>
              <div className="adminOverViewNumber">
                +
                {dashboardData.percentageOfHotelsIncreasedDuringTheMonth.toFixed(
                  2
                )}
                %
              </div>
            </div>
          </div>

          <div className="col-12 col-sm-6 col-md-3 col-lg-3">
            <div className="adminOverViewThisMonthItem">
              <div className="adminOverViewInfo">
                <FontAwesomeIcon icon={faBed} />
                <div className="adminInfoNumber">
                  {dashboardData.totalNumberOfBookedRooms}
                </div>
                <div className="adminInfoText">Tổng số phòng đã đặt</div>
              </div>
              <div className="adminOverViewNumber">
                +
                {dashboardData.percentageOfBookedRoomsIncreasedDuringTheMonth.toFixed(
                  2
                )}
                %
              </div>
            </div>
          </div>

          <div className="col-12 col-sm-6 col-md-3 col-lg-3">
            <div className="adminOverViewThisMonthItem">
              <div className="adminOverViewInfo">
                <FontAwesomeIcon icon={faPencil} />
                <div className="adminInfoNumber">
                  {dashboardData.totalNumberOfComments}
                </div>
                <div className="adminInfoText">Bình luận đã được viết</div>
              </div>
              <div className="adminOverViewNumber">
                +
                {dashboardData.percentageOfCommentsIncreaseDuringTheMonth.toFixed(
                  2
                )}
                %
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="adminChartsGroup">
        <div className="row">
          <div className="col-12 col-sm-12 col-md-6 col-lg-6">
            <div className="adminChartContainer">
              <p>Doanh thu của từng thành phố</p>
              <BarChart />
            </div>
          </div>
          <div className="col-12 col-sm-12 col-md-6 col-lg-6">
            <div className="adminChartContainer">
              <p>Tổng số khách sạn của từng thành phố</p>
              <PieChart data={data} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DashBoard;
