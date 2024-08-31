import React, { useState, useEffect } from "react";
import "./bookingForm.css";
import { DateRange } from "react-date-range";
import { format } from "date-fns";
import { getRoomById, getUserByEmail, bookRoom, getAllRedeemedDiscountByUserId } from "../utils/ApiFunction";
import moment from "moment";

const BookingForm = ({ roomId, onClose }) => {
  const [openDate, setOpenDate] = useState(false);
  const [redeemedDiscounts, setRedeemedDiscounts] = useState([]);
  const [selectedDiscount, setSelectedDiscount] = useState(null);
  const [totalPayment, setTotalPayment] = useState(0);
  const [error, setError] = useState("");
  const userEmail = localStorage.getItem("userEmail");
  const userId = localStorage.getItem("userId");
  const [adults, setAdults] = useState(1);
  const [children, setChildren] = useState(0);
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
    if (userId) {
      const fetchUserRedeemedDiscount = async () => {
        try {
          const response = await getAllRedeemedDiscountByUserId(userId);
          console.log(response)
          setRedeemedDiscounts(response);
        } catch (error) {
          console.error("Error fetching user:", error.message);
          setError(error.message);
        }
      };
      fetchUserRedeemedDiscount();
    }
  }, [userId]);

  useEffect(() => {
    const fetchRoom = async () => {
      try {
        const response = await getRoomById(roomId);
        setRoomInfo(response);
      } catch (error) {
        console.error("Error fetching room:", error.message);
        setError(error.message);
      }
    };

    fetchRoom();
  }, [roomId]);


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
    updateTotalPayment(item.selection.startDate, item.selection.endDate, selectedDiscount);
  };

  const handleDiscountChange = (e) => {
    const selectedDiscountId = e.target.value;

    // Nếu chọn phần tử đầu tiên, đặt mã giảm giá là null
    if (selectedDiscountId === "") {
      setSelectedDiscount(null);
      updateTotalPayment(date[0].startDate, date[0].endDate, null);
    } else {
      const discount = redeemedDiscounts.find(d => d.id === parseInt(selectedDiscountId, 10));
      setSelectedDiscount(discount);
      updateTotalPayment(date[0].startDate, date[0].endDate, discount);
    }
    console.log(selectedDiscountId)
  };

  const handleSubmit = async () => {
    // Tạo đối tượng booking với discountId là id của discount hoặc null nếu không chọn mã giảm giá
    const booking = {
      guestFullName: `${user.firstName} ${user.lastName}`,
      checkInDate: date[0].startDate.toISOString(),
      checkOutDate: date[0].endDate.toISOString(),
      guestPhoneNumber: user.phoneNumber,
      guestEmail: user.email,
      numOfAdults: adults,
      numOfChildren: children,
      totalPayment: calculatePayment(date[0].startDate, date[0].endDate, selectedDiscount),
      discountId: selectedDiscount ? selectedDiscount.id : null, // Truyền discountId hoặc null
    };

    try {
      const redeemedDiscountId = booking.discountId ? parseInt(booking.discountId, 10) : null;
      await bookRoom(roomId, booking, userId, redeemedDiscountId);
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

  const calculatePayment = (checkInDate, checkOutDate, discount) => {
    const checkIn = moment(checkInDate);
    const checkOut = moment(checkOutDate);
    const diffInDays = checkOut.diff(checkIn, "days");
    const price = room.roomPrice ? room.roomPrice : 0;
    const total = diffInDays * price;
    if (discount) {
      return total - (total * discount.percentDiscount / 100);
    }
    return total;
  };

  const updateTotalPayment = (checkInDate, checkOutDate, discount = selectedDiscount) => {
    const total = calculatePayment(checkInDate, checkOutDate, discount);
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
              />
            )}
          </div>
          <select onChange={handleDiscountChange}>
            <option value="">Chọn mã giảm giá (nếu có)</option>
            {redeemedDiscounts.map((discount) => (
              <option key={discount.id} value={discount.id}>
                {discount.discountName} ({discount.percentDiscount}%) {discount.used ? " - Đã sử dụng" : ""}
              </option>
            ))}
          </select>
          <p>
            <strong>Tổng thanh toán:</strong> {totalPayment} VND
          </p>
          <button onClick={handleSubmit} className="submitButton">
            Đặt phòng
          </button>
        </div>
        {error && <p className="error">{error}</p>}
      </div>
    </div>
  );
};

export default BookingForm;
