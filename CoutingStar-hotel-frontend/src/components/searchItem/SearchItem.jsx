import React from "react";
import "./searchItem.css";

const SearchItem = ({ hotel }) => {
  return (
    <div className="searchItem">
      <img src={hotel.photo} alt="" className="siImg" />
      <div className="siDesc">
        <h1 className="siHotelName">{hotel.hotelName}</h1>
        <span className="siCity">Tại thành phố {hotel.city}</span>
        <span className="siLocation">{hotel.hotelLocation}</span>
        <span className="sihotelDescription">{hotel.hotelDescription}</span>
      </div>
      <div className="siDetails">
        <div className="siRating">
          <span>{hotel.ratingText}</span>
          <button>{hotel.rating}</button>
        </div>
        <div className="siDetailTexts">
          <span className="siPrice">${hotel.price}</span>
          <span className="siTaxOp">Bao gồm tất cả khoảng phí</span>
          <button className="siCheckButton">Xem khách sạn</button>
        </div>
      </div>
    </div>
  );
};

export default SearchItem;
