import React from "react";
import "./discount.css";
import { useNavigate, Link } from "react-router-dom";

const Discount = () => {
  return (
    <div className="discount mt-4">
      <div className="container">
          <h1 className="homeTitle mb-4">Săn mã giảm giá mua du lịch</h1>
        <div className="discountBanner">
          <div className="discountBannerContent">
            <div className="discountBannerTitle">
              Đặt liền tay nhận ngay ưu đãi
            </div>
            <div className="discountBannerContext">
              Hãy nhận ngay ưu đãi ngay bên dưới!!!
            </div>
            <div className="discountBannerButton">
              <Link className="discountButton" to={`/discount`}>
                Tìm ngay ưu đãi mùa du lịch
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Discount;
