import React, { useEffect, useState } from "react";
import "./featuredProperties.css";
import khachsan1 from "../../assets/featuredProperties-img/khachsan1.jpg";
import khachsan2 from "../../assets/featuredProperties-img/khachsan2.jpg";
import khachsan3 from "../../assets/featuredProperties-img/khachsan3.jpg";
import khachsan4 from "../../assets/featuredProperties-img/khachsan4.jpg";
import khachsan5 from "../../assets/featuredProperties-img/khachsan5.jpg";
import { getFiveHotelForHomePage } from "../utils/ApiFunction";
import { Link } from "react-router-dom";

const FeaturedProperties = () => {
  const [hotels, setHotels] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    getFiveHotelForHomePage()
      .then((response) => {
        setHotels(response);
        setIsLoading(false);
      })
      .catch((error) => {
        setError(error);
        setIsLoading(false);
      });
  }, []);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div className="fp">
      {hotels.map((hotel, index) => (
        <div key={index} className="fpItem">
          <Link className="linkForm" to={`/hotels/hotel/${hotel.id}`}>
            <img
              src={`data:image/jpeg;base64, ${hotel.photo}`}
              alt=""
              className="fpImg"
            />
            <span className="fpName">{hotel.hotelName}</span>
            <span className="fpName">{hotel.city}</span>
            <span className="fpName fpPrice">Giá từ ${hotel.lowestPrice}</span>
            <div className="fpRating">
              <button>{hotel.averageNumberOfHotelStars}</button>
              <span>Trên 5 sao</span>
            </div>
          </Link>
        </div>
      ))}
    </div>
  );
};

export default FeaturedProperties;
