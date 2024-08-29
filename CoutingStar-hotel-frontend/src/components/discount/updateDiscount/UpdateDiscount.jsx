import React, { useState, useEffect } from "react";
import "./updateDiscount.css";
import { getDiscountById, updateDiscount } from "../../utils/ApiFunction";

const UpdateDiscount = ({ discountId, onClose, handleUpdateDiscount }) => {
  const [discountName, setDiscountName] = useState("");
  const [percentDiscount, setPercentDiscount] = useState(0);
  const [discountDescription, setDiscountDescription] = useState("");
  const [expirationDate, setExpirationDate] = useState("");

  useEffect(() => {
    const fetchDiscountDetails = async () => {
      try {
        const discountData = await getDiscountById(discountId);

        // Đảm bảo rằng expirationDate ở định dạng yyyy-MM-dd
        const formattedDate = new Date(discountData.expirationDate).toISOString().split('T')[0];

        setDiscountName(discountData.discountName);
        setPercentDiscount(discountData.percentDiscount);
        setDiscountDescription(discountData.discountDescription);
        setExpirationDate(formattedDate); // Định dạng date cho input type="date"
      } catch (error) {
        console.error("Error fetching discount details:", error.message);
      }
    };

    fetchDiscountDetails();
  }, [discountId]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const updatedDiscount = {
      id: discountId,
      discountName,
      percentDiscount,
      discountDescription,
      expirationDate, // Đã được định dạng đúng
    };

    try {
      await updateDiscount(discountId, updatedDiscount);
      handleUpdateDiscount(updatedDiscount);
      onClose();
    } catch (error) {
      console.error("Error updating discount:", error.message);
    }
  };

  return (
    <div className="modal">
      <div className="modalContent">
        <h2>Chỉnh sửa mã giảm giá</h2>
        <form onSubmit={handleSubmit}>
          <div className="formGroup">
            <label>Tên mã giảm giá:</label>
            <input
              type="text"
              value={discountName}
              onChange={(e) => setDiscountName(e.target.value)}
              required
            />
          </div>
          <div className="formGroup">
            <label>Phần trăm giảm:</label>
            <input
              type="number"
              value={percentDiscount}
              onChange={(e) => setPercentDiscount(e.target.value)}
              required
            />
          </div>
          <div className="formGroup">
            <label>Mô tả:</label>
            <textarea
              value={discountDescription}
              onChange={(e) => setDiscountDescription(e.target.value)}
              required
            />
          </div>
          <div className="formGroup">
            <label>Ngày hết hạn:</label>
            <input
              type="date"
              value={expirationDate}
              onChange={(e) => setExpirationDate(e.target.value)}
              required
            />
          </div>
          <div className="modalActions">
            <button type="submit" className="saveButton">Lưu</button>
            <button type="button" className="cancelButton" onClick={onClose}>
              Hủy bỏ
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default UpdateDiscount;