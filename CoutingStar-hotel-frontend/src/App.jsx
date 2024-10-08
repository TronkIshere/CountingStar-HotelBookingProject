import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import Home from "./pages/home/Home";
import List from "./pages/list/List";
import Hotel from "./pages/hotel/Hotel";
import Navbar from "./components/navbar/Navbar";
import { useState } from "react";
import Login from "./components/login/Login";
import UserProfile from "./pages/userProfile/UserProfile";
import Footer from "./components/footer/Footer";
import HotelRegistration from "./pages/hotelRegistration/HotelRegistration";
import HotelRoomManagement from "./pages/hotelRoomManagement/HotelRoomManagement";
import HotelInformationManagement from "./pages/hotelInformationManagement/HotelInformationManagement";
import HotelBookingManagement from "./pages/hotelBookingManagement/HotelBookingManagement";
import { AuthProvider } from "./components/utils/AuthProvider";
import Admin from "./pages/admin/Admin";
import HotelOwner from "./pages/hotelOwner/HotelOwner";
import Discount from "./pages/Discount/Discount";
import HotelDashBoard from "./pages/hotelDashBroad/HotelDashBroard";

function App() {
  const [showLogin, setShowLogin] = useState(false);

  const handleLoginClick = () => {
    setShowLogin(true);
  };

  const handleCloseModal = () => {
    setShowLogin(false);
  };
  return (
    <AuthProvider>
      <BrowserRouter>
        <Navbar onLoginClick={handleLoginClick} />
        {showLogin && <Login onClose={handleCloseModal} />}
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/hotels" element={<List />} />
          <Route path="/discount" element={<Discount />} />
          <Route path="/hotels/hotel/:hotelId" element={<Hotel />} />
          <Route path="/user/:userId" element={<UserProfile />} />
          <Route path="/hotelRegistration" element={<HotelRegistration />} />
          <Route
            path="/hotel/:hotelId/HotelRoomManagement"
            element={<HotelRoomManagement />}
          />
          <Route
            path="/hotel/:hotelId/hotelBookingManagement"
            element={<HotelBookingManagement />}
          />
          <Route path="/hotel/:hotelId/hotelInformationManagement" element={<HotelInformationManagement />} />
          <Route path="/user/admin" element={<Admin />} />
          <Route path="/hotel/hotelOwner" element={<HotelDashBoard />} />
        </Routes>
        <Footer />
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
