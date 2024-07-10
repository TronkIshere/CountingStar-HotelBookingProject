import React, { useState, useEffect } from "react";
import "./bookingForm.css";
import { DateRange } from "react-date-range";
import { format } from "date-fns";
import {
  getBookingByBookingId,
  getRoomById,
  getUserByEmail,
  getUserProfile,
} from "../utils/ApiFunction";

const BookingForm = ({ roomId, onClose }) => {
  const [openDate, setOpenDate] = useState(false);
  const [date, setDate] = useState([
    {
      startDate: new Date(),
      endDate: new Date(),
      key: "selection",
    },
  ])
  const [room, setRoomInfo] = useState({
    id: "",
    photo: "",
    roomType: "",
    roomPrice: "",
  })
  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
  })

  const userEmail = localStorage.getItem("userEmail");
  console.log("userEmail", userEmail);
  if (userEmail != null) {
    useEffect(() => {
      const fetchUser = async () => {
        try {
          const response = await getUserByEmail(userEmail)
          setUser(response)
        } catch (error) {
          console.error("Error fetching user:", error.message);
          setErrorMessage(error.message);
        }
      };

      fetchUser()
    }, [userEmail])
  }

  useEffect(() => {
    const fetchRoom = async () => {
      try {
        const response = await getRoomById(roomId);
        setRoomInfo(response);
      } catch (error) {
        console.error("Error fetching room:", error.message);
        setErrorMessage(error.message);
      }
    };

    fetchRoom();
  }, [roomId]);

  const [adults, setAdults] = useState(1);
  const [children, setChildren] = useState(0);

  useEffect(() => {
    const storedStartDate = localStorage.getItem("startDate");
    const storedEndDate = localStorage.getItem("endDate");
    const storedAdults = localStorage.getItem("adults");
    const storedChildren = localStorage.getItem("children");

    if (storedStartDate && storedEndDate) {
      setDate([
        {
          startDate: new Date(storedStartDate),
          endDate: new Date(storedEndDate),
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
  };

  const handleSubmit = () => {
    localStorage.setItem("startDate", date[0].startDate.toISOString());
    localStorage.setItem("endDate", date[0].endDate.toISOString());
    localStorage.setItem("adults", adults);
    localStorage.setItem("children", children);
    onClose();
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
        <input type="text" placeholder="Nhập Email" value={user.email} />
          <input type="text" placeholder="Nhập tên" value={user.firstName} />
          <input type="text" placeholder="Nhập họ và tên đệm" value={user.lastName} />
          <input type="text" placeholder="Nhập số điện thoại" value={user.phoneNumber} />
          <input
            type="number"
            placeholder="Nhập số người lớn"
            value={adults}
            onChange={(e) => setAdults(e.target.value)}
          />
          <input
            type="number"
            placeholder="Nhập số trẻ em"
            value={children}
            onChange={(e) => setChildren(e.target.value)}
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
        <button className="submitButton" onClick={handleSubmit}>
          Đặt phòng
        </button>
      </div>
    </div>
  );
};

export default BookingForm;
