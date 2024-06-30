import React from "react";
import "./home.css";
import Header from "../../components/header/Header";
import Featured from "../../components/featured/Featured";
import PropertyList from "../../components/propertyList/PropertyList";
import FeaturedProperties from "../../components/featuredProperties/FeaturedProperties";
import Register from "../../components/register/Register";

const Home = () => {
  return (
    <div>
      <Header />
      <div className="homeContainer">
        <Featured />
        <h1 className="homeTitle">Những loại phòng hiện có</h1>
        <PropertyList />
        <h1 className="homeTitle">Những khách sạn đang được yêu thích</h1>
        <FeaturedProperties />
        <Register />
      </div>
    </div>
  );
};

export default Home;
