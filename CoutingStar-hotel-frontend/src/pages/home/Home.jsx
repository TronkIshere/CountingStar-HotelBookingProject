import React from "react";
import "./home.css";
import Navbar from "../../components/navbar/Navbar";
import Header from "../header/Header";
import Featured from "../../components/featured/Featured";

const Home = () => {
  return (
    <div>
      <Navbar />
      <Header />
      <div className="homeContainer">
        <Featured />
        <h1 className="homeTitle">Những loại phòng hiện có</h1>
      </div>
    </div>
  );
};

export default Home;
