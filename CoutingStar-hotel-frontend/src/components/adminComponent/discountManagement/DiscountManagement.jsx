import React, { useEffect, useState } from "react";
import "./discountManagement.css";
import { getAllDiscount, getDiscountByKeyword } from "../../utils/ApiFunction";

const DiscountManagement = () => {
  const [discounts, setDiscounts] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const fetchDiscounts = async (pageNo = 0, pageSize = 8, keyword = "") => {
    try {
      let discountsData;
      if (keyword) {
        discountsData = await getDiscountByKeyword(pageNo, pageSize, keyword);
      } else {
        discountsData = await getAllDiscount(pageNo, pageSize);
      }

      if (discountsData && discountsData.content) {
        setDiscounts(discountsData.content);
        setTotalPages(discountsData.totalPages);
      } else {
        setDiscounts([]);
        setTotalPages(1);
      }
    } catch (error) {
      console.error("Error fetching discounts:", error);
      setDiscounts([]);
      setTotalPages(1);
    }
  };

  useEffect(() => {
    fetchDiscounts(page, 8, searchKeyword);
  }, [page, searchKeyword]);

  const handleSearch = () => {
    setPage(0);
    fetchDiscounts(0, 8, searchKeyword);
  };

  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage < totalPages) {
      setPage(newPage);
    }
  };

  return (
    <div className="discountManagement">
      <div className="container">
        <div className="searchDiscount-bar">
          <div className="row">
            <div className="col-sm-12 col-md-5 col-lg-5 col-12">
              <div className="mb-3">
                <label htmlFor="discount-keyword" className="form-label">
                  Nhập từ khóa mã giảm giá:
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="discount-keyword"
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
            <button className="main-btn">
              Thêm mã giảm giá 
            </button>
        </div>

        <div className="searchDiscountList">
          <table className="table table-striped">
            <thead>
              <tr>
                <th scope="col">ID</th>
                <th scope="col">Tên mã giảm giá</th>
                <th scope="col">Giảm giá (%)</th>
                <th scope="col">Mô tả</th>
                <th scope="col">Ngày tạo</th>
                <th scope="col">Ngày hết hạn</th>
                <th scope="col">Thao tác</th>
              </tr>
            </thead>
            <tbody>
              {discounts.map((discount) => (
                <tr key={discount.id}>
                  <td>{discount.id}</td>
                  <td>{discount.discountName}</td>
                  <td>{discount.percentDiscount}</td>
                  <td>{discount.discountDescription}</td>
                  <td>{new Date(discount.createDate).toLocaleDateString()}</td>
                  <td>
                    {new Date(discount.expirationDate).toLocaleDateString()}
                  </td>
                  <td>
                    <button className="btn btn-primary btn-sm">
                      Chỉnh sửa
                    </button>
                    <button className="btn btn-danger btn-sm">Xóa</button>
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

export default DiscountManagement;
