import React, { useState, useEffect } from "react";
import "./searchHotelsPage.css";
import { useLocation } from "react-router-dom";
import { format } from "date-fns";
import { DateRange } from "react-date-range";
import SearchItem from "../../components/searchItem/SearchItem";
import { getHotelByKeyword } from "../../components/utils/ApiFunction";
import Register from "../../components/register/Register";

const SearchHotelsPage = () => {
  const userId = localStorage.getItem("userId");
  const location = useLocation();
  const { date: initialDate = [], options: initialOptions = {} } =
    location.state || {};
  const queryParams = new URLSearchParams(location.search);
  const initialDestination = queryParams.get("destination") || "";
  const [hotels, setHotels] = useState([]);
  const [openDate, setOpenDate] = useState(false);
  const [destination, setDestination] = useState(initialDestination);
  const [date, setDate] = useState(initialDate);
  const [options, setOptions] = useState(initialOptions);
  const [pageNo, setPageNo] = useState(0);
  const [pageSize, setPageSize] = useState(8);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    fetchHotels();
  }, [destination, pageNo, pageSize]);

  const fetchHotels = async () => {
    try {
      const hotelsData = await getHotelByKeyword(
        pageNo,
        pageSize,
        decodeURIComponent(destination)
      );
      setHotels(hotelsData.content);
      setTotalPages(hotelsData.totalPages);
    } catch (error) {
      console.error("Error fetching hotels:", error);
    }
  };

  const handleDestinationChange = (e) => {
    setDestination(e.target.value);
    setPageNo(0);
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
    <div className="searchHotelsPage mt-4">
      <div className="container">
        <div className="row">
          <div className="col-12 col-sm-12 col-md-3 col-lg-3">
            <div className="listSearch">
              <h1 className="lsTitle">Tìm kiếm</h1>
              <div className="lsItem">
                <label>Điểm đến</label>
                <input
                  type="text"
                  placeholder="Bạn muốn đi đâu?"
                  value={destination}
                  onChange={handleDestinationChange}
                />
              </div>
              <div className="lsItem calendar-container">
                <label>Ngày thuê</label>
                <span onClick={() => setOpenDate(!openDate)}>{`${format(
                  date[0]?.startDate,
                  "MM/dd/yyyy"
                )} đến ${format(date[0]?.endDate, "MM/dd/yyyy")}`}</span>
                {openDate && (
                  <DateRange
                    className="calendar"
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
          </div>
          <div className="col-12 col-sm-12 col-md-9 col-lg-9">
            <div className="listResultContainer">
              <div className="listResult">
                {hotels.length > 0 ? (
                  hotels.map((hotel) => (
                    <SearchItem key={hotel.id} hotel={hotel} />
                  ))
                ) : (
                  <p>
                    Không tìm thấy khách sạn nào cho thành phố "{destination}"
                  </p>
                )}
              </div>

              <nav aria-label="Page navigation example">
                <ul className="pagination">
                  <li className={`page-item ${pageNo === 0 ? "disabled" : ""}`}>
                    <button
                      className="page-link"
                      onClick={() => handlePageChange(pageNo - 1)}
                      disabled={pageNo === 0}
                    >
                      Trang trước
                    </button>
                  </li>
                  {Array.from({ length: totalPages }, (_, index) => (
                    <li
                      key={index}
                      className={`page-item ${
                        index === pageNo ? "active" : ""
                      }`}
                    >
                      <button
                        className="page-link"
                        onClick={() => handlePageChange(index)}
                      >
                        {index + 1}
                      </button>
                    </li>
                  ))}
                  <li
                    className={`page-item ${
                      pageNo === totalPages - 1 ? "disabled" : ""
                    }`}
                  >
                    <button
                      className="page-link"
                      onClick={() => handlePageChange(pageNo + 1)}
                      disabled={pageNo === totalPages - 1}
                    >
                      Trang sau
                    </button>
                  </li>
                </ul>
              </nav>
            </div>
          </div>
        </div>
      </div>
      {userId ? <div></div> : <Register />}
    </div>
  );
};

export default SearchHotelsPage;
