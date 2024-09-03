import React, { useState, useEffect } from "react";
import Navbar from "../../components/navbar/Navbar";
import Header from "../../components/header/Header";
import "./list.css";
import { useLocation } from "react-router-dom";
import { format } from "date-fns";
import { DateRange } from "react-date-range";
import SearchItem from "../../components/searchItem/SearchItem";
import { getHotelsByCity } from "../../components/utils/ApiFunction";
import Register from "../../components/register/Register";

const List = () => {
  const userId = localStorage.getItem("userId");
  const location = useLocation();
  const {
    destination: initialDestination = "",
    date: initialDate = [],
    options: initialOptions = {},
  } = location.state || {};

  const [hotels, setHotels] = useState([]);
  const [openDate, setOpenDate] = useState(false);
  const [destination, setDestination] = useState(initialDestination);
  const [date, setDate] = useState(initialDate);
  const [options, setOptions] = useState(initialOptions);
  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    fetchHotels();
  }, [destination, pageNo, pageSize]);

  const fetchHotels = async () => {
    try {
      const hotelsData = await getHotelsByCity(
        decodeURIComponent(destination),
        pageNo,
        pageSize
      );
      setHotels(hotelsData.content);
      setTotalPages(hotelsData.totalPages);
    } catch (error) {
      console.error("Error fetching hotels:", error);
    }
  };

  const handleDestinationChange = (e) => {
    setDestination(e.target.value);
    setPageNo(0); // Reset về trang đầu khi thay đổi điểm đến
  };

  const handleDateChange = (item) => {
    setDate([item.selection]);
  };

  const handleOptionChange = (name, value) => {
    setOptions((prevOptions) => ({
      ...prevOptions,
      [name]: value,
    }));
  };

  const handleSearch = () => {
    fetchHotels();
  };

  const handlePageChange = (newPage) => {
    setPageNo(newPage);
  };

  return (
    <div>
      <Header type="list" />
      <div className="listContainer">
        <div className="listWrapper">
          <div className="listSearch">
            <h1 className="lsTitle">Search</h1>
            <div className="lsItem">
              <label>Điểm đến</label>
              <select value={destination} onChange={handleDestinationChange}>
                <option value="" disabled>
                  Bạn muốn đi đâu?
                </option>
                <option value="Ho Chi Minh">Hồ Chí Minh</option>
                <option value="Ha Noi">Hà Nội</option>
                <option value="Da Lat">Đà Lạt</option>
                <option value="Nha Trang">Nha Trang</option>
                <option value="Vung Tau">Vũng Tàu</option>
              </select>
            </div>
            <div className="lsItem">
              <label>Ngày thuê</label>
              <span onClick={() => setOpenDate(!openDate)}>{`${format(
                date[0]?.startDate,
                "MM/dd/yyyy"
              )} đến ${format(date[0]?.endDate, "MM/dd/yyyy")}`}</span>
              {openDate && (
                <DateRange
                  onChange={handleDateChange}
                  minDate={new Date()}
                  ranges={date}
                />
              )}
            </div>
            <div className="lsItem">
              <label>Lựa chọn</label>
              <div className="lsOptions">
                <div className="lsOptionItem">
                  <span className="lsOptionText">
                    giá thấp nhất <small>theo ngày</small>
                  </span>
                  <input type="number" className="lsOptionInput" />
                </div>

                <div className="lsOptionItem">
                  <span className="lsOptionText">
                    giá thấp nhất <small>theo ngày</small>
                  </span>
                  <input type="number" className="lsOptionInput" />
                </div>

                <div className="lsOptionItem">
                  <span className="lsOptionText">
                    giá cao nhất <small>theo ngày</small>
                  </span>
                  <input type="number" className="lsOptionInput" />
                </div>

                <div className="lsOptionItem">
                  <span className="lsOptionText">Người lớn</span>
                  <input
                    type="number"
                    min={1}
                    className="lsOptionInput"
                    value={options.adult}
                    onChange={(e) =>
                      handleOptionChange("adult", e.target.value)
                    }
                  />
                </div>

                <div className="lsOptionItem">
                  <span className="lsOptionText">Trẻ con</span>
                  <input
                    type="number"
                    min={0}
                    className="lsOptionInput"
                    value={options.children}
                    onChange={(e) =>
                      handleOptionChange("children", e.target.value)
                    }
                  />
                </div>
              </div>
            </div>
            <button onClick={handleSearch}>Tìm kiếm</button>
          </div>
          <div className="listResultContainer">
            <div className="listResult">
              {hotels.length > 0 ? (
                hotels.map((hotel) => (
                  <SearchItem key={hotel.id} hotel={hotel} />
                ))
              ) : (
                <p>Không tìm thấy khách sạn nào cho thành phố "{destination}"</p>
              )}
            </div>
            <div className="pagination">
              <button
                onClick={() => handlePageChange(pageNo - 1)}
                disabled={pageNo === 0}
              >
                Trang trước
              </button>
              {Array.from({ length: totalPages }, (_, index) => (
                <button
                  key={index}
                  onClick={() => handlePageChange(index)}
                  className={index === pageNo ? "active" : ""}
                >
                  {index + 1}
                </button>
              ))}
              <button
                onClick={() => handlePageChange(pageNo + 1)}
                disabled={pageNo === totalPages - 1}
              >
                Trang sau
              </button>
            </div>
          </div>
        </div>
      </div>
      {userId ? <div></div> : <Register />}
    </div>
  );
};

export default List;
