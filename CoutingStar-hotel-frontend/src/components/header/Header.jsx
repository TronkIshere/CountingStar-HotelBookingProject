import React, { useState } from "react";
import "./header.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBed, faCalendar, faCar, faLocation, faPerson, faPlane, faTaxi } from "@fortawesome/free-solid-svg-icons";
import "react-date-range/dist/styles.css";
import "react-date-range/dist/theme/default.css";
import { DateRange } from "react-date-range";
import { format } from "date-fns";
import { useNavigate } from "react-router-dom";
import { Link } from "react-scroll";

const Header = ({ type }) => {
  const [destination, setDestination] = useState("")
  const [openDate, setOpenDate] = useState(false)
  const [date, setDate] = useState([
    {
      startDate: new Date(),
      endDate: new Date(),
      key: "selection",
    }
  ])
  const [openOptions, setOpenOptions] = useState(false)
  const [options, setOptions] = useState({
    adult: 1,
    children: 0,
  })

  const navigate = useNavigate();

  const handleOption = (name, operation) => {
    setOptions((prev) => {
      return {
        ...prev,
        [name]: operation === "i" ? options[name] + 1 : options[name] - 1,
      }
    })
  }

  const handleSearch = () => {
    localStorage.setItem("startDate", date[0].startDate.toISOString())
    localStorage.setItem("endDate", date[0].endDate.toISOString())
    localStorage.setItem("adult", options.adult)
    localStorage.setItem("children", options.children)
  
    navigate('/hotels', { state: { destination, date, options } })
  }

  return (
    <div className="header">
      <div className={type === "list" ? "headerContainer listMode" : "headerContainer"}>
        <div className="headerList">
          <div className="headerListItem active">
            <FontAwesomeIcon icon={faBed} />
            <span>Stays</span>
          </div>
          <div className="headerListItem">
            <FontAwesomeIcon icon={faPlane} />
            <span>Flights</span>
          </div>
          <div className="headerListItem">
            <FontAwesomeIcon icon={faCar} />
            <span>Car rentals</span>
          </div>
          <div className="headerListItem">
            <FontAwesomeIcon icon={faBed} />
            <span>Attractions</span>
          </div>
          <div className="headerListItem">
            <FontAwesomeIcon icon={faTaxi} />
            <span>Airport taxis</span>
          </div>
        </div>
        {type !== "list" && (
          <>
            <h1 className="headerTitle">Nghỉ dưỡng đẳng cấp, trải nghiệm hoàn hảo.</h1>
            <p className="headerDesc">Muốn nhận ưu đãi từ chuyến đi của bạn – Hãy đăng ký tài khoản để nhận ưu đãi giảm giá 10% cho mọi chuyến đi</p>
            <Link to="register" smooth={true} duration={500} className="headerBtn">
              Đăng ký ngay hôm nay!
            </Link>

            <div className="headerSearch">
              <div className="headerSearchItem">
                <FontAwesomeIcon icon={faBed} className="headerIcon" />
                <select className="headerSearchSelect" value={destination} onChange={(e) => setDestination(e.target.value)}>
                  <option value="" disabled>Bạn muốn đi đâu?</option>
                  <option value="Ho Chi Minh">Hồ Chí Minh</option>
                  <option value="Ha Noi">Hà Nội</option>
                  <option value="Da Lat">Đà Lạt</option>
                  <option value="Nha Trang">Nha Trang</option>
                  <option value="Vung Tau">Vũng Tàu</option>
                </select>
              </div>
              <div className="headerSearchItem">
                <FontAwesomeIcon icon={faCalendar} className="headerIcon" />
                <span onClick={() => setOpenDate(!openDate)} className="headerSearchText">
                  {`${format(date[0].startDate, "MM/dd/yyyy")} đến ${format(date[0].endDate, "MM/dd/yyyy")}`}
                </span>
                {openDate && (
                  <DateRange editableDateInputs={true} onChange={(item) => setDate([item.selection])} moveRangeOnFirstSelection={false} ranges={date} className="date" minDate={new Date()} />
                )}
              </div>
              <div className="headerSearchItem">
                <FontAwesomeIcon icon={faPerson} className="headerIcon" />
                <span onClick={() => setOpenOptions(!openOptions)} className="headerSearchText">
                  {`${options.adult} người lớn | ${options.children} trẻ em`}
                </span>
                {openOptions && (
                  <div className="options">
                    <div className="optionItem">
                      <span className="optionText">Người lớn</span>
                      <div className="optionCounter">
                        <button className="optionCounterButton" disabled={options.adult <= 1} onClick={() => handleOption("adult", "d")}>
                          -
                        </button>
                        <span className="optionCounterNumber">{options.adult}</span>
                        <button className="optionCounterButton" onClick={() => handleOption("adult", "i")}>
                          +
                        </button>
                      </div>
                    </div>
                    <div className="optionItem">
                      <span className="optionText">Trẻ em</span>
                      <div className="optionCounter">
                        <button className="optionCounterButton" disabled={options.children <= 0} onClick={() => handleOption("children", "d")}>
                          -
                        </button>
                        <span className="optionCounterNumber">{options.children}</span>
                        <button className="optionCounterButton" onClick={() => handleOption("children", "i")}>
                          +
                        </button>
                      </div>
                    </div>
                  </div>
                )}
              </div>
              <div className="headerSearchItem">
                <button className="headerBtn" onClick={handleSearch}>
                  Tìm kiếm
                </button>
              </div>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default Header;
