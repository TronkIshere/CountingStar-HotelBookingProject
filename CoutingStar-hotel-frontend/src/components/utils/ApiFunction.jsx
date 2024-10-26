import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:9192",
});

export const getHeader = () => {
  const token = localStorage.getItem("token");
  return {
    Authorization: `Bearer ${token}`,
  };
};

// **********************
// HOTEL FUNCTION
// **********************
export async function addHotel(
  userId,
  hotelName,
  city,
  hotelLocation,
  hotelDescription,
  phoneNumber,
  photo
) {
  const formData = new FormData();
  formData.append("hotelName", hotelName);
  formData.append("city", city);
  formData.append("hotelLocation", hotelLocation);
  formData.append("hotelDescription", hotelDescription);
  formData.append("phoneNumber", phoneNumber);
  formData.append("photo", photo);

  const response = await api.post(`/hotels/${userId}/addHotel`, formData);
  return response;
}

export async function getAllHotels(pageNo = 0, pageSize = 8) {
  try {
    const response = await api.get(`/hotels/all-hotels`, {
      params: { pageNo, pageSize },
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching hotels:", error);
    throw new Error("Error fetching hotels");
  }
}

export async function getHotelByKeyword(pageNo = 0, pageSize = 8, keyword) {
  try {
    const response = await api.get(
      `/hotels/getHotelByKeyword/${encodeURIComponent(keyword)}`,
      {
        params: { pageNo, pageSize },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching hotels:", error);
    throw new Error("Error fetching hotels");
  }
}

export async function getAllRoomByKeywordAndHotelId(
  pageNo = 0,
  pageSize = 8,
  keyword,
  hotelId
) {
  try {
    const response = await api.get(
      `/rooms/getAllRoomByKeyword/${hotelId}/${encodeURIComponent(keyword)}`,
      {
        params: { pageNo, pageSize },
      }
    );
    return response.data;
  } catch (error) {
    throw new Error("Error fetching hotels");
  }
}

export async function getHotelsByCity(city, pageNo = 0, pageSize = 10) {
  try {
    const response = await api.get(`/hotels/${encodeURIComponent(city)}`, {
      params: { pageNo, pageSize },
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching hotels:", error);
    throw new Error("Error fetching hotels");
  }
}

export async function getHotelById(hotelId) {
  try {
    const response = await api.get(`/hotels/hotel/${hotelId}`);
    return response.data;
  } catch (error) {
    throw new Error(`Error fetching hotels: ${error.message}`);
  }
}

export async function getFiveHotelForHomePage() {
  try {
    const response = await api.get(`/hotels/homepage`);
    return response.data;
  } catch (error) {
    throw new Error("Error fetching hotels");
  }
}

export async function updateHotel(hotelId, hotelData) {
  const formData = new FormData();
  formData.append("hotelName", hotelData.hotelName);
  formData.append("city", hotelData.city);
  formData.append("hotelLocation", hotelData.hotelLocation);
  formData.append("hotelDescription", hotelData.hotelDescription);
  formData.append("phoneNumber", hotelData.phoneNumber);
  formData.append("photo", hotelData.photo);

  const response = await api.put(
    `/hotels/hotel/${hotelId}/hotelInformationUpdate`,
    formData,
    {
      headers: getHeader(),
    }
  );

  return response;
}

export async function deleteHotel(hotelId) {
  try {
    const result = await api.delete(`/hotel/${hotelId}/delete`, {
      headers: getHeader(),
    });
    return result.data;
  } catch (error) {
    throw new Error(`Error deleting room ${error.message}`);
  }
}

// **********************
// ROOM FUNCTION
// **********************
export async function addRoom(
  photo,
  roomType,
  roomPrice,
  roomDescription,
  hotelId
) {
  const formData = new FormData();
  formData.append("photo", photo);
  formData.append("roomType", roomType);
  formData.append("roomPrice", roomPrice);
  formData.append("roomDescription", roomDescription);
  console.log(roomType);
  const response = await api.post(`/rooms/add/new-room/${hotelId}`, formData, {
    headers: getHeader(),
  });
  if (response.status === 201) {
    return true;
  } else {
    return false;
  }
}

export async function getRoomTypes() {
  try {
    const response = await api.get("/rooms/room/types");
    return response.data;
  } catch (error) {
    throw new Error("Error fetching room types");
  }
}

export async function getAllRooms() {
  try {
    const result = await api.get("/rooms/all-rooms");
    return result.data;
  } catch (error) {
    throw new Error("Error fetching rooms");
  }
}

export async function deleteRoom(roomId) {
  try {
    const result = await api.delete(`/rooms/delete/room/${roomId}`, {
      headers: getHeader(),
    });
    return result.data;
  } catch (error) {
    throw new Error(`Error deleting room ${error.message}`);
  }
}

export async function updateRoom(roomId, roomData) {
  const formData = new FormData();
  formData.append("roomType", roomData.roomType);
  formData.append("roomDescription", roomData.roomDescription);
  formData.append("roomPrice", roomData.roomPrice);
  formData.append("photo", roomData.photo);
  console.log(roomData.roomType);
  console.log(roomData.roomDescription);
  console.log(roomData.roomPrice);
  const response = await api.put(`/rooms/update/${roomId}`, formData, {
    headers: getHeader(),
  });
  return response;
}

export async function getRoomById(roomId) {
  try {
    const result = await api.get(`/rooms/room/${roomId}`);
    return result.data;
  } catch (error) {
    throw new Error(`Error fetching room ${error.message}`);
  }
}

export async function getRoomsByHotelId(hotelId, pageNo = 0, pageSize = 8) {
  try {
    const result = await api.get(`/rooms/${hotelId}`, {
      params: { pageNo, pageSize },
    });
    return result.data;
  } catch (error) {
    throw new Error(`Error fetching room ${error.message}`);
  }
}

// **********************
// BOOKING FUNCTION
// **********************
export async function bookRoom(roomId, booking, userId, redeemedDiscountId) {
  try {
    let url = `/bookings/room/${roomId}/booking?userId=${userId}`;
    if (redeemedDiscountId !== null) {
      url += `&redeemedDiscountId=${redeemedDiscountId}`;
    }
    const response = await api.post(url, booking, { headers: getHeader() });
    console.log("API response:", response);
  } catch (error) {
    if (error.response && error.response.data) {
      console.error("API error:", error.response.data);
      throw new Error(error.response.data);
    } else {
      console.error("API error:", error.message);
      throw new Error(`Error booking room: ${error.message}`);
    }
  }
}

export async function getAllBookings() {
  try {
    const result = await api.get("/bookings/all-bookings");
    console.log("API response:", result);
    return result.data;
  } catch (error) {
    throw new Error(`Error fetching bookings : ${error.message}`);
  }
}

export async function getBookingByConfirmationCode(confirmationCode) {
  try {
    const result = await api.get(`/bookings/confirmation/${confirmationCode}`);
    return result.data;
  } catch (error) {
    if (error.response && error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error(`Error find booking : ${error.message}`);
    }
  }
}

export async function getBookingByHotelId(pageNo = 0, pageSize = 8, hotelId) {
  try {
    const result = await api.get(`/bookings/hotel/${hotelId}/booking`, {
      params: { pageNo, pageSize },
    });
    return result.data;
  } catch (error) {
    if (error.response && error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error(`Error find booking:  ${error.message}`);
    }
  }
}

export async function getAllBookingByKeywordAndHotelId(
  pageNo = 0,
  pageSize = 8,
  hotelId,
  keyword
) {
  try {
    const result = await api.get(
      `/bookings/getAllBookingByKeywordAndHotelId/${hotelId}/${encodeURIComponent(
        keyword
      )}`,
      {
        params: { pageNo, pageSize },
      }
    );
    return result.data;
  } catch (error) {
    if (error.response && error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error(`Error find booking:  ${error.message}`);
    }
  }
}

export async function getBookingByBookingId(bookingId) {
  try {
    const result = await api.get(`/bookings/booking/${bookingId}`);
    console.log(result);
    return result.data;
  } catch (error) {
    if (error.response && error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error(`Error find booking:  ${error.message}`);
    }
  }
}

export async function updateBooking(bookingId, bookingData) {
  const formData = new FormData();
  formData.append("checkInDate", bookingData.checkInDate);
  formData.append("checkOutDate", bookingData.checkOutDate);
  formData.append("guestEmail", bookingData.guestEmail);
  formData.append("guestPhoneNumber", bookingData.guestPhoneNumber);
  formData.append("guestFullName", bookingData.guestFullName);
  formData.append("totalNumOfGuest", bookingData.totalNumOfGuest);
  const result = await api.put(
    `/bookings/booking/${bookingId}/update`,
    formData
  );
  return result;
}

export async function cancelBooking(bookingId) {
  try {
    const result = await api.delete(`/bookings/booking/${bookingId}/delete`);
    return result.data;
  } catch (error) {
    throw new Error(`Error cancelling booking :${error.message}`);
  }
}

export async function getAvailableRooms(checkInDate, checkOutDate, roomType) {
  const result = await api.get(
    `rooms/available-rooms?checkInDate=${checkInDate}
		&checkOutDate=${checkOutDate}&roomType=${roomType}`
  );
  return result;
}

export async function getBookingsByUserId(userId, token) {
  try {
    const response = await api.get(`/bookings/user/${userId}/bookings`, {
      headers: getHeader(),
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching bookings:", error.message);
    throw new Error("Failed to fetch bookings");
  }
}

// **********************
// USER FUNCTION
// **********************
export async function registerUser(registration) {
  try {
    const response = await api.post("/auth/register-user", registration);
    return response.data;
  } catch (error) {
    if (error.response && error.response.data) {
      throw new Error(JSON.stringify(error.response.data));
    } else {
      throw new Error(`User registration error: ${error.message}`);
    }
  }
}

export async function registerHotelOwner(registration) {
  try {
    const response = await api.post("/auth/register-hotelOwner", registration);
    return response.data;
  } catch (error) {
    if (error.response && error.response.data) {
      throw new Error(JSON.stringify(error.response.data));
    } else {
      throw new Error(`User registration error: ${error.message}`);
    }
  }
}

export async function loginUser(login) {
  try {
    const response = await api.post("/auth/login", login);
    if (response.status >= 200 && response.status < 300) {
      return response.data;
    } else {
      return null;
    }
  } catch (error) {
    console.error(error);
    return null;
  }
}

export async function getUserByUserId(userId) {
  try {
    const response = await api.get(`users/getUserByUserId/${userId}`, {
      headers: getHeader(),
    });
    return response.data;
  } catch (error) {
    throw error;
  }
}

export async function getUserProfile(userId, token) {
  try {
    const response = await api.get(`users/profile/${userId}`, {
      headers: getHeader(),
    });
    return response.data;
  } catch (error) {
    throw error;
  }
}

export async function getAllUserExceptAminRole(pageNo = 0, pageSize = 8) {
  try {
    const response = await api.get(`users/getAllUserExceptAminRole`, {
      headers: getHeader(),
      params: { pageNo, pageSize },
    });
    return response.data;
  } catch (error) {
    throw error;
  }
}

export async function searchUserByKeyWord(pageNo = 0, pageSize = 8, keyword) {
  try {
    const response = await api.get(
      `users/searchUserByKeyWord/${encodeURIComponent(keyword)}`,
      {
        headers: getHeader(),
        params: { pageNo, pageSize },
      }
    );
    return response.data;
  } catch (error) {
    throw error;
  }
}

export async function updateUser(
  userId,
  firstName,
  lastName,
  email,
  phoneNumber
) {
  const formData = new FormData();
  formData.append("firstName", firstName);
  formData.append("lastName", lastName);
  formData.append("email", email);
  formData.append("phoneNumber", phoneNumber);
  const response = await api.post(`/users/updateUser/${userId}`, formData, {
    headers: getHeader(),
  });
  if (response.status === 201) {
    return true;
  } else {
    return false;
  }
}

export async function deleteUser(userId) {
  try {
    const response = await api.delete(`/users/delete/${userId}`, {
      headers: getHeader(),
    });
    return response.data;
  } catch (error) {
    return error.message;
  }
}

export async function getUserByEmail(email, token) {
  try {
    const response = await api.get(`/users/${email}`, {
      headers: getHeader(),
    });
    return response.data;
  } catch (error) {
    throw error;
  }
}

export async function checkIfUserCanComment(userId, hotelId) {
  try {
    const response = await api.get(
      `/ratings/hotel/${hotelId}/CheckUserRating/${userId}`
    );
    console.log(response.data);
    return response.data;
  } catch (error) {
    throw new Error("Faild to check");
  }
}

// **********************
// RATING FUNCTION
// **********************
export async function addNewRating(userId, hotelId, star, comment, rateDay) {
  const formData = new FormData();
  formData.append("star", star);
  formData.append("comment", comment);
  formData.append("rateDay", rateDay);

  const response = await api.post(
    `ratings/add/hotel/${hotelId}/user/${userId}/addRating`,
    formData
  );
  if (response.status === 201) {
    return true;
  } else {
    return false;
  }
}

export async function getAllRatingByHotelId(hotelId) {
  try {
    const response = await api.get(`/ratings/hotel/${hotelId}`);
    return response.data;
  } catch (error) {
    throw new Error("Faild to fetch Rating");
  }
}

// **********************
// DASHBOARD FUNCTION
// **********************
export async function getDataForDashBoardMonthIncreased() {
  try {
    const response = await api.get(`/dashboard/Admin/dashBoardMonthIncreased`);
    return response.data;
  } catch (error) {
    throw new Error("Error fetching dashboard data");
  }
}

export async function getDataForHotelDashBoardMonthIncreased(hotelId) {
  try {
    console.log(hotelId);
    const response = await api.get(
      `/dashboard/HotelOwner/dashBoardMonthIncreased/${hotelId}`
    );
    return response.data;
  } catch (error) {
    throw new Error("Error fetching dashboard data");
  }
}

export async function getDataForAdminPieChart() {
  try {
    const response = await api.get("/dashboard/Admin/PieChart");
    return response.data;
  } catch (error) {
    throw new Error("Error fetching data");
  }
}

export async function getDataForHotelOwnerPieChart(hotelId) {
  try {
    const response = await api.get(`/dashboard/HotelOwner/PieChart/${hotelId}`);
    return response.data;
  } catch (error) {
    throw new Error("Error fetching data");
  }
}

export async function getDataForAdminBarChart() {
  try {
    const response = await api.get("/dashboard/Admin/BarChart");
    return response.data;
  } catch (error) {
    throw new Error("Error fetching data");
  }
}

// **********************
// DISCOUNT FUNCTION
// **********************

export async function getAllDiscount(pageNo = 0, pageSize = 8) {
  try {
    const response = await api.get("/discounts/getAllDiscount", {
      headers: getHeader(),
      params: { pageNo, pageSize },
    });
    return response.data;
  } catch (error) {
    throw new Error("Error fetching data");
  }
}

export async function getDiscountNotExpired(pageNo = 0, pageSize = 8) {
  try {
    const response = await api.get("/discounts/getDiscountNotExpired", {
      headers: getHeader(),
      params: { pageNo, pageSize },
    });
    return response.data;
  } catch (error) {
    throw new Error("Error fetching data");
  }
}

export async function getDiscountByKeyword(pageNo = 0, pageSize = 8, keyword) {
  try {
    const response = await api.get(
      `/discounts/getDiscountByKeyword/${encodeURIComponent(keyword)}`,
      {
        headers: getHeader(),
        params: { pageNo, pageSize },
      }
    );
    return response.data;
  } catch (error) {
    throw new Error("Error fetching data");
  }
}

export async function getDiscountById(discountId) {
  try {
    const response = await api.get(`/discounts/getDiscountById/${discountId}`);
    return response.data;
  } catch (error) {
    throw new Error("Error feching data");
  }
}

export async function addDiscount(
  discountName,
  percentDiscount,
  discountDescription,
  expirationDate
) {
  const formData = new FormData();
  formData.append("discountName", discountName);
  formData.append("percentDiscount", percentDiscount);
  formData.append("discountDescription", discountDescription);
  formData.append("expirationDate", expirationDate);
  const response = await api.post("/discounts/addDiscount", formData, {
    headers: getHeader(),
  });
  return response.data;
}

export async function updateDiscount(discountId, updatedDiscount) {
  const formData = new FormData();
  formData.append("discountName", updatedDiscount.discountName);
  formData.append("percentDiscount", updatedDiscount.percentDiscount);
  formData.append("discountDescription", updatedDiscount.discountDescription);
  formData.append("expirationDate", updatedDiscount.expirationDate);
  const result = await api.put(`/discounts/update/${discountId}`, formData, {
    headers: getHeader(),
  });
  return result;
}

export async function deleteDiscount(discountId) {
  try {
    const response = await api.delete(`/discounts/delete/${discountId}`, {
      headers: getHeader(),
    });
    return response.data;
  } catch (error) {
    return error.message;
  }
}

export async function addRedeemedDiscount(discountId, userId) {
  try {
    const response = await api.post(
      `/redeemedDiscount/add/${discountId}/${userId}`
    );

    if (response.status !== 200) {
      const errorText = response.data || response.statusText;
      throw new Error(errorText);
    }

    return response;
  } catch (error) {
    if (error.response && error.response.data) {
      console.error("Error in addRedeemedDiscount:", error.response.data);
      throw new Error(error.response.data);
    } else {
      console.error("Error in addRedeemedDiscount:", error.message);
      throw new Error("Có lỗi xảy ra khi thực hiện yêu cầu.");
    }
  }
}

export async function getAllRedeemedDiscountByUserId(userId) {
  try {
    const response = await api.get(`/redeemedDiscount/list/${userId}`);
    return response.data;
  } catch (error) {
    throw new Error("Error feching data");
  }
}
