import React, { useEffect, useState } from "react";
import "./discount.css";
import {
  getDiscountNotExpired,
  addRedeemedDiscount,
} from "../../components/utils/ApiFunction";
import AddDiscount from "../../components/discount/addDiscount/AddDiscount";
import UpdateDiscount from "../../components/discount/updateDiscount/UpdateDiscount";
import DeleteDiscount from "../../components/discount/deleteDiscount/DeleteDiscount";

const Discount = () => {
  const [discounts, setDiscounts] = useState([]);
  const [errorMessage, setErrorMessage] = useState(null);
  const [isAddModalOpen, setIsAddModalOpen] = useState(false);
  const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [selectedDiscountId, setSelectedDiscountId] = useState(null);
  const userRole = localStorage.getItem("userRole");
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    const fetchDiscounts = async () => {
      try {
        const response = await getDiscountNotExpired();
        setDiscounts(response);
      } catch (error) {
        console.error("Error fetching discounts:", error.message);
        setErrorMessage(error.message);
      }
    };

    fetchDiscounts();
  }, []);

  const handleAddDiscount = (newDiscount) => {
    setDiscounts([...discounts, newDiscount]);
    setIsAddModalOpen(false);
  };

  const handleOpenAddModal = () => {
    setIsAddModalOpen(true);
  };

  const handleCloseAddModal = () => {
    setIsAddModalOpen(false);
  };

  const handleOpenUpdateModal = (discountId) => {
    setSelectedDiscountId(discountId);
    setIsUpdateModalOpen(true);
  };

  const handleCloseUpdateModal = () => {
    setIsUpdateModalOpen(false);
  };

  const handleUpdateDiscount = (updatedDiscount) => {
    const updatedDiscounts = discounts.map((discount) =>
      discount.id === updatedDiscount.id ? updatedDiscount : discount
    );
    setDiscounts(updatedDiscounts);
    setIsUpdateModalOpen(false);
  };

  const handleOpenDeleteModal = (discountId) => {
    setSelectedDiscountId(discountId);
    setIsDeleteModalOpen(true);
  };

  const handleCloseDeleteModal = () => {
    setIsDeleteModalOpen(false);
  };

  const handleDeleteDiscount = (discountId) => {
    setDiscounts(discounts.filter((discount) => discount.id !== discountId));
    setIsDeleteModalOpen(false);
  };

  const handleRedeemDiscount = async (discountId) => {
    try {
      const response = await addRedeemedDiscount(discountId, userId);
      if (response.status === 200) {
        alert("Mã giảm giá đã được nhận thành công!");
      }
    } catch (error) {
      // Hiển thị thông báo lỗi cụ thể
      alert("Có lỗi xảy ra khi nhận mã giảm giá: " + error.message);
    }
  };

  return (
    <div className="container">
      {userRole === "ROLE_ADMIN" && (
        <div className="adminTable">
          <button className="adminButton" onClick={handleOpenAddModal}>
            Thêm mã giảm giá
          </button>
        </div>
      )}
      <br />
      {discounts.length > 0 ? (
        discounts.map((discount) => (
          <div key={discount.id} className="discountItem">
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
              {userRole === "ROLE_ADMIN" && (
                <div className="discountAdminButton">
                  <button
                    className="adminButton"
                    onClick={() => handleOpenUpdateModal(discount.id)}
                  >
                    Chỉnh sửa
                  </button>
                  <button
                    className="adminButton"
                    onClick={() => handleOpenDeleteModal(discount.id)}
                  >
                    Xóa
                  </button>
                </div>
              )}
            </div>
          </div>
        ))
      ) : (
        <div>Không có mã giảm giá nào khả dụng</div>
      )}
      {errorMessage && <div className="error">{errorMessage}</div>}
      {isAddModalOpen && (
        <AddDiscount
          onClose={handleCloseAddModal}
          handleAddDiscount={handleAddDiscount}
        />
      )}
      {isUpdateModalOpen && selectedDiscountId && (
        <UpdateDiscount
          discountId={selectedDiscountId}
          onClose={handleCloseUpdateModal}
          handleUpdateDiscount={handleUpdateDiscount}
        />
      )}
      {isDeleteModalOpen && selectedDiscountId && (
        <DeleteDiscount
          discountId={selectedDiscountId}
          onClose={handleCloseDeleteModal}
          handleDeleteDiscount={handleDeleteDiscount}
        />
      )}
    </div>
  );
};

export default Discount;
