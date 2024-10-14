import React, { useContext, useEffect, useState } from "react";
import "./hotelPage.css";
import Header from "../../components/header/Header";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCircleArrowLeft,
  faCircleArrowRight,
  faCircleXmark,
  faLocationDot,
  faPencil,
  faPhone,
  faStar,
} from "@fortawesome/free-solid-svg-icons";
import { Link as ScrollLink } from "react-scroll";
import { Link as RouterLink } from "react-router-dom";
import RoomList from "../../components/room/roomList/RoomList";
import Register from "../../components/register/Register";
import Rating from "../../components/rating/Rating";
import { getHotelById } from "../../components/utils/ApiFunction";
import { useParams } from "react-router-dom";
import { AuthContext } from "../../components/utils/AuthProvider";

const Hotel = () => {
  const userId = localStorage.getItem("userId");
  const [slideNumber, setSlideNumber] = useState(0);
  const [open, setOpen] = useState(false);
  const [ratingOpen, setRatingOpen] = useState(false);
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
    setTimeout(() => {
      getHotelById(hotelId)
        .then((response) => {
          setHotelInfo(response);
          console.log(response);
          setIsLoading(false);
        })
        .catch((error) => {
          setError(error);
          setIsLoading(false);
        });
    }, 2000);
  }, [hotelId]);

  const photos = [
    {
      src: "https://cf.bstatic.com/xdata/images/hotel/max1280x900/261707778.jpg?k=56ba0babbcbbfeb3d3e911728831dcbc390ed2cb16c51d88159f82bf751d04c6&o=&hp=1",
    },
    {
      src: "https://cf.bstatic.com/xdata/images/hotel/max1280x900/261707367.jpg?k=cbacfdeb8404af56a1a94812575d96f6b80f6740fd491d02c6fc3912a16d8757&o=&hp=1",
    },
    {
      src: "https://cf.bstatic.com/xdata/images/hotel/max1280x900/261708745.jpg?k=1aae4678d645c63e0d90cdae8127b15f1e3232d4739bdf387a6578dc3b14bdfd&o=&hp=1",
    },
    {
      src: "https://cf.bstatic.com/xdata/images/hotel/max1280x900/261707776.jpg?k=054bb3e27c9e58d3bb1110349eb5e6e24dacd53fbb0316b9e2519b2bf3c520ae&o=&hp=1",
    },
    {
      src: "https://cf.bstatic.com/xdata/images/hotel/max1280x900/261708693.jpg?k=ea210b4fa329fe302eab55dd9818c0571afba2abd2225ca3a36457f9afa74e94&o=&hp=1",
    },
    {
      src: "https://cf.bstatic.com/xdata/images/hotel/max1280x900/261707389.jpg?k=52156673f9eb6d5d99d3eed9386491a0465ce6f3b995f005ac71abc192dd5827&o=&hp=1",
    },
  ];

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

  const userRole = localStorage.getItem("userRole");

  return (
    <div>
      <Header type="list" />
      <div className="hotelContainer">
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
                alt=""
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
        {ratingOpen && (
          <Rating hotelId={hotelInfo.id} onClose={() => setRatingOpen(false)} />
        )}
        <div className="hotelWrapper">
          <ScrollLink to="roomList" smooth={true} duration={500}>
            <button className="bookNow">Hãy đặt ngay!</button>
          </ScrollLink>

          {userRole === "ROLE_ADMIN" && (
            <div className="adminTbale">
              <h1>Bảng điều chỉnh khách sạn của Admin</h1>
              <div className="adminButtonPatten">
                <RouterLink to={`/hotel/${hotelInfo.id}/hotelRoomManagement`}>
                  <button className="adminButton">Quản lý phòng</button>
                </RouterLink>
                <RouterLink to={`/hotel/${hotelInfo.id}/hotelBookingManagement`}>
                  <button className="adminButton">Quản lý phòng được đặt</button>
                </RouterLink>
                <RouterLink to={`/hotel/${hotelInfo.id}/hotelInformationManagement`}>
                  <button className="adminButton">Quản lý thông tin khách sạn</button>
                </RouterLink>
              </div>
            </div>
          )}

          <h1 className="hotelTitle">{hotelInfo.hotelName}</h1>
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
              5/5
            </p>
            <p className="viewHotelRating" onClick={() => setRatingOpen(true)}>
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
            />
            {/* {photos.map((photo, i) => (
              <div className="hotelImgWrapper" key={i}>
                <img
                  onClick={() => handleOpen(i)}
                  src={photo.src}
                  alt=""
                  className="hotelImg"
                />
              </div>
            ))} */}
          </div>

          <div className="hotelDetails">
            <div className="hotelDetailsTexts">
              <h1 className="hotelTitle">Tổng quan khách sạn</h1>
              <p className="hotelDesc">{hotelInfo.hotelDescription}</p>
            </div>
            <div className="hotelDetailsPrice">
              <h1>Nơi tuyệt vời để thuê!</h1>
              <span>
                Nằm tại thành phố Hồ Chí Minh, khách sạn này có số điểm là 9.8!
              </span>
              <h2>
                <b>
                  Từ ${hotelInfo.lowestPrice}-{hotelInfo.highestPrice}
                </b>{" "}
                (1 đêm)
              </h2>
              <button>Đặt phòng ngay!</button>
            </div>
          </div>
          <div id="roomList">
            <RoomList hotelId={hotelInfo.id} />
          </div>
        </div>
        {userId ? <div></div> : <Register />}
      </div>
    </div>
  );
};

export default Hotel;
