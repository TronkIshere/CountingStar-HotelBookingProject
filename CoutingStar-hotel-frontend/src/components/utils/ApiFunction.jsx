import axios from "axios"

export const api = axios.create({
    baseURL :"http://localhost:9192"
})

export const getHeader = () =>{
	const token = localStorage.getItem("token")
	return{
		Authorization : `Bearer ${token}`
	}
}

/* Add new hotel function */
export async function addHotel(userId, hotelName, city, hotelLocation, hotelDescription, phoneNumber, photo) {
	const formData = new FormData()
	formData.append("hotelName", hotelName)
	formData.append("city", city)
	formData.append("hotelLocation", hotelLocation)
	formData.append("hotelDescription", hotelDescription)
	formData.append("phoneNumber", phoneNumber)
	formData.append("photo", photo)

	const response = await api.post(`/hotels/${userId}/addHotel`, formData)
	return response;
}

/* Get all hotels by city */
export async function getHotelsByCity(city) {
	try {
		const response = await api.get(`/hotels/${city}`)
		return response.data
	} catch (error) {
		throw new Error("Error fetching hotels")
	}
}

export async function getHotelById(hotelId) {
	try {
		const response = await api.get(`/hotels/hotel/${hotelId}`)
		return response.data
	} catch (error) {
		throw new Error("Error fetching hotels")
	}
}

export async function getFiveHotelForHomePage() {
	try {
		const response = await api.get(`/hotels/homepage`)
		return response.data
	} catch (error) {
		throw new Error("Error fetching hotels")
	}
}

/* Update hotel informtaion */
export async function updateHotel(hotelId, hotelData){
	const formData = new FormData()
	formData.append("hotelName", hotelData.hotelName)
	formData.append("city", hotelData.city)
	formData.append("hotelLocation", hotelData.hotelLocation)
	formData.append("hotelDescription", hotelData.hotelDescription)
	formData.append("phoneNumber", hotelData.phoneNumber)
	formData.append("photo", hotelData.photo)
	const response = await api.put(`/hotels/hotel/${hotelId}/hotelInformationUpdate`, formData)
	return response
}

export async function deleteHotel(hotelId) {
	try {
		const result = await api.delete(`/hotel/${hotelId}/delete`, {
			headers: getHeader()
		})
		return result.data
	} catch (error) {
		throw new Error(`Error deleting room ${error.message}`)
	}
}

/* This function adds a new room room to the database */
export async function addRoom(photo, roomType, roomPrice, roomDescription, hotelId) {
	const formData = new FormData()
	formData.append("photo", photo)
	formData.append("roomType", roomType)
	formData.append("roomPrice", roomPrice)
	formData.append("roomDescription", roomDescription)
	console.log(roomType)
	const response = await api.post(`/rooms/add/new-room/${hotelId}`, formData, {
		headers: getHeader()
	})
	if (response.status === 201) {
		return true
	} else {
		return false
	}
}


/* This function gets all room types from thee database */
export async function getRoomTypes() {
	try {
		const response = await api.get("/rooms/room/types")
		return response.data
	} catch (error) {
		throw new Error("Error fetching room types")
	}
}

/* This functions gets all rooms from the database */ 
export async function getAllRooms(){
	try{
		const result = await api.get("/rooms/all-rooms")
		return result.data
	} catch(error) {
		throw new Error("Error fetching rooms")
	}
}

/* This function deletes a room by the Id */
export async function deleteRoom(roomId) {
	try {
		const result = await api.delete(`/rooms/delete/room/${roomId}`, {
			headers: getHeader()
		})
		return result.data
	} catch (error) {
		throw new Error(`Error deleting room ${error.message}`)
	}
}

/* This function update a room by the id*/
export async function updateRoom(roomId, roomData){
	const formData = new FormData()
	formData.append("roomType", roomData.roomType)
	formData.append("roomDescription", roomData.roomDescription)
	formData.append("roomPrice", roomData.roomPrice)
	formData.append("photo", roomData.photo)
	console.log(roomData.roomType)
	console.log(roomData.roomDescription)
	console.log(roomData.roomPrice)
	const response = await api.put(`/rooms/update/${roomId}`, formData, {
		headers: getHeader()
	})
	return response
}

/* This function gets a room by the id*/
export async function getRoomById(roomId){
	try{
		const result = await api.get(`/rooms/room/${roomId}`)
		return result.data
	} catch(error) {
		throw new Error(`Error fetching room ${error.message}`)
	}
}

export async function getRoomsByHotelId(hotelId){
	try{
		const result = await api.get(`/rooms/${hotelId}`)
		return result.data;
	} catch(error){
		throw new Error(`Error fetching room ${err.message}`)
	}
}

/* This function saves a new booking to the databse */
export async function bookRoom(roomId, booking, userId) {
    try {

		console.log(roomId)
		console.log(booking)
		console.log(userId)

        const response = await api.post(
			`/bookings/room/${roomId}/booking?userId=${userId}`,
			booking, 
			{headers: getHeader()}
		);
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

/* This function gets alll bokings from the database */
export async function getAllBookings() {
	try {
		const result = await api.get("/bookings/all-bookings")
		console.log("API response:", result);
		return result.data
	} catch (error) {
		throw new Error(`Error fetching bookings : ${error.message}`)
	}
}

/* This function get booking by the cnfirmation code */
export async function getBookingByConfirmationCode(confirmationCode) {
	try {
		const result = await api.get(`/bookings/confirmation/${confirmationCode}`)
		return result.data
	} catch (error) {
		if (error.response && error.response.data) {
			throw new Error(error.response.data)
		} else {
			throw new Error(`Error find booking : ${error.message}`)
		}
	}
}

export async function getBookingByHotelId(hotelId){
	try{
		const result = await api.get(`/bookings/hotel/${hotelId}/booking`)
		return result.data
	} catch (error) {
		if (error.response && error.response.data) {
			throw new Error(error.response.data)
		} else {
			throw new Error(`Error find booking:  ${error.message}`)
		}
	}
}

export async function getBookingByBookingId(bookingId, bookingData){
	try{
		const result = await api.get(`/bookings/booking/${bookingId}`)
		return result.data
	} catch (error) {
		if (error.response && error.response.data) {
			throw new Error(error.response.data)
		} else {
			throw new Error(`Error find booking:  ${error.message}`)
		}
	}
}

export async function updateBooking(bookingId, bookingData){
	const formData = new FormData()
	formData.append("checkInDate", bookingData.checkInDate)
	formData.append("checkOutDate", bookingData.checkOutDate)
	formData.append("guestEmail", bookingData.guestEmail)
	formData.append("guestPhoneNumber", bookingData.guestPhoneNumber)
	formData.append("guestFullName", bookingData.guestFullName)
	formData.append("totalNumOfGuest", bookingData.totalNumOfGuest)
	const result = await api.put(`/bookings/booking/${bookingId}/update`, formData)
	return result
}

/* This is the function to cancel user booking */
export async function cancelBooking(bookingId) {
	try {
		const result = await api.delete(`/bookings/booking/${bookingId}/delete`)
		return result.data
	} catch (error) {
		throw new Error(`Error cancelling booking :${error.message}`)
	}
}

/* This function gets all availavle rooms from the database with a given date and a room type */
export async function getAvailableRooms(checkInDate, checkOutDate, roomType) {
	const result = await api.get(
		`rooms/available-rooms?checkInDate=${checkInDate}
		&checkOutDate=${checkOutDate}&roomType=${roomType}`
	)
	return result
}

/* This function register a new user */
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

/* This function login a registered user */
export async function loginUser(login) {
	try {
		const response = await api.post("/auth/login", login)
		if (response.status >= 200 && response.status < 300) {
			return response.data
		} else {
			return null
		}
	} catch (error) {
		console.error(error)
		return null
	}
}

/*  This is function to get the user profile */
export async function getUserProfile(userId, token) {
	try {
		const response = await api.get(`users/profile/${userId}`, {
			headers: getHeader()
		})
		return response.data
	} catch (error) {
		throw error
	}
}

/* This isthe function to delete a user */
export async function deleteUser(userId) {
	try {
		const response = await api.delete(`/users/delete/${userId}`, {
			headers: getHeader()
		})
		return response.data
	} catch (error) {
		return error.message
	}
}

/* This is the function to get a single user */
export async function getUserByEmail(email, token) {
	try {
		const response = await api.get(`/users/${email}`, {
			headers: getHeader()
		})
		return response.data
	} catch (error) {
		throw error
	}
}

/* This is the function to get user bookings by the user id */
export async function getBookingsByUserId(userId, token) {
	try {
		const response = await api.get(`/bookings/user/${userId}/bookings`, {
			headers: getHeader()
		})
		return response.data
	} catch (error) {
		console.error("Error fetching bookings:", error.message)
		throw new Error("Failed to fetch bookings")
	}
}

export async function checkIfUserCanComment(userId, hotelId){
	try{
		const response = await api.get(`/ratings/hotel/${hotelId}/CheckUserRating/${userId}`)
		return response.data
	} catch (error) {
		throw new Error("Faild to check")
	}
}

export async function addNewRating(userId, hotelId, star, comment, rateDay){
	const formData = new FormData()
	formData.append("star", star)
	formData.append("comment", comment)
	formData.append("rateDay", rateDay)

	const response = await api.post(`ratings/add/hotel/${hotelId}/user/${userId}/addRating`, formData)
	if (response.status === 201) {
		return true
	} else {
		return false
	}
}

export async function getAllRatingByHotelId(hotelId){
	try{
		const response = await api.get(`/ratings/hotel/${hotelId}`)
		return response.data
	} catch (error) {
		throw new Error("Faild to fetch Rating")
	}
}

export async function getDataForDashBoardMonthIncreased() {
	try {
		const response = await api.get(`/dashboard/dashBoardMonthIncreased`)
		return response.data
	} catch (error) {
		throw new Error("Error fetching dashboard data")
	}
}

export async function getDataForPieChart(){
	try{
		const response = await api.get("/dashboard/PieChart")
		return response.data
	} catch(error) {
		throw new Error("Error fetching data")
	}
}

export async function getDataForBarChart(){
	try{
		const response = await api.get("/dashboard/BarChart")
		return response.data
	} catch(error) {
		throw new Error("Error fetching data")
	}
}

export async function getDiscountNotExpired(){
	try{
		const response = await api.get("/discounts/getAllDiscount")
		return response.data
	} catch(error) {
		throw new Error("Error fetching data")
	}
}