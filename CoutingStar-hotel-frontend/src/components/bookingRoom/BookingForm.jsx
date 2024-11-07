import React, { useState, useEffect } from "react";
import "./bookingForm.css";
import { DateRange } from "react-date-range";
import { format } from "date-fns";
import {
  getRoomById,
  getUserByEmail,
  bookRoom,
  getAllRedeemedDiscountByUserId,
} from "../utils/ApiFunction";
import moment from "moment";

const BookingForm = ({ roomId }) => {
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
      const response = await getRoomById(roomId);
      setRoomInfo(response);
      if (!response) setError(error.message);
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
    updateTotalPayment(
      item.selection.startDate,
      item.selection.endDate,
      selectedDiscount
    );
  };

  const handleDiscountChange = (e) => {
    const selectedDiscountId = e.target.value;

    if (selectedDiscountId === "") {
      setSelectedDiscount(null);
      updateTotalPayment(date[0].startDate, date[0].endDate, null);
    } else {
      const discount = redeemedDiscounts.find(
        (d) => d.id === parseInt(selectedDiscountId, 10)
      );
      setSelectedDiscount(discount);
      updateTotalPayment(date[0].startDate, date[0].endDate, discount);
    }
  };

  const handleSubmit = async () => {
    const booking = {
      guestFullName: `${user.firstName} ${user.lastName}`,
      checkInDate: date[0].startDate.toISOString().split("T")[0],
      checkOutDate: date[0].endDate.toISOString().split("T")[0],
      guestPhoneNumber: user.phoneNumber,
      guestEmail: user.email,
      numOfAdults: adults,
      numOfChildren: children,
      totalNumOfGuest: adults + children,
      totalPayment: calculatePayment(
        date[0].startDate,
        date[0].endDate,
        selectedDiscount
      ),
      discountId: selectedDiscount ? selectedDiscount.id : null,
    };

    try {
      const redeemedDiscountId = booking.discountId
        ? parseInt(booking.discountId, 10)
        : null;
      const result = await bookRoom(
        roomId,
        booking,
        userId,
        redeemedDiscountId
      );
      console.log(result);
      localStorage.setItem("checkInDate", booking.checkInDate);
      localStorage.setItem("checkOutDate", booking.checkOutDate);
      localStorage.setItem("adults", adults);
      localStorage.setItem("children", children);
    } catch (error) {
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
      return total - (total * discount.percentDiscount) / 100;
    }
    return total;
  };

  const updateTotalPayment = (
    checkInDate,
    checkOutDate,
    discount = selectedDiscount
  ) => {
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
    <div
      className="modal fade"
      id="bookingModal"
      tabIndex="-1"
      aria-labelledby="bookingModalLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog modal-dialog-centered">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="bookingModalLabel">
              Đặt phòng
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div className="modal-body">
            <div className="roomContent mb-3">
              <div className="row">
                <div className="col-12 col-sm-12 col-md-4 col-lg-4">
                  <div className="roomImg">
                    <img
                      src={`data:image/png;base64, ${room.photo}`}
                      alt={room.roomType}
                      className="img-fluid"
                    />
                  </div>
                </div>
                <div className="col-12 col-sm-12 col-md-8 col-lg-8">
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
                      <strong>Đánh giá:</strong> {room.averageNumberOfRoomStars}{" "}
                      / 5
                    </p>
                  </div>
                </div>
              </div>
            </div>
            <div className="userInfoInput">
              <div className="row">
                <div className="col-md-6">
                  <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input
                      type="text"
                      id="email"
                      name="email"
                      placeholder="Nhập Email"
                      value={user.email}
                      onChange={handleInputChange}
                      className="form-control mb-2"
                    />
                  </div>
                </div>
                <div className="col-md-6">
                  <div className="form-group">
                    <label htmlFor="firstName">Tên</label>
                    <input
                      type="text"
                      id="firstName"
                      name="firstName"
                      placeholder="Nhập tên"
                      value={user.firstName}
                      onChange={handleInputChange}
                      className="form-control mb-2"
                    />
                  </div>
                </div>
              </div>

              <div className="row">
                <div className="col-md-6">
                  <div className="form-group">
                    <label htmlFor="lastName">Họ và tên đệm</label>
                    <input
                      type="text"
                      id="lastName"
                      name="lastName"
                      placeholder="Nhập họ và tên đệm"
                      value={user.lastName}
                      onChange={handleInputChange}
                      className="form-control mb-2"
                    />
                  </div>
                </div>
                <div className="col-md-6">
                  <div className="form-group">
                    <label htmlFor="phoneNumber">Số điện thoại</label>
                    <input
                      type="text"
                      id="phoneNumber"
                      name="phoneNumber"
                      placeholder="Nhập số điện thoại"
                      value={user.phoneNumber}
                      onChange={handleInputChange}
                      className="form-control mb-2"
                    />
                  </div>
                </div>
              </div>

              <div className="row">
                <div className="col-md-6">
                  <div className="form-group">
                    <label htmlFor="adults">Số người lớn</label>
                    <input
                      type="number"
                      id="adults"
                      placeholder="Nhập số người lớn"
                      value={adults}
                      onChange={(e) => setAdults(parseInt(e.target.value, 10))}
                      className="form-control mb-2"
                    />
                  </div>
                </div>
                <div className="col-md-6">
                  <div className="form-group">
                    <label htmlFor="children">Số trẻ em</label>
                    <input
                      type="number"
                      id="children"
                      placeholder="Nhập số trẻ em"
                      value={children}
                      onChange={(e) =>
                        setChildren(parseInt(e.target.value, 10))
                      }
                      className="form-control mb-2"
                    />
                  </div>
                </div>
              </div>

              <div className="form-group mb-2">
                <label htmlFor="dateRange">Ngày đặt</label>
                <span
                  onClick={() => setOpenDate(!openDate)}
                  className="form-control"
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

              <div className="form-group mb-2">
                <label htmlFor="discount">Mã giảm giá</label>
                <select
                  onChange={handleDiscountChange}
                  id="discount"
                  className="form-select mb-2"
                >
                  <option value="">Chọn mã giảm giá (nếu có)</option>
                  {redeemedDiscounts.map((discount) => (
                    <option key={discount.id} value={discount.id}>
                      {discount.discountName} ({discount.percentDiscount}%)
                      {discount.used ? " - Đã sử dụng" : ""}
                    </option>
                  ))}
                </select>
              </div>

              <p>
                <strong>Tổng thanh toán:</strong>{" "}
                {new Intl.NumberFormat("vi-VN", {
                  style: "currency",
                  currency: "VND",
                }).format(totalPayment)}
              </p>

              <div className="modal-footer">
                <button
                  type="button"
                  className="white-btn"
                  data-bs-dismiss="modal"
                >
                  Đóng
                </button>
                <button onClick={handleSubmit} className="main-btn">
                  Đặt phòng
                </button>
              </div>

              {error && <p className="text-danger">{error}</p>}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookingForm;
