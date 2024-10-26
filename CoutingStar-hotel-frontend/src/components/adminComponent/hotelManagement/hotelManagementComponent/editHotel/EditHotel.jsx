import React, { useEffect, useState } from "react";
import "./editHotel.css";
import { getHotelById, updateHotel } from "../../../../utils/ApiFunction";
import ReactQuill from "react-quill";

const EditHotel = ({ hotelId }) => {
  const [hotelName, setHotelName] = useState("");
  const [city, setCity] = useState("");
  const [hotelLocation, setHotelLocation] = useState("");
  const [hotelDescription, setHotelDescription] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [photo, setPhoto] = useState(null);
  const [base64Photo, setBase64Photo] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    if (hotelId) {
      const fetchHotel = async () => {
        try {
          const hotelData = await getHotelById(hotelId);
          setHotelName(hotelData.hotelName);
          setCity(hotelData.city);
          setHotelLocation(hotelData.hotelLocation);
          setHotelDescription(hotelData.hotelDescription);
          setPhoneNumber(hotelData.phoneNumber);
          if (hotelData.photo) {
            setBase64Photo(`data:image/jpeg;base64,${hotelData.photo}`);
          }
        } catch (error) {
          console.error("Error fetching hotel:", error);
          setErrorMessage("Lỗi khi tải thông tin khách sạn.");
        }
      };
      fetchHotel();
    }
  }, [hotelId]);

  const handlePhotoChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setPhoto(reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleUpdate = async () => {
    try {
      if (hotelId) {
        const hotelData = {
          hotelName,
          city,
          hotelLocation,
          hotelDescription,
          phoneNumber,
          photo: photo ? photo : base64Photo,
        };

        const response = await updateHotel(hotelId, hotelData);
        setSuccessMessage("Khách sạn đã được chỉnh sửa thành công!");
        setErrorMessage("");
        setTimeout(() => {
          setSuccessMessage("");
        }, 3000);
      }
    } catch (error) {
      console.error("Error updating hotel:", error);
      setErrorMessage("Lỗi khi cập nhật thông tin khách sạn.");
      setSuccessMessage("");
    }
  };

  const descriptionModule = {
    toolbar: [
      ["bold", "italic", "underline", "strike"],
      [{ header: 1 }, { header: 2 }],
      [
        { list: "ordered" },
        { list: "bullet" },
        { indent: "-1" },
        { indent: "+1" },
      ],
      [{ size: ["small", false, "large", "huge"] }],
      [{ header: [1, 2, 3, 4, 5, 6, false] }],
      [{ font: [] }],
      [{ align: [] }],
      ["image"],
      ["clean"],
    ],
  };

  return (
    <div
      className="editHotelModel modal fade"
      id="editHotelModel"
      tabIndex="-1"
      aria-labelledby="editHotelModelLabel"
      aria-hidden="true"
    >
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="editHotelModelLabel">
              Sửa thông tin khách sạn
            </h5>
            <button
              type="button"
              className="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>

          <div className="modal-body">
            <div className="mb-3">
              <label htmlFor="hotelName" className="form-label">
                Tên khách sạn:
              </label>
              <input
                type="text"
                className="form-control"
                id="hotelName"
                value={hotelName}
                onChange={(e) => setHotelName(e.target.value)}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="city" className="form-label">
                Thành phố:
              </label>
              <input
                type="text"
                className="form-control"
                id="city"
                value={city}
                onChange={(e) => setCity(e.target.value)}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="hotelLocation" className="form-label">
                Địa chỉ:
              </label>
              <input
                type="text"
                className="form-control"
                id="hotelLocation"
                value={hotelLocation}
                onChange={(e) => setHotelLocation(e.target.value)}
              />
            </div>

            <div className="react-quill">
              <label htmlFor="hotelDescription" className="form-label">
                Mô tả:
              </label>
              <ReactQuill
                className="reactQuill"
                id="hotelDescription"
                modules={descriptionModule}
                theme="snow"
                value={hotelDescription}
                onChange={setHotelDescription}
                style={{ height: "250px" }}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="phoneNumber" className="form-label">
                Số điện thoại:
              </label>
              <input
                type="tel"
                className="form-control"
                id="phoneNumber"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="photo" className="form-label">
                Chọn ảnh khách sạn:
              </label>
              <input
                className="form-control"
                type="file"
                id="photo"
                onChange={handlePhotoChange}
              />
              {base64Photo && (
                <div className="mt-3">
                  <h6>Hình ảnh hiện tại:</h6>
                  <img
                    src={base64Photo}
                    alt="Current"
                    style={{ maxWidth: "100%", height: "auto" }}
                  />
                </div>
              )}
            </div>
          </div>

          {successMessage && (
            <div className="alert alert-success" role="alert">
              {successMessage}
            </div>
          )}
          {errorMessage && (
            <div className="alert alert-danger" role="alert">
              {errorMessage}
            </div>
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
              onClick={handleUpdate}
            >
              Cập nhật
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EditHotel;
