import React, { useContext, useState } from "react";
import "./home.css";
import Header from "../../components/header/Header";
import Featured from "../../components/featured/Featured";
import PropertyList from "../../components/propertyList/PropertyList";
import FeaturedProperties from "../../components/featuredProperties/FeaturedProperties";
import Register from "../../components/register/Register";
import { AuthContext } from "../../components/utils/AuthProvider";

const Home = () => {
  const userId = localStorage.getItem("userId");

  return (
    <div>
      <Header />
      <div className="homeContainer">
        <Featured />
        <h1 className="homeTitle">Những lợi ích chúng tôi mang lại</h1>
        <PropertyList />
        <h1 className="homeTitle">Những khách sạn đang được yêu thích</h1>
        <FeaturedProperties />
        {userId ? <div></div> : <Register />}
      </div>
    </div>
  );
};

export default Home;
