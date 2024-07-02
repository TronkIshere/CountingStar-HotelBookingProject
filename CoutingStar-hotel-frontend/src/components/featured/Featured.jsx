import React from "react";
import "./featured.css";
import dalat from "../../assets/featured-img/dalat.jpg";
import nhatrang from "../../assets/featured-img/nhatrang.jpg";
import vungtau from "../../assets/featured-img/vungtau.jpeg";
import saigon from "../../assets/featured-img/saigon.jpg";
import hanoi from "../../assets/featured-img/hanoi.jpg";
import coVN from "../../assets/featured-img/coVN.png";

const Featured = () => {
  return (
    <div className="featured">
      <div className="featuredItemList top">
        <div className="featuredItem">
          <img src={saigon} alt="" className="featuredImg" />
          <div className="featuredTitles">
            <h1>Hồ Chí Minh</h1>
            <img className="vnIcon" src={coVN} />
          </div>
        </div>
        <div className="featuredItem">
          <img src={hanoi} alt="" className="featuredImg" />
          <div className="featuredTitles">
            <h1>Hà nội</h1>
            <img className="vnIcon" src={coVN} />
          </div>
        </div>
      </div>
      <div className="featuredItemList">
        <div className="featuredItem">
          <img src={dalat} alt="" className="featuredImg" />
          <div className="featuredTitles">
            <h1>Đà lạt</h1>
            <img className="vnIcon" src={coVN} />
          </div>
        </div>
        <div className="featuredItem">
          <img src={nhatrang} alt="" className="featuredImg" />
          <div className="featuredTitles">
            <h1>Nha Trang</h1>
            <img className="vnIcon" src={coVN} />
          </div>
        </div>
        <div className="featuredItem">
          <img src={vungtau} alt="" className="featuredImg" />
          <div className="featuredTitles">
            <h1>Vũng Tàu</h1>
            <img className="vnIcon" src={coVN} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Featured;
