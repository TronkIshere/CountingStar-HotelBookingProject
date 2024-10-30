import React, { useEffect, useState } from "react";
import { getDiscountById, updateDiscount } from "../../../../utils/ApiFunction";
import "./editDiscount.css";

const EditDiscount = ({ discountId }) => {
  const [discountName, setDiscountName] = useState("");
  const [percentDiscount, setPercentDiscount] = useState(0);
  const [discountDescription, setDiscountDescription] = useState("");
  const [createDate, setCreateDate] = useState("");
  const [expirationDate, setExpirationDate] = useState("");
  const [error, setError] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  useEffect(() => {
    const fetchDiscountData = async () => {
      try {
        const discountData = await getDiscountById(discountId);
        setDiscountName(discountData.discountName);
        setPercentDiscount(discountData.percentDiscount);
        setDiscountDescription(discountData.discountDescription);
        // Chuyển đổi ngày về định dạng YYYY-MM-DD
        setCreateDate(formatDate(discountData.createDate));
        setExpirationDate(formatDate(discountData.expirationDate));
      } catch (error) {
        console.error("Error fetching discount:", error);
      }
    };

    if (discountId) {
      fetchDiscountData();
    }
  }, [discountId]);

  // Hàm chuyển đổi định dạng ngày
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0"); // Thêm 0 cho tháng
    const day = String(date.getDate()).padStart(2, "0"); // Thêm 0 cho ngày
    return `${year}-${month}-${day}`;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await updateDiscount(discountId, {
        discountName,
        percentDiscount,
        discountDescription,
        createDate,
        expirationDate,
      });
      setSuccessMessage("Chỉnh sửa mã giảm giá thành công!");
      setError("");
    } catch (error) {
      setError("Đã xảy ra lỗi vui lòng thử lại!.");
    }

    setTimeout(() => {
      setError("");
      setSuccessMessage("");
    }, 5000);
  };

  return (
    <div
      className="editDiscount modal fade"
      id="editDiscountModal"
      tabIndex="-1"
      aria-labelledby="editDiscountModalLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="editDiscountModalLabel">
              Chỉnh sửa mã giảm giá
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div className="modal-body">
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label">Tên mã giảm giá:</label>
                <input
                  type="text"
                  className="form-control"
                  value={discountName}
                  onChange={(e) => setDiscountName(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Phần trăm giảm giá:</label>
                <input
                  type="number"
                  className="form-control"
                  value={percentDiscount}
                  onChange={(e) => setPercentDiscount(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Miêu tả:</label>
                <textarea
                  className="form-control"
                  value={discountDescription}
                  onChange={(e) => setDiscountDescription(e.target.value)}
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Ngày tạo:</label>
                <input
                  type="date"
                  className="form-control"
                  value={createDate}
                  onChange={(e) => setCreateDate(e.target.value)}
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Ngày hết hạn:</label>
                <input
                  type="date"
                  className="form-control"
                  value={expirationDate}
                  onChange={(e) => setExpirationDate(e.target.value)}
                />
              </div>

              {error && <p className="text-danger">{error}</p>}
              {successMessage && (
                <p className="text-success">{successMessage}</p>
              )}

              <div className="modal-footer">
                <button
                  type="button"
                  className="white-btn"
                  data-bs-dismiss="modal"
                >
                  Đóng
                </button>
                <button type="submit" className="main-btn">
                  Cập nhật
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EditDiscount;
