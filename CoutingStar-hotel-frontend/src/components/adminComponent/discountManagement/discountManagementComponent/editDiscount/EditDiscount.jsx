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
        setCreateDate(discountData.createDate);
        setExpirationDate(discountData.expirationDate);
      } catch (error) {
        console.error("Error fetching discount:", error);
      }
    };

    if (discountId) {
      fetchDiscountData();
    }
  }, [discountId]);

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
      setSuccessMessage("Discount updated successfully!");
      setError("");
    } catch (error) {
      console.error("Error updating discount:", error);
      setError("An error occurred while updating the discount.");
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
              Edit Discount
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
                <label className="form-label">Discount Name:</label>
                <input
                  type="text"
                  className="form-control"
                  value={discountName}
                  onChange={(e) => setDiscountName(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Percent Discount:</label>
                <input
                  type="number"
                  className="form-control"
                  value={percentDiscount}
                  onChange={(e) => setPercentDiscount(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Discount Description:</label>
                <textarea
                  className="form-control"
                  value={discountDescription}
                  onChange={(e) => setDiscountDescription(e.target.value)}
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Create Date:</label>
                <input
                  type="date"
                  className="form-control"
                  value={createDate}
                  onChange={(e) => setCreateDate(e.target.value)}
                />
              </div>

              <div className="mb-3">
                <label className="form-label">Expiration Date:</label>
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
                  className="btn white-btn"
                  data-bs-dismiss="modal"
                >
                  Đóng
                </button>
                <button
                  type="button"
                  className="btn main-btn"
                >
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
