import React from "react";
import './featuredProperties.css'
import khachsan1 from "../../assets/featuredProperties-img/khachsan1.jpg";
import khachsan2 from "../../assets/featuredProperties-img/khachsan2.jpg";
import khachsan3 from "../../assets/featuredProperties-img/khachsan3.jpg";
import khachsan4 from "../../assets/featuredProperties-img/khachsan4.jpg";
import khachsan5 from "../../assets/featuredProperties-img/khachsan5.jpg";

const FeaturedProperties = () => {
  return (
    <div className="fp">
      <div className="fpItem">
        <img src={khachsan1} alt="" className="fpImg" />
        <span className="fpName">Aparthotel Stare Miasto</span>
        <span className="fpName">Madrid</span>
        <span className="fpName">Sstarting from $120</span>
        <div className="fpRating">
          <button>8.9</button>
          <span>Excellent</span>
        </div>
      </div>

      <div className="fpItem">
        <img src={khachsan2} alt="" className="fpImg" />
        <span className="fpName">Aparthotel Stare Miasto</span>
        <span className="fpName">Madrid</span>
        <span className="fpName">Sstarting from $120</span>
        <div className="fpRating">
          <button>8.9</button>
          <span>Excellent</span>
        </div>
      </div>

      <div className="fpItem">
        <img src={khachsan3} alt="" className="fpImg" />
        <span className="fpName">Aparthotel Stare Miasto</span>
        <span className="fpName">Madrid</span>
        <span className="fpName">Sstarting from $120</span>
        <div className="fpRating">
          <button>8.9</button>
          <span>Excellent</span>
        </div>
      </div>

      <div className="fpItem">
        <img src={khachsan4} alt="" className="fpImg" />
        <span className="fpName">Aparthotel Stare Miasto</span>
        <span className="fpName">Madrid</span>
        <span className="fpName">Sstarting from $120</span>
        <div className="fpRating">
          <button>8.9</button>
          <span>Excellent</span>
        </div>
      </div>

      <div className="fpItem">
        <img src={khachsan5} alt="" className="fpImg" />
        <span className="fpName">Aparthotel Stare Miasto</span>
        <span className="fpName">Madrid</span>
        <span className="fpName">Sstarting from $120</span>
        <div className="fpRating">
          <button>8.9</button>
          <span>Excellent</span>
        </div>
      </div>
    </div>
  );
};

export default FeaturedProperties;
