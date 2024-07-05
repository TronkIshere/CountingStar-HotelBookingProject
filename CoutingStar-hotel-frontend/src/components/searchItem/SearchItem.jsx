import React from "react";
import "./searchItem.css";
import { Link } from "react-router-dom";

const SearchItem = ({ hotel }) => {
  const truncate = (str, numWords) => {
    const words = str.split(" ");
    if (words.length <= numWords) return str;
    return words.slice(0, numWords).join(" ") + "...";
  };
  return (
    <div className="searchItem">
      <img src={hotel.photo} alt="" className="siImg" />
      <div className="siDesc">
        <h1 className="siHotelName">{hotel.hotelName}</h1>
        <span className="siCity">Tại thành phố {hotel.city}</span>
        <span className="siLocation">{hotel.hotelLocation}</span>
        <span className="sihotelDescription">{truncate(hotel.hotelDescription, 35)}</span>
      </div>
      <div className="siDetails">
        <div className="siRating">
          <span>{hotel.ratingText}</span>
          <button>{hotel.rating}</button>
        </div>
        <div className="siDetailTexts">
          <span className="siPrice">${hotel.price}</span>
          <span className="siTaxOp">Bao gồm tất cả khoảng phí</span>
          <button className="siCheckButton">
            <Link className="siCheckButtonText" to={`/hotels/hotel/${hotel.id}`}>Xem khách sạn</Link>
          </button>
        </div>
      </div>
    </div>
  );
};

export default SearchItem;
