import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomePage from "./pages/homePage/HomePage";
import SearchHotelsPage from "./pages/searchHotelsPage/SearchHotelsPage";
import Navbar from "./components/navbar/Navbar";
import { useState } from "react";
import Login from "./components/login/Login";
import UserProfile from "./pages/userProfile/UserProfile";
import Footer from "./components/footer/Footer";
import { AuthProvider } from "./components/utils/AuthProvider";
import AdminPage from "./pages/adminPage/AdminPage";
import Discount from "./pages/Discount/Discount";
import HotelPage from "./pages/hotelPage/HotelPage";
import HotelRegistration from "./pages/hotelRegistration/HotelRegistration";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import "./App.css";

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
          <Route path="/" element={<HomePage />} />
          <Route path="/hotels" element={<SearchHotelsPage />} />
          <Route path="/discount" element={<Discount />} />
          <Route path="/hotels/hotel/:hotelId" element={<HotelPage />} />
          <Route path="/user/:userId" element={<UserProfile />} />
          <Route path="/hotelRegistration" element={<HotelRegistration />} />
          <Route path="/user/admin" element={<AdminPage />} />
        </Routes>

        <Footer />
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
