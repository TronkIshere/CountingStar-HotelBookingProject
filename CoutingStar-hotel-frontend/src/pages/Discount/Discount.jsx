import React, { useEffect, useState } from "react";
import { Pagination } from "react-bootstrap";
import "./discount.css";
import {
  getDiscountNotExpired,
  addRedeemedDiscount,
} from "../../components/utils/ApiFunction";

const Discount = () => {
  const [discounts, setDiscounts] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [errorMessage, setErrorMessage] = useState(null);
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    const fetchDiscounts = async () => {
      try {
        const response = await getDiscountNotExpired(currentPage, 10);
        setDiscounts(response.content);
        setTotalPages(response.totalPages);
      } catch (error) {
        setErrorMessage(error.message);
      }
    };

    fetchDiscounts();
  }, [currentPage]);

  const handlePageChange = (page) => {
    if (page >= 0 && page < totalPages) {
      setCurrentPage(page);
    }
  };

  const handleRedeemDiscount = async (discountId) => {
    if (!userId) alert("Hãy đăng nhập để nhận mã giảm giá");
    else {
      try {
        const response = await addRedeemedDiscount(discountId, userId);
        if (response.status === 200) {
          alert("Bạn đã nhận được mã giảm giá");
        }
      } catch (error) {
        alert("Có lỗi xảy ra khi nhận mã giảm giá: " + error.message);
      }
    }
  };

  return (
    <div className="container mt-5">
      <div className="row">
        {discounts.length > 0 ? (
          discounts.map((discount) => (
            <div className="col-12 col-sm-12 col-md-6">
              <div key={discount.id} className="discountItem w-100">
                <div className="discountContent">
                  <div className="discountLeftContent">
                    <div className="discountName">{discount.discountName}</div>
                    <div className="percentDiscount">
                      {discount.percentDiscount}%
                    </div>
                    <div className="discountDescription">
                      {discount.discountDescription}
                    </div>
                  </div>
                  <div className="discountRightContent">
                    <button onClick={() => handleRedeemDiscount(discount.id)}>
                      Nhận ngay!
                    </button>
                    <div className="expirationDate">
                      Ngày hết hạn:{" "}
                      {new Date(discount.expirationDate).toLocaleDateString(
                        "vi-VN"
                      )}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          ))
        ) : (
          <div>Không có mã giảm giá nào khả dụng</div>
        )}
        {errorMessage && <div className="error">{errorMessage}</div>}
      </div>
      <Pagination className="justify-content-center mt-4">
        <Pagination.First
          onClick={() => handlePageChange(0)}
          disabled={currentPage === 0}
        />
        <Pagination.Prev
          onClick={() => handlePageChange(currentPage - 1)}
          disabled={currentPage === 0}
        />

        {[...Array(totalPages).keys()].map((page) => (
          <Pagination.Item
            key={page}
            active={page === currentPage}
            onClick={() => handlePageChange(page)}
          >
            {page + 1}
          </Pagination.Item>
        ))}

        <Pagination.Next
          onClick={() => handlePageChange(currentPage + 1)}
          disabled={currentPage === totalPages - 1}
        />
        <Pagination.Last
          onClick={() => handlePageChange(totalPages - 1)}
          disabled={currentPage === totalPages - 1}
        />
      </Pagination>
    </div>
  );
};

export default Discount;
