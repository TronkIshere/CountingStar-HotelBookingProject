import React, { useState } from "react"
import moment from "moment"
import { cancelBooking, getBookingByConfirmationCode } from "../utils/ApiFunctions"

const FindBooking = () => {
	const [confirmationCode, setConfirmationCode] = useState("")
	const [error, setError] = useState(null)
	const [successMessage, setSuccessMessage] = useState("")
	const [isLoading, setIsLoading] = useState(false)
	const [bookingInfo, setBookingInfo] = useState({
		bookingId: "",
		bookingConfirmationCode: "",
		room: { id: "", roomType: "" },
		roomNumber: "",
		checkInDate: "",
		checkOutDate: "",
		guestName: "",
		guestEmail: "",
		numOfAdults: "",
		numOfChildren: "",
		totalNumOfGuests: ""
	})

	const emptyBookingInfo = {
		bookingId: "",
		bookingConfirmationCode: "",
		room: { id: "", roomType: "" },
		roomNumber: "",
		checkInDate: "",
		checkOutDate: "",
		guestName: "",
		guestEmail: "",
		numOfAdults: "",
		numOfChildren: "",
		totalNumOfGuests: ""
	}
	const [isDeleted, setIsDeleted] = useState(false)

	const handleInputChange = (event) => {
		setConfirmationCode(event.target.value)
	}

	const handleFormSubmit = async (event) => {
		event.preventDefault()
		setIsLoading(true)
		try {
			const data = await getBookingByConfirmationCode(confirmationCode)
			setBookingInfo(data)
			setError(null)
		} catch (error) {
			setBookingInfo(emptyBookingInfo)
			if (error.response && error.response.status === 404) {
				setError(error.response.data.message)
			} else {
				setError(error.message)
			}
		}

		setTimeout(() => setIsLoading(false), 2000)
	}

	const handleBookingCancellation = async (bookingId) => {
		try {
			await cancelBooking(bookingInfo.bookingId)
			setIsDeleted(true)
			setSuccessMessage("Booking has been cancelled successfully!")
			setBookingInfo(emptyBookingInfo)
			setConfirmationCode("")
			setError(null)
		} catch (error) {
			setError(error.message)
		}
		setTimeout(() => {
			setSuccessMessage("")
			setIsDeleted(false)
		}, 2000)
	}

	return (
		<>
			<div className="container mt-5 d-flex flex-column justify-content-center align-items-center make-footer-down">
				<h2 className="text-center mb-4">Find My Booking</h2>
				<form onSubmit={handleFormSubmit} className="col-md-6">
					<div className="input-group mb-3">
						<input
							className="form-control"
							type="text"
							id="confirmationCode"
							name="confirmationCode"
							value={confirmationCode}
							onChange={handleInputChange}
							placeholder="Enter the booking confirmation code"
						/>

						<button type="submit" className="btn btn-hotel input-group-text">
							Find booking
						</button>
					</div>
				</form>

				{isLoading ? (
                <div className="loading-message">Finding your booking...</div>
            ) : error ? (
                <div className="text-danger error-message">Error: {error}</div>
            ) : bookingInfo.bookingConfirmationCode ? (
                <div className="col-md-6 mt-4 mb-5 booking-info-container">
                    <h3 className="booking-info-title">Booking Information</h3>
                    <p className="text-success confirmation-code">
                        Confirmation Code: {bookingInfo.bookingConfirmationCode}
                    </p>
                    <p className="booking-detail">Room Number: {bookingInfo.room.id}</p>
                    <p className="booking-detail">Room Type: {bookingInfo.room.roomType}</p>
                    <p className="booking-detail">
                        Check-in Date: {moment(bookingInfo.checkInDate).subtract(1, "month").format("MMM Do, YYYY")}
                    </p>
                    <p className="booking-detail">
                        Check-out Date: {moment(bookingInfo.checkInDate).subtract(1, "month").format("MMM Do, YYYY")}
                    </p>
                    <p className="booking-detail">Full Name: {bookingInfo.guestName}</p>
                    <p className="booking-detail">Email Address: {bookingInfo.guestEmail}</p>
                    <p className="booking-detail">Adults: {bookingInfo.numOfAdults}</p>
                    <p className="booking-detail">Children: {bookingInfo.numOfChildren}</p>
                    <p className="booking-detail">Total Guest: {bookingInfo.totalNumOfGuests}</p>

                    {!isDeleted && (
                        <button
                            onClick={() => handleBookingCancellation(bookingInfo.id)}
                            className="btn btn-danger cancel-button"
                        >
                            Cancel Booking
                        </button>
                    )}
                </div>
            ) : (
                <div className="loading-message">Finding booking...</div>
            )}

				{isDeleted && <div className="alert alert-success mt-3 fade show">{successMessage}</div>}
			</div>
		</>
	)
}

export default FindBooking