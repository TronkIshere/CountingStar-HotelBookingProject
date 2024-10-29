import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./featured.css";
import dalat from "../../../assets/featured-img/dalat.jpg";
import nhatrang from "../../../assets/featured-img/nhatrang.jpg";
import vungtau from "../../../assets/featured-img/vungtau.jpeg";
import saigon from "../../../assets/featured-img/saigon.jpg";
import hanoi from "../../../assets/featured-img/hanoi.jpg";
import coVN from "../../../assets/featured-img/coVN.png";

const Featured = () => {
  const navigate = useNavigate();
  const [options, setOptions] = useState({
    adult: 1,
    children: 0,
  });

  const [date, setDate] = useState([
    {
      startDate: new Date(),
      endDate: new Date(),
      key: "selection",
    },
  ]);

  const handleCityClick = (city) => {
    const encodedCity = encodeURIComponent(city);

    // Pass the date state in the same format as in the Header component
    navigate(`/hotels?destination=${encodedCity}`, {
      state: {
        date: [
          {
            startDate: date[0].startDate.toISOString(),
            endDate: date[0].endDate.toISOString(),
          },
        ],
        options,
      },
    });
  };

  return (
    <div className="featured pt-3">
      <div className="container">
        <div className="row">
          <div className="col-12 col-sm-12 col-md-6 col-lg-6">
            <div
              className="featuredItem"
              onClick={() => handleCityClick("Hồ Chí Minh")}
            >
              <img src={saigon} alt="" className="featuredImg" />
              <div className="featuredTitles">
                <h1>Hồ Chí Minh</h1>
                <img className="vnIcon" src={coVN} alt="Vietnam Icon" />
              </div>
            </div>
          </div>
          <div className="col-12 col-sm-12 col-md-6 col-lg-6">
            <div
              className="featuredItem"
              onClick={() => handleCityClick("Hà Nội")}
            >
              <img src={hanoi} alt="" className="featuredImg" />
              <div className="featuredTitles">
                <h1>Hà Nội</h1>
                <img className="vnIcon" src={coVN} alt="Vietnam Icon" />
              </div>
            </div>
          </div>
        </div>

        <div className="row mt-3">
          <div className="col-12 col-sm-12 col-md-4 col-lg-4">
            <div
              className="featuredItem"
              onClick={() => handleCityClick("Đà Lạt")}
            >
              <img src={dalat} alt="" className="featuredImg" />
              <div className="featuredTitles">
                <h1>Đà Lạt</h1>
                <img className="vnIcon" src={coVN} alt="Vietnam Icon" />
              </div>
            </div>
          </div>
          <div className="col-12 col-sm-12 col-md-4 col-lg-4">
            <div
              className="featuredItem"
              onClick={() => handleCityClick("Nha Trang")}
            >
              <img src={nhatrang} alt="" className="featuredImg" />
              <div className="featuredTitles">
                <h1>Nha Trang</h1>
                <img className="vnIcon" src={coVN} alt="Vietnam Icon" />
              </div>
            </div>
          </div>
          <div className="col-12 col-sm-12 col-md-4 col-lg-4">
            <div
              className="featuredItem"
              onClick={() => handleCityClick("Vũng Tàu")}
            >
              <img src={vungtau} alt="" className="featuredImg" />
              <div className="featuredTitles">
                <h1>Vũng Tàu</h1>
                <img className="vnIcon" src={coVN} alt="Vietnam Icon" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Featured;
