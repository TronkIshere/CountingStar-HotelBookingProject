import React, { useEffect, useState } from "react";
import "./hotelManagement.css";
import { getAllHotels, getHotelByKeyword } from "../../utils/ApiFunction";
import EditHotel from "./hotelManagementComponent/editHotel/EditHotel";
import DeleteHotel from "./hotelManagementComponent/deleteHotel/DeleteHotel";

const HotelManagement = () => {
  const [hotels, setHotels] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const fetchHotels = async (pageNo = 0, pageSize = 8, keyword = "") => {
    try {
      let hotelsData;
      if (keyword) {
        hotelsData = await getHotelByKeyword(pageNo, pageSize, keyword);
      } else {
        hotelsData = await getAllHotels(pageNo, pageSize);
      }

      if (hotelsData && hotelsData.content) {
        setHotels(hotelsData.content);
        setTotalPages(hotelsData.totalPages);
      } else {
        setHotels([]);
        setTotalPages(1);
      }
    } catch (error) {
      console.error("Error fetching hotels:", error);
      setHotels([]);
      setTotalPages(1);
    }
  };

  useEffect(() => {
    fetchHotels(page, 8, searchKeyword);
  }, [page, searchKeyword]);

  const handleSearch = () => {
    setPage(0);
    fetchHotels(0, 8, searchKeyword);
  };

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      setPage(newPage);
    }
  };

  const [currentHotelId, setCurrentHotelId] = useState(null);

  const handleEditClick = (id) => {
    setCurrentHotelId(id);
  };

  const handleDeleteClick = (id) => {
    setCurrentHotelId(id);
  };

  return (
    <div className="hotelManagement">
      <div className="container">
        <div className="searchHotel-bar">
          <div className="row">
            <div className="col-sm-12 col-md-5 col-lg-5 col-12">
              <div className="mb-3">
                <label htmlFor="hotel-keyword" className="form-label">
                  Nhập từ khóa khách sạn:
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="hotel-keyword"
                  placeholder="Nhập từ khóa"
                  value={searchKeyword}
                  onChange={(e) => setSearchKeyword(e.target.value)}
                />
              </div>
            </div>

            <div className="col-sm-2 col-md-2 col-lg-2 col-12 d-flex align-items-center">
              <div className="button-container">
                <button className="main-btn" onClick={handleSearch}>
                  Tìm <i className="bi bi-search"></i>
                </button>
              </div>
            </div>
          </div>
        </div>

        <div className="searchHotelList">
          <table className="table table-striped">
            <thead>
              <tr>
                <th scope="col">ID</th>
                <th scope="col">Tên khách sạn</th>
                <th scope="col">Thành phố</th>
                <th scope="col">Địa điểm</th>
                <th scope="col">Số điện thoại</th>
                <th scope="col">Thao tác</th>
              </tr>
            </thead>
            <tbody>
              {hotels.map((hotel) => (
                <tr key={hotel.id}>
                  <td>{hotel.id}</td>
                  <td>{hotel.hotelName}</td>
                  <td>{hotel.city}</td>
                  <td>{hotel.hotelLocation}</td>
                  <td>{hotel.phoneNumber}</td>
                  <td>
                    <button
                      className="btn btn-primary btn-sm"
                      data-bs-toggle="modal"
                      data-bs-target="#editHotelModel"
                      onClick={() => handleEditClick(hotel.id)}
                    >
                      Chỉnh sửa
                    </button>
                    <EditHotel hotelId={currentHotelId} />
                    <button
                      className="btn btn-danger btn-sm"
                      data-bs-toggle="modal"
                      data-bs-target="#deleteHotelModal"
                      onClick={() => handleDeleteClick(hotel.id)}
                    >
                      Xóa
                    </button>
                    <DeleteHotel hotelId={currentHotelId} />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <div className="pagination d-flex justify-content-center mt-4">
            <nav aria-label="Page navigation example">
              <ul className="pagination">
                <li className={`page-item ${page === 0 ? "disabled" : ""}`}>
                  <button
                    className="page-link"
                    onClick={() => handlePageChange(page - 1)}
                  >
                    &laquo;
                  </button>
                </li>
                {Array.from({ length: totalPages }, (_, i) => (
                  <li
                    key={i}
                    className={`page-item ${page === i ? "active" : ""}`}
                  >
                    <button
                      className="page-link"
                      onClick={() => handlePageChange(i)}
                    >
                      {i + 1}
                    </button>
                  </li>
                ))}
                <li
                  className={`page-item ${
                    page === totalPages - 1 ? "disabled" : ""
                  }`}
                >
                  <button
                    className="page-link"
                    onClick={() => handlePageChange(page + 1)}
                  >
                    &raquo;
                  </button>
                </li>
              </ul>
            </nav>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HotelManagement;
