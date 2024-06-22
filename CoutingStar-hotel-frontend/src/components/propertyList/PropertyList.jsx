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
          <h1>Hotels</h1>
          <h2>233 hotels</h2>
        </div>
      </div>
      <div className="pListItem">
        <img src={phong2} alt="" className="pListImg" />
        <div className="pListTitles">
          <h1>Hotels</h1>
          <h2>233 hotels</h2>
        </div>
      </div>
      <div className="pListItem">
        <img src={phong3} alt="" className="pListImg" />
        <div className="pListTitles">
          <h1>Hotels</h1>
          <h2>233 hotels</h2>
        </div>
      </div>
      <div className="pListItem">
        <img src={phong4} alt="" className="pListImg" />
        <div className="pListTitles">
          <h1>Hotels</h1>
          <h2>233 hotels</h2>
        </div>
      </div>
      <div className="pListItem">
        <img src={phong5} alt="" className="pListImg" />
        <div className="pListTitles">
          <h1>Hotels</h1>
          <h2>233 hotels</h2>
        </div>
      </div>
    </div>
  );
};

export default PropertyList;
