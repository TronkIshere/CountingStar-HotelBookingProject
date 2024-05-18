import React, { useState, useEffect } from 'react';
import { cancelBooking, getAllBookings } from '../utils/ApiFunctions';
import BookingsTable from './BookingsTable';
import Header from '../common/Header';

const Bookings = () => {
    const [bookingInfo, setBookingInfo] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        setTimeout(() => {
            getAllBookings()
                .then((data) => {
                    console.log("Data received from API:", data);
                    setBookingInfo(data);
                    setIsLoading(false);
                })
                .catch((error) => {
                    setError(error.message);
                    setIsLoading(false);
                });
        }, 1000);
    }, []);

    const handleBookingCancellation = async (bookingId) => {
        try {
            await cancelBooking(bookingId);
            const data = await getAllBookings();
            console.log("handleBookingCancellation: ", data)
            setBookingInfo(data);
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <section style={{ backgroundColor: 'whitesmoke' }}>
            <Header title={'Existing Bookings'} />
            {error && <div className="text-danger">{error}</div>}
            {isLoading ? (
                <div>Loading existing bookings</div>
            ) : (
                <BookingsTable
                    bookingInfo={bookingInfo}
                    handleBookingCancellation={handleBookingCancellation}
                />
            )}
        </section>
    );
};

export default Bookings;
