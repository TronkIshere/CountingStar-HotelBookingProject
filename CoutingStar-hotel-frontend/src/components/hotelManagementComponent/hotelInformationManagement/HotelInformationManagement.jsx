import React, { useState, useEffect } from "react";
import "./hotelInformationManagement.css";
import {
  getHotelById,
  updateHotel,
} from "../../../components/utils/ApiFunction";
import { useParams } from "react-router-dom";
import ReactQuill from "react-quill";

const HotelInformationManagement = () => {
  const [hotel, setHotel] = useState({
    hotelName: "",
    city: "",
    hotelLocation: "",
    hotelDescription: "",
    phoneNumber: "",
    photo: "",
  });

  const [imagePreview, setImagePreview] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleImageChange = (e) => {
    const selectedImage = e.target.files[0];
    setHotel({ ...hotel, photo: selectedImage });
    setImagePreview(URL.createObjectURL(selectedImage));
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setHotel({ ...hotel, [name]: value });
  };

  const { hotelId } = useParams();

  useEffect(() => {
    const fetchRoom = async () => {
      try {
        const hotelData = await getHotelById(hotelId);
        setHotel(hotelData);
        setImagePreview(hotelData.photo);
      } catch (error) {
        console.log(error);
      }
    };

    fetchRoom();
  }, [hotelId]);

  const handleDescriptionChange = (value) => {
    setHotel((prevHotel) => ({
      ...prevHotel,
      description: value,
    }));
  };

  const descriptionModule = {
    toolbar: [
      ["bold", "italic", "underline", "strike"],
      [{ header: 1 }, { header: 2 }],
      [{ list: "ordered" }, { list: "bullet" }, { indent: "-1" }, { indent: "+1" }],
      [{ size: ["small", false, "large", "huge"] }],
      [{ header: [1, 2, 3, 4, 5, 6, false] }],
      [{ font: [] }],
      [{ align: [] }],
      ["image"],
      ["clean"],
    ],
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await updateHotel(hotelId, hotel);
      if (response.status === 200) {
        setSuccessMessage("Chỉnh sửa khách sạn thành công!!!");
        const updatedhotelData = await getHotelById(hotelId);
        setHotel(updatedhotelData);
        setImagePreview(updatedhotelData.photo);
        setErrorMessage("");
      } else {
        setErrorMessage("Đã xảy ra lỗi!!!");
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
  };

  return (
    <div className="hotelInformationManagement">
      <div className="container">
        <form onSubmit={handleSubmit} className="form">
          <h2>Chỉnh sửa thông tin khách sạn</h2>
          <div className="row mb-3">
            <div className="col-12 col-sm-12 col-md-6 col-lg-6">
              <label htmlFor="hotelName" className="form-label">
                Tên khách sạn:
              </label>
              <input
                type="text"
                className="form-control"
                id="hotelName"
                name="hotelName"
                value={hotel.hotelName}
                onChange={handleInputChange}
                required
              />
            </div>
            <div className="col-12 col-sm-12 col-md-6 col-lg-6">
              <label htmlFor="city" className="form-label">
                Thành phố:
              </label>
              <input
                type="text"
                className="form-control"
                id="city"
                name="city"
                value={hotel.city}
                onChange={handleInputChange}
                required
              />
            </div>
          </div>
          <div className="row mb-3">
            <div className="col-12 col-sm-12 col-md-6 col-lg-6">
              <label htmlFor="hotelLocation" className="form-label">
                Địa chỉ:
              </label>
              <input
                type="text"
                className="form-control"
                id="hotelLocation"
                name="hotelLocation"
                value={hotel.hotelLocation}
                onChange={handleInputChange}
                required
              />
            </div>
            <div className="col-12 col-sm-12 col-md-6 col-lg-6">
              <label htmlFor="phoneNumber" className="form-label">
                Số điện thoại:
              </label>
              <input
                type="tel"
                className="form-control"
                id="phoneNumber"
                name="phoneNumber"
                value={hotel.phoneNumber}
                onChange={handleInputChange}
                required
              />
            </div>
          </div>
          <div className="row mb-5">
          <label htmlFor="specifications" className="form-label">
              Mô tả:
            </label>
            <ReactQuill
              className="reactQuill"
              id="description"
              modules={descriptionModule}
              theme="snow"
              value={hotel.description}
              onChange={handleDescriptionChange}
              style={{ height: "250px" }}
            />
          </div>
          <div className="form-group mb-3">
            <label>Ảnh của khách sạn:</label>
            <input type="file" name="hotelImage" onChange={handleImageChange} />
            {imagePreview && (
              <img
                src={`data:image/jpeg;base64,${imagePreview}`}
                alt="Room preview"
                className="imagePreview"
              />
            )}
          </div>

          <button type="submit" className="main-btn">
            Lưu
          </button>
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
        </form>
      </div>
    </div>
  );
};

export default HotelInformationManagement;
