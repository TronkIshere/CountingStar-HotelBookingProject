import React from 'react'
import './featured.css'
import dalat from '../../assets/featured-img/dalat.jpg';
import nhatrang from '../../assets/featured-img/nhatrang.jpg';
import vungtau from '../../assets/featured-img/vungtau.jpeg';

const Featured = () => {
  return (
    <div className='featured'>
        <div className="featuredItem">
            <img src={dalat} alt="" className='featuredImg'/>
            <div className='featuredTitles'>
                <h1>Đà lạt</h1>
                <h2>123 properties</h2>
            </div>
        </div>
        <div className="featuredItem">
            <img src={nhatrang} alt="" className='featuredImg'/>
            <div className='featuredTitles'>
                <h1>Nha Trang</h1>
                <h2>123 properties</h2>
            </div>
        </div>
        <div className="featuredItem">
            <img src={vungtau} alt="" className='featuredImg'/>
            <div className='featuredTitles'>
                <h1>Vũng Tàu</h1>
                <h2>123 properties</h2>
            </div>
        </div>
    </div>
  )
}

export default Featured