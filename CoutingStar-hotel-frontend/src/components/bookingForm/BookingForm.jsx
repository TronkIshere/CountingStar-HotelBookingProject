import React, { useState, useEffect } from "react";
import "./bookingForm.css";
import { DateRange } from "react-date-range";
import { format } from "date-fns";
import { getRoomById, getUserByEmail, bookRoom } from "../utils/ApiFunction";
import moment from "moment";

const BookingForm = ({ roomId, onClose }) => {
  const [openDate, setOpenDate] = useState(false);
  const [date, setDate] = useState([
    {
      startDate: new Date(),
      endDate: new Date(),
      key: "selection",
    },
  ]);
  const [room, setRoomInfo] = useState({
    id: "",
    photo: "",
    roomType: "",
    roomPrice: 0,
    roomDescription: "",
    rating: "",
  });
  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
  });
  const [totalPayment, setTotalPayment] = useState(0);
  const [error, setError] = useState("");

  const userEmail = localStorage.getItem("userEmail");
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    if (userEmail) {
      const fetchUser = async () => {
        try {
          const response = await getUserByEmail(userEmail);
          setUser(response);
        } catch (error) {
          console.error("Error fetching user:", error.message);
        }
      };
      fetchUser();
    }
  }, [userEmail]);

  useEffect(() => {
    const fetchRoom = async () => {
      try {
        const response = await getRoomById(roomId);
        setRoomInfo(response);
      } catch (error) {
        console.error("Error fetching room:", error.message);
      }
    };

    fetchRoom();
  }, [roomId]);

  const [adults, setAdults] = useState(1);
  const [children, setChildren] = useState(0);

  useEffect(() => {
    const storedCheckInDate = localStorage.getItem("checkInDate");
    const storedCheckOutDate = localStorage.getItem("checkOutDate");
    const storedAdults = localStorage.getItem("adults");
    const storedChildren = localStorage.getItem("children");

    if (storedCheckInDate && storedCheckOutDate) {
      setDate([
        {
          startDate: new Date(storedCheckInDate),
          endDate: new Date(storedCheckOutDate),
          key: "selection",
        },
      ]);
    }
    if (storedAdults) setAdults(parseInt(storedAdults, 10));
    if (storedChildren) setChildren(parseInt(storedChildren, 10));
  }, []);

  if (!room) return null;

  const handleDateChange = (item) => {
    setDate([item.selection]);
    updateTotalPayment(item.selection.startDate, item.selection.endDate);
  };

  const handleSubmit = async () => {
    const booking = {
      guestFullName: `${user.firstName} ${user.lastName}`,
      checkInDate: date[0].startDate.toISOString(),
      checkOutDate: date[0].endDate.toISOString(),
      guestPhoneNumber: user.phoneNumber,
      guestEmail: user.email,
      numOfAdults: adults,
      numOfChildren: children,
      totalPayment: calculatePayment(date[0].startDate, date[0].endDate),
    };

    try {
      await bookRoom(roomId, booking, userId);
      localStorage.setItem("checkInDate", booking.checkInDate);
      localStorage.setItem("checkOutDate", booking.checkOutDate);
      localStorage.setItem("adults", adults);
      localStorage.setItem("children", children);
      onClose();
    } catch (error) {
      console.error("Error booking room:", error.message);
      setError(error.message);
    }
  };

  const calculatePayment = (checkInDate, checkOutDate) => {
    const checkIn = moment(checkInDate);
    const checkOut = moment(checkOutDate);
    const diffInDays = checkOut.diff(checkIn, "days");
    const price = room.roomPrice ? room.roomPrice : 0;
    return diffInDays * price;
  };

  const updateTotalPayment = (checkInDate, checkOutDate) => {
    const total = calculatePayment(checkInDate, checkOutDate);
    setTotalPayment(total);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setUser((prevUser) => ({
      ...prevUser,
      [name]: value,
    }));
  };

  return (
    <div className="modal_overlay" onClick={onClose}>
      <div className="bookingForm" onClick={(e) => e.stopPropagation()}>
        <button className="closeButton" onClick={onClose}>
          ×
        </button>
        <h1>Đặt phòng</h1>
        <div className="roomContent">
          <div className="roomImg">
            <img
              src={`data:image/png;base64, ${room.photo}`}
              alt={room.roomType}
            />
          </div>
          <div className="roomDetail">
            <p>
              <strong>Loại phòng:</strong> {room.roomType}
            </p>
            <p>
              <strong>Miêu tả:</strong> {room.roomDescription}
            </p>
            <p>
              <strong>Giá tiền:</strong> {room.roomPrice}
            </p>
            <p>
              <strong>Đánh giá:</strong> {room.rating}
            </p>
          </div>
        </div>
        <div className="userInfoInput">
          <input
            type="text"
            name="email"
            placeholder="Nhập Email"
            value={user.email}
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="firstName"
            placeholder="Nhập tên"
            value={user.firstName}
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="lastName"
            placeholder="Nhập họ và tên đệm"
            value={user.lastName}
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="phoneNumber"
            placeholder="Nhập số điện thoại"
            value={user.phoneNumber}
            onChange={handleInputChange}
          />
          <input
            type="number"
            placeholder="Nhập số người lớn"
            value={adults}
            onChange={(e) => setAdults(parseInt(e.target.value, 10))}
          />
          <input
            type="number"
            placeholder="Nhập số trẻ em"
            value={children}
            onChange={(e) => setChildren(parseInt(e.target.value, 10))}
          />
          <div className="dateSelection">
            <span
              onClick={() => setOpenDate(!openDate)}
              className="headerSearchText"
            >
              {`${format(date[0].startDate, "MM/dd/yyyy")} đến ${format(
                date[0].endDate,
                "MM/dd/yyyy"
              )}`}
            </span>
            {openDate && (
              <DateRange
                editableDateInputs={true}
                onChange={handleDateChange}
                moveRangeOnFirstSelection={false}
                ranges={date}
                className="date"
                minDate={new Date()}
              />
            )}
          </div>
        </div>
        <div className="totalPayment">
          <p>
            <strong>Tổng số tiền:</strong> {totalPayment} $
          </p>
          {error && <p className="error">{error}</p>}
        </div>
        <button className="submitButton" onClick={handleSubmit}>
          Đặt phòng
        </button>
      </div>
    </div>
  );
};

export default BookingForm;
