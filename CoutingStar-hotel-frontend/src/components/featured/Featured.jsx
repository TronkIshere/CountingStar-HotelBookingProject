import React from "react";
import { useNavigate } from "react-router-dom";
import "./featured.css";
import dalat from "../../assets/featured-img/dalat.jpg";
import nhatrang from "../../assets/featured-img/nhatrang.jpg";
import vungtau from "../../assets/featured-img/vungtau.jpeg";
import saigon from "../../assets/featured-img/saigon.jpg";
import hanoi from "../../assets/featured-img/hanoi.jpg";
import coVN from "../../assets/featured-img/coVN.png";

const Featured = () => {
  const navigate = useNavigate();

  const handleCityClick = (city) => {
    const encodedCity = encodeURIComponent(city);
    const currentDate = new Date();
    navigate(`/hotels/`, { state: { destination: encodedCity, date: [{ startDate: currentDate, endDate: currentDate }], options: {} } });
  };

  return (
    <div className="featured">
      <div className="featuredItemList top">
        <div className="featuredItem" onClick={() => handleCityClick("Hồ Chí Minh")}>
          <img src={saigon} alt="" className="featuredImg" />
          <div className="featuredTitles">
            <h1>Hồ Chí Minh</h1>
            <img className="vnIcon" src={coVN} alt="Vietnam Icon" />
          </div>
        </div>
        <div className="featuredItem" onClick={() => handleCityClick("Hà Nội")}>
          <img src={hanoi} alt="" className="featuredImg" />
          <div className="featuredTitles">
            <h1>Hà Nội</h1>
            <img className="vnIcon" src={coVN} alt="Vietnam Icon" />
          </div>
        </div>
      </div>
      <div className="featuredItemList">
        <div className="featuredItem" onClick={() => handleCityClick("Đà Lạt")}>
          <img src={dalat} alt="" className="featuredImg" />
          <div className="featuredTitles">
            <h1>Đà Lạt</h1>
            <img className="vnIcon" src={coVN} alt="Vietnam Icon" />
          </div>
        </div>
        <div className="featuredItem" onClick={() => handleCityClick("Nha Trang")}>
          <img src={nhatrang} alt="" className="featuredImg" />
          <div className="featuredTitles">
            <h1>Nha Trang</h1>
            <img className="vnIcon" src={coVN} alt="Vietnam Icon" />
          </div>
        </div>
        <div className="featuredItem" onClick={() => handleCityClick("Vũng Tàu")}>
          <img src={vungtau} alt="" className="featuredImg" />
          <div className="featuredTitles">
            <h1>Vũng Tàu</h1>
            <img className="vnIcon" src={coVN} alt="Vietnam Icon" />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Featured;
