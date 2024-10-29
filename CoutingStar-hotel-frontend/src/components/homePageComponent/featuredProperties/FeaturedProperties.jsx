import React, { useEffect, useRef, useState } from "react";
import "./featuredProperties.css";
import { getFiveHotelForHomePage } from "../../utils/ApiFunction";
import { Link } from "react-router-dom";

const FeaturedProperties = () => {
  const [hotels, setHotels] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const hotelsCarouselRef = useRef(null);

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

  useEffect(() => {
    if (hotels.length > 0 && hotelsCarouselRef.current) {
      const $hotelsCarouselRef = $(hotelsCarouselRef.current);
      if ($hotelsCarouselRef.hasClass("owl-loaded")) {
        $hotelsCarouselRef.trigger("destroy.owl.carousel");
      }
      setTimeout(() => {
        $hotelsCarouselRef.owlCarousel({
          loop: true,
          margin: 10,
          nav: true,
          responsive: {
            0: { items: 1 },
            600: { items: 2 },
            760: { items: 2 },
            1000: { items: 5 },
          },
        });
      }, 1000);
    }
  }, [hotels]);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message}</div>;
  }

  return (
    <div className="hotHotels mt-5">
      <div className="container">
        <h1 className="homeTitle mb-4">Những khách sạn đang được yêu thích</h1>
        <div
          className="owl-carousel owl-theme mt-4 pb-4"
          ref={hotelsCarouselRef}
        >
          {hotels.map((hotel, index) => (
            <div key={index} className="item hotHotelsItem">
              <Link className="linkForm" to={`/hotels/hotel/${hotel.id}`}>
                <img
                  src={`data:image/jpeg;base64, ${hotel.photo}`}
                  alt=""
                  className="hotHotelsImg"
                />
                <span className="hotHotelsName">{hotel.hotelName}</span>
                <span className="hotHotelsName">{hotel.city}</span>
                <span className="hotHotelsName hotHotelsPrice">
                  Giá từ{" "}
                  {new Intl.NumberFormat("vi-VN", {
                    style: "currency",
                    currency: "VND",
                  }).format(hotel.lowestPrice)}
                </span>

                <div className="hotHotelsRating">
                  <button>{hotel.averageNumberOfHotelStars}</button>
                  <span>Trên 5 sao</span>
                </div>
              </Link>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default FeaturedProperties;
