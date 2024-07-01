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
import HotelRegistration from "./components/registerHotelOwner/HotelRegistration";

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
        <Route path="/hotels/:id" element={<List/>}/>
        <Route path="/hotelpage" element={<Hotel/>}/>
        <Route path="/userProfile" element={<UserProfile/>}/>
        <Route path="/hotelRegistration" element={<HotelRegistration/>}/>
      </Routes>
      <Footer/>
    </BrowserRouter>
  );
}

export default App;
