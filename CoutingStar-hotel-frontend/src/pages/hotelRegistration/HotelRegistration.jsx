import React, { useState } from "react";
import "./hotelRegistration.css";
import {
  registerHotelOwner,
  addHotel,
} from "../../components/utils/ApiFunction";
import ReactQuill from "react-quill";

const HotelRegistration = () => {
  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    phoneNumber: "",
  });

  const [hotel, setHotel] = useState({
    hotelName: "",
    city: "",
    hotelLocation: "",
    description: "",
    hotelPhone: "",
    photo: null,
  });

  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [imagePreview, setImagePreview] = useState("");

  const handleRegistration = async (e) => {
    e.preventDefault();
    try {
      const hotelOwner = await registerHotelOwner(user);

      try {
        const response = await addHotel(
          hotelOwner.id,
          hotel.hotelName,
          hotel.city,
          hotel.hotelLocation,
          hotel.description,
          hotel.hotelPhone,
          hotel.photo
        );

        if (response.status === 200 || response.status === 201) {
          setSuccessMessage(response.data || "Hotel registered successfully!");
        } else {
          setErrorMessage(response.data || "Failed to register hotel.");
        }
      } catch (addHotelError) {
        let errorMessage = "Failed to register hotel: ";
        if (addHotelError.response && addHotelError.response.data) {
          errorMessage += JSON.stringify(addHotelError.response.data);
        } else {
          errorMessage += addHotelError.message;
        }
        setErrorMessage(errorMessage);
      }
    } catch (registerHotelOwnerError) {
      let errorMessage = "Failed to register hotel owner: ";
      if (
        registerHotelOwnerError.response &&
        registerHotelOwnerError.response.data
      ) {
        errorMessage += JSON.stringify(registerHotelOwnerError.response.data);
      } else {
        errorMessage += registerHotelOwnerError.message;
      }
      setErrorMessage(errorMessage);
    }

    setTimeout(() => {
      setErrorMessage("");
      setSuccessMessage("");
    }, 5000);
  };

  const handleUserChange = (e) => {
    const { name, value } = e.target;
    setUser((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleHotelChange = (e) => {
    const { name, value } = e.target;
    setHotel((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleImageChange = (e) => {
    const selectedImage = e.target.files[0];
    setHotel({ ...hotel, photo: selectedImage });
    setImagePreview(URL.createObjectURL(selectedImage));
  };

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

  return (
    <div className="hotelRegistration">
      <div className="container my-4">
        <form
          onSubmit={handleRegistration}
          className="form"
          encType="multipart/form-data"
        >
          <h2>Thông tin chủ khách sạn</h2>
          <div className="row mb-3">
            <div className="col-md-6">
              <label className="form-label">Họ và tên đệm:</label>
              <input
                type="text"
                name="firstName"
                className="form-control"
                value={user.firstName}
                onChange={handleUserChange}
                required
              />
            </div>
            <div className="col-md-6">
              <label className="form-label">Tên:</label>
              <input
                type="text"
                name="lastName"
                className="form-control"
                value={user.lastName}
                onChange={handleUserChange}
                required
              />
            </div>
          </div>
          <div className="row mb-3">
            <div className="col-md-6">
              <label className="form-label">Mật khẩu:</label>
              <input
                type="password"
                name="password"
                className="form-control"
                value={user.password}
                onChange={handleUserChange}
                required
              />
            </div>
            <div className="col-md-6">
              <label className="form-label">Số điện thoại:</label>
              <input
                type="tel"
                name="phoneNumber"
                className="form-control"
                value={user.phoneNumber}
                onChange={handleUserChange}
                required
              />
            </div>
          </div>
          <div className="row mb-3">
            <div className="col-md-6">
              <label className="form-label">Email:</label>
              <input
                type="email"
                name="email"
                className="form-control"
                value={user.email}
                onChange={handleUserChange}
                required
              />
            </div>
          </div>
          <h2>Thông tin khách sạn</h2>
          <div className="row mb-3">
            <div className="col-md-6">
              <label className="form-label">Tên khách sạn:</label>
              <input
                type="text"
                name="hotelName"
                className="form-control"
                value={hotel.hotelName}
                onChange={handleHotelChange}
                required
              />
            </div>
            <div className="col-md-6">
              <label className="form-label">Thành phố:</label>
              <select
                name="city"
                className="form-select"
                value={hotel.city}
                onChange={handleHotelChange}
                required
              >
                <option value="" disabled>
                  Khách sạn bạn ở thành phố nào?
                </option>
                <option value="Hồ Chí Minh">Hồ Chí Minh</option>
                <option value="Hà Nội">Hà Nội</option>
                <option value="Đà Lạt">Đà Lạt</option>
                <option value="Nha Trang">Nha Trang</option>
                <option value="Vũng Tàu">Vũng Tàu</option>
              </select>
            </div>
          </div>
          <div className="row mb-3">
            <div className="col-md-6">
              <label className="form-label">Địa chỉ:</label>
              <input
                type="text"
                name="hotelLocation"
                className="form-control"
                value={hotel.hotelLocation}
                onChange={handleHotelChange}
                required
              />
            </div>
            <div className="col-md-6">
              <label className="form-label">Số điện thoại khách sạn:</label>
              <input
                type="tel"
                name="hotelPhone"
                className="form-control"
                value={hotel.hotelPhone}
                onChange={handleHotelChange}
                required
              />
            </div>
          </div>

          <div className="reactQuill mb-5">
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

          <div className="mb-3">
            <label className="form-label">Ảnh của khách sạn:</label>
            <input
              type="file"
              name="photo"
              className="form-control"
              onChange={handleImageChange}
              required
            />
            {imagePreview && (
              <div className="imagePreview mt-3">
                <img className="inputImg" src={imagePreview} alt="Preview" />
              </div>
            )}
          </div>

          <button type="submit" className="main-btn">
            Đăng ký khách sạn
          </button>

          {errorMessage && (
            <div className="alert alert-danger mt-3">{errorMessage}</div>
          )}
          {successMessage && (
            <div className="alert alert-success mt-3">{successMessage}</div>
          )}
        </form>
      </div>
    </div>
  );
};

export default HotelRegistration;
