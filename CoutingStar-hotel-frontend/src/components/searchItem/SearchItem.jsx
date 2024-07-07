import React, { useEffect, useState } from "react";
import "./searchItem.css";
import { Link } from "react-router-dom";
import { getHighestPriceByHotelId, getLowestPriceByHotelId } from "../utils/ApiFunction";

const SearchItem = ({ hotel }) => {
  const [lowestPrice, setLowestPrice] = useState({
    price: ""
  });
  const [highestPrice, setHighestPrice] = useState({
    price: ""
  });

  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    const fetchLowestPrice = async () => {
      try {
        const success = await getLowestPriceByHotelId(hotel.id);
        if (success) {
          const lowestPrice = success;
          setLowestPrice({ price: lowestPrice });
        } else {
          setErrorMessage("Error fetching the lowest price.");
        }
      } catch (error) {
        setErrorMessage("Error fetching the lowest price.");
      }
      setTimeout(() => {
        setErrorMessage("");
      }, 4000);
    };

    fetchLowestPrice();
  }, [hotel.id]);

  useEffect(() => {
    const fetchHighestPrice = async () => {
      try {
        const success = await getHighestPriceByHotelId(hotel.id);
        if (success) {
          console.log(success)
          const highestPrice = success;
          setHighestPrice({ price: highestPrice });
        } else {
          setErrorMessage("Error fetching the highest price.");
        }
      } catch (error) {
        setErrorMessage("Error fetching the highest price.");
      }
      setTimeout(() => {
        setErrorMessage("");
      }, 4000);
    };

    fetchHighestPrice();
  }, [hotel.id]);

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
          <span className="siPrice">${lowestPrice.price}-{highestPrice.price}</span>
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
