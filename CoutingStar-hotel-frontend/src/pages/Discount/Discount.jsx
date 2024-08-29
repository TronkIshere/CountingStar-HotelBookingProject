import React, { useEffect } from "react";
import "./discount.css";
import { getDiscountNotExpired } from "../../components/utils/ApiFunction";

const Discount = () => {
  const userRole = localStorage.getItem("userRole");

  useEffect(() => {
    const fetchDiscounts = async () => {
      try {
        const response = await getDiscountNotExpired();
        console.log(response)
      } catch (error) {
        console.error("Error fetching discounts:", error.message);
        setErrorMessage(error.message);
      }
    };

    fetchDiscounts();
  }, []);

  return (
    <div className="container">
      {userRole === "ROLE_ADMIN" && (
        <div className="adminTbale">
          <button className="adminButton">Thêm mã giảm giá</button>
        </div>
      )}
      <br />
      <div className="discountItem">
        <div className="discountContent">
          <div className="discountLeftContent">
            <div className="discountName">Giảm giá mùa hè</div>
            <div className="percentDiscount">50%</div>
            <div className="discountDescription">
              Giảm giá đến 50% cho mọi phòng bạn đặt
            </div>
          </div>
          <div className="discountRightContent">
            <button>Nhận ngay!</button>
            <div className="expirationDate">Ngày hết hạn: 25-10-2024</div>
          </div>
          {userRole === "ROLE_ADMIN" && (
            <div className="discountAdminButton">
              <button className="adminButton">Chỉnh sửa</button>
              <button className="adminButton">Xóa</button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default Discount;
