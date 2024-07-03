import React from "react";
import "./propertyList.css";
import phong1 from '../../assets/propertyList-img/phong1.jpg';
import phong2 from '../../assets/propertyList-img/phong2.jpg';
import phong3 from '../../assets/propertyList-img/phong3.jpg';
import phong4 from '../../assets/propertyList-img/phong4.jpg';
import phong5 from '../../assets/propertyList-img/phong5.jpeg';

const PropertyList = () => {
  return (
    <div className="pList">
      <div className="pListItem">
        <img src={phong1} alt="" className="pListImg" />
        <div className="pListTitles">
          <h1>Nhanh chóng</h1>
          <h2>Chỉ với vài click!</h2>
        </div>
      </div>
      <div className="pListItem">
        <img src={phong2} alt="" className="pListImg" />
        <div className="pListTitles">
          <h1>Tiện lợi</h1>
          <h2>Không thủ tục rườm rà</h2>
        </div>
      </div>
      <div className="pListItem">
        <img src={phong3} alt="" className="pListImg" />
        <div className="pListTitles">
          <h1>Giá hời</h1>
          <h2>Giảm giá ngập trời</h2>
        </div>
      </div>
      <div className="pListItem">
        <img src={phong4} alt="" className="pListImg" />
        <div className="pListTitles">
          <h1>Dễ dàng</h1>
          <h2>Giao diện thân thiện</h2>
        </div>
      </div>
      <div className="pListItem">
        <img src={phong5} alt="" className="pListImg" />
        <div className="pListTitles">
          <h1>tiện nghi</h1>
          <h2>Dễ tìm ra được phòng phù hợp</h2>
        </div>
      </div>
    </div>
  );
};

export default PropertyList;
