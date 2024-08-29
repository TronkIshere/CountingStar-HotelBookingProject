import React, { useState } from "react";
import "./addDiscount.css";
import { addDiscount } from "../../utils/ApiFunction";

const AddDiscount = ({ onClose }) => {
  const [newDiscount, setNewDiscount] = useState({
    discountName: "",
    percentDiscount: "",
    discountDescription: "",
    expirationDate: ""
  });

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleDiscountInputChange = (e) => {
    const name = e.target.name;
    let value = e.target.value;

    if (name === "percentDiscount") {
      if (!isNaN(value) && value >= 0 && value <= 100) {
        value = parseInt(value);
      } else {
        value = "";
      }
    }

    setNewDiscount({ ...newDiscount, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const success = await addDiscount(
        newDiscount.discountName,
        newDiscount.percentDiscount,
        newDiscount.discountDescription,
        newDiscount.expirationDate
      );
      if (success !== undefined) {
        setSuccessMessage("Thêm mã giảm giá thành công!");
        setNewDiscount({
          discountName: "",
          percentDiscount: "",
          discountDescription: "",
          expirationDate: ""
        });
        setErrorMessage("");
      } else {
        setErrorMessage("Có lỗi khi thêm mã giảm giá");
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  return (
    <div className="modal">
      <form className="modalContent" onSubmit={handleSubmit}>
        <div className="modalHeader">
          <h2>Thêm mã giảm giá mới</h2>
          <span className="close" onClick={onClose}>
            &times;
          </span>
        </div>
        <div className="modalBody">
          <input
            type="text"
            name="discountName"
            placeholder="Tên mã giảm giá"
            value={newDiscount.discountName}
            onChange={handleDiscountInputChange}
          />
          <input
            type="text"
            name="percentDiscount"
            placeholder="Phần trăm giảm giá"
            value={newDiscount.percentDiscount}
            onChange={handleDiscountInputChange}
          />
          <input
            type="text"
            name="discountDescription"
            placeholder="Miêu tả"
            value={newDiscount.discountDescription}
            onChange={handleDiscountInputChange}
          />
          <input
            type="date"
            name="expirationDate"
            placeholder="Ngày hết hạn"
            value={newDiscount.expirationDate}
            onChange={handleDiscountInputChange}
          />
        </div>
        <div className="modalFooter">
          <button className="addButton" type="submit">
            Thêm mã giảm giá
          </button>
        </div>
        {successMessage && <p className="successMessage">{successMessage}</p>}
        {errorMessage && <p className="errorMessage">{errorMessage}</p>}
      </form>
    </div>
  );
};

export default AddDiscount;
