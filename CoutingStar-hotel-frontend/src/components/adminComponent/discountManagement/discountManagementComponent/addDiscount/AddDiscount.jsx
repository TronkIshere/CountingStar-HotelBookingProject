import React, { useState } from "react";
import { addDiscount } from "../../../../utils/ApiFunction";
import "./addDiscount.css";

const AddDiscount = ({ onClose }) => {
  const [newDiscount, setNewDiscount] = useState({
    discountName: "",
    percentDiscount: "",
    discountDescription: "",
    expirationDate: "",
  });

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleInputChange = (e) => {
    const name = e.target.name;
    let value = e.target.value;

    // Convert percentDiscount to a number
    if (name === "percentDiscount") {
      value = !isNaN(value) ? parseInt(value) : "";
    }

    setNewDiscount({ ...newDiscount, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const success = await addDiscount(
      newDiscount.discountName,
      newDiscount.percentDiscount,
      newDiscount.discountDescription,
      newDiscount.expirationDate
    );
    if (success) {
      setSuccessMessage("Thêm mã giảm giá thành công!");
      setNewDiscount({
        discountName: "",
        percentDiscount: "",
        discountDescription: "",
        expirationDate: "",
      });
      setErrorMessage("");
    } else {
      setErrorMessage("Có lỗi khi thêm mã giảm giá.");
    }

    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  return (
    <div
      className="addDiscount modal fade show"
      style={{ display: "block" }}
      tabIndex="-1"
      role="dialog"
    >
      <div className="modal-dialog" role="document">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Thêm mã giảm giá</h5>
            <button
              type="button"
              className="btn-close"
              onClick={onClose}
            ></button>
          </div>
          <div className="modal-body">
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label">Tên mã giảm giá</label>
                <input
                  type="text"
                  className="form-control"
                  name="discountName"
                  placeholder="Tên mã giảm giá"
                  value={newDiscount.discountName}
                  onChange={handleInputChange}
                />
              </div>
              <div className="mb-3">
                <label className="form-label">Phần trăm giảm giá</label>
                <input
                  type="text"
                  className="form-control"
                  name="percentDiscount"
                  placeholder="Phần trăm giảm giá"
                  value={newDiscount.percentDiscount}
                  onChange={handleInputChange}
                />
              </div>
              <div className="mb-3">
                <label className="form-label">Miêu tả</label>
                <input
                  type="text"
                  className="form-control"
                  name="discountDescription"
                  placeholder="Miêu tả"
                  value={newDiscount.discountDescription}
                  onChange={handleInputChange}
                />
              </div>
              <div className="mb-3">
                <label className="form-label">Ngày hết hạn</label>
                <input
                  type="date"
                  className="form-control"
                  name="expirationDate"
                  value={newDiscount.expirationDate}
                  onChange={handleInputChange}
                />
              </div>
              {successMessage && (
                <div className="alert alert-success">{successMessage}</div>
              )}
              {errorMessage && (
                <div className="alert alert-danger">{errorMessage}</div>
              )}
              <div className="modal-footer">
                <button type="button" className="white-btn" onClick={onClose}>
                  Đóng
                </button>
                <button type="submit" className="main-btn">
                  Thêm mã giảm giá
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddDiscount;
