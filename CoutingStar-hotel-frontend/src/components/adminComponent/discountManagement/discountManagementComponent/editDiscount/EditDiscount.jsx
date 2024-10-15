import React from 'react'
import { getDiscountById } from "../../../components/utils/ApiFunction";

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
        console.log(discountData);
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
    <div className="editDiscountModel">
      <form onSubmit={handleSubmit}>
        <h5>Edit Discount</h5>

        <div>
          <label>Discount Name:</label>
          <input
            type="text"
            value={discountName}
            onChange={(e) => setDiscountName(e.target.value)}
            required
          />
        </div>

        <div>
          <label>Percent Discount:</label>
          <input
            type="number"
            value={percentDiscount}
            onChange={(e) => setPercentDiscount(e.target.value)}
            required
          />
        </div>

        <div>
          <label>Discount Description:</label>
          <textarea
            value={discountDescription}
            onChange={(e) => setDiscountDescription(e.target.value)}
          />
        </div>

        <div>
          <label>Create Date:</label>
          <input
            type="date"
            value={createDate}
            onChange={(e) => setCreateDate(e.target.value)}
          />
        </div>

        <div>
          <label>Expiration Date:</label>
          <input
            type="date"
            value={expirationDate}
            onChange={(e) => setExpirationDate(e.target.value)}
          />
        </div>

        {error && <p className="text-danger">{error}</p>}
        {successMessage && <p className="text-success">{successMessage}</p>}

        <button type="submit">Save Changes</button>
      </form>
    </div>
  );
};

export default EditDiscount;
