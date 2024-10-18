import React, { useEffect, useState } from "react";
import "./hotelPage.css";
import Header from "../../components/header/Header";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCircleArrowLeft,
  faCircleArrowRight,
  faCircleXmark,
  faLocationDot,
  faPhone,
  faStar,
} from "@fortawesome/free-solid-svg-icons";
import { Link as ScrollLink } from "react-scroll";
import Register from "../../components/register/Register";
import Rating from "../../components/rating/Rating";
import { getHotelById } from "../../components/utils/ApiFunction";
import { useParams } from "react-router-dom";
import RoomList from "../../components/roomList/RoomList";

const Hotel = () => {
  const userId = localStorage.getItem("userId");
  const [slideNumber, setSlideNumber] = useState(0);
  const [open, setOpen] = useState(false);
  const [hotelInfo, setHotelInfo] = useState({
    id: "",
    hotelName: "",
    city: "",
    hotelLocation: "",
    hotelDescription: "",
    phoneNumber: "",
    photo: "",
    averageNumberOfHotelStars: "",
    lowestPrice: "",
    highestPrice: "",
  });

  const { hotelId } = useParams();

  useEffect(() => {
    getHotelById(hotelId)
      .then((response) => {
        setHotelInfo(response);
      })
      .catch((error) => {
        console.error("Error fetching hotel data:", error);
      });
  }, [hotelId]);

  const handleOpen = (i) => {
    setSlideNumber(i);
    setOpen(true);
  };

  const handleMove = (direction) => {
    let newSlideNumber;

    if (direction === "l") {
      newSlideNumber = slideNumber === 0 ? 5 : slideNumber - 1;
    } else {
      newSlideNumber = slideNumber === 5 ? 0 : slideNumber + 1;
    }

    setSlideNumber(newSlideNumber);
  };

  const [isRatingModalOpen, setRatingModalOpen] = useState(false);

  const handleOpenRatingClick = () => {
    setRatingModalOpen(true);
  };

  return (
    <div className="hotelPage">
      <div className="container my-4">
        {open && (
          <div className="slider">
            <FontAwesomeIcon
              icon={faCircleXmark}
              className="close"
              onClick={() => setOpen(false)}
            />
            <FontAwesomeIcon
              icon={faCircleArrowLeft}
              className="arrow"
              onClick={() => handleMove("l")}
            />
            <div className="sliderWrapper">
              <img
                src={`data:image/jpeg;base64,${hotelInfo.photo}`}
                alt="Hotel"
                className="sliderImg"
              />
            </div>
            <FontAwesomeIcon
              icon={faCircleArrowRight}
              className="arrow"
              onClick={() => handleMove("r")}
            />
          </div>
        )}

        {isRatingModalOpen && (
          <Rating hotelId={hotelId} onClose={() => setRatingModalOpen(false)} />
        )}

        <div className="hotelWrapper">
          <div className="row d-flex align-items-center">
            <div className="col-md-6">
              <h1 className="hotelTitle">{hotelInfo.hotelName}</h1>
            </div>
            <div className="col-md-6 d-flex justify-content-end align-items-end">
              <ScrollLink to="roomList" smooth={true} duration={500}>
                <button className="main-btn">Hãy đặt ngay!</button>
              </ScrollLink>
            </div>
          </div>
          <div className="hotelAddressAndContact">
            <FontAwesomeIcon icon={faLocationDot} />
            <span>{hotelInfo.hotelLocation}</span>
            <FontAwesomeIcon icon={faPhone} />
            <span>{hotelInfo.phoneNumber}</span>
          </div>
          <span className="hotelDistance">
            Tại thành phố - {hotelInfo.city}
          </span>
          <span className="hotelRating">
            <p>
              Đây là khách sạn với số sao là <FontAwesomeIcon icon={faStar} />{" "}
              {hotelInfo.averageNumberOfHotelStars}/5
            </p>
            <p
              className="viewHotelRating"
              data-bs-toggle="modal"
              data-bs-target="#ratingModal"
              onClick={handleOpenRatingClick}
            >
              Xem đánh giá
            </p>
          </span>
          <span className="hotelPriceHighlight">
            Hãy đặt ngay để tận hưởng khoảng khắc tuyệt vời tại khách sạn
          </span>
          <div className="hotelImages">
            <img
              className="hotelImg"
              src={`data:image/jpeg;base64,${hotelInfo.photo}`}
              alt="Hotel"
            />
          </div>

          <div className="hotelDetails">
            <div className="row">
              <div className="col-md-9">
                <div className="hotelDetailsTexts">
                  <h1 className="hotelTitle">Tổng quan khách sạn</h1>
                  <p className="hotelDesc">
                  <div dangerouslySetInnerHTML={{ __html: hotelInfo.hotelDescription }} />
                    </p>
                </div>
              </div>
              <div className="col-md-3">
                <div className="hotelDetailsPrice">
                  <h1>Nơi tuyệt vời để thuê!</h1>
                  <span>
                    Nằm tại thành phố {hotelInfo.city}, khách sạn này có số điểm
                    là 9.8!
                  </span>
                  <h2>
                    <b>
                      Từ ${hotelInfo.lowestPrice}-{hotelInfo.highestPrice}
                    </b>{" "}
                    (1 đêm)
                  </h2>
                  <button className="main-btn">Đặt phòng ngay!</button>
                </div>
              </div>
            </div>
          </div>

          <div id="roomList">
            <RoomList hotelId={hotelInfo.id} />
          </div>
        </div>

        {userId ? null : <Register />}
      </div>
    </div>
  );
};

export default Hotel;
