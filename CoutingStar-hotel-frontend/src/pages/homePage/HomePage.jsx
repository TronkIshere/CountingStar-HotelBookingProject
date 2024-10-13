import React, { useContext, useState } from "react";
import "./homePage.css";
import Header from "../../components/header/Header";
import Featured from "../../components/homePageComponent/featured/Featured";
import PropertyList from "../../components/homePageComponent/propertyList/PropertyList";
import FeaturedProperties from "../../components/homePageComponent/featuredProperties/FeaturedProperties";
import Register from "../../components/register/Register";
import Discount from "../../components/discount/Discount";

const HomePage = () => {
  const userId = localStorage.getItem("userId");

  return (
    <div className="homePage">
      <Header />
      <div className="homeContainer">
        <Featured />
        <PropertyList />
        <Discount/>
        <FeaturedProperties />
        {userId ? <div></div> : <Register />}
      </div>
    </div>
  );
};

export default HomePage;
