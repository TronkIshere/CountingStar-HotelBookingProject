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
import HotelInformation from "./pages/hotelInformation/HotelInformation";
import HotelRoomManagement from "./pages/hotelRoomManagement/HotelRoomManagement";
import HotelBookingManagement from "./pages/hotelBookingManagement/HotelBookingManagement";

function App() {
  const [showLogin, setShowLogin] = useState(false);

  const handleLoginClick = () => {
    setShowLogin(true);
  };

  const handleCloseModal = () => {
    setShowLogin(false);
  };
  return (
    <BrowserRouter>
      <Navbar onLoginClick={handleLoginClick}/>
      {showLogin && <Login onClose={handleCloseModal} />}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/hotels" element={<List />} />
        <Route path="/hotels/hotel/:hotelId" element={<Hotel/>}/>
        <Route path="/hotelpage" element={<Hotel/>}/>
        <Route path="/userProfile" element={<UserProfile/>}/>
        <Route path="/hotelRegistration" element={<HotelRegistration/>}/>
        <Route path="/HotelInfomation" element={<HotelInformation/>}/>
        <Route path="/HotelRoomManagement" element={<HotelRoomManagement/>}/>
        <Route path="/HotelBookingManagement" element={<HotelBookingManagement/>}/>
      </Routes>
      <Footer/>
    </BrowserRouter>
  );
}

export default App;
