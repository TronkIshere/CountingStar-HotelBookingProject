import background1 from "../resource/img/background/backrground1.png"
import { useState } from "react";

const getParent = (e, selector) => {
    const selectorE = document.querySelector(selector);
    while(e.getParent.contains(selectorE)){
        e = e.parentNode;
    }
    return e;
}

const init = {
    location: 'TP.Hồ Chí Minh',
    checkIn: undefined,
    checkOut: undefined,
    person: {
        adult: 2,
        baby: 0,
    },
}


function SectionOne(){

    const [native, setNative] = useState(init);
    const onNativeChange = e => {
        console.log("onNativeChange: ", e.target.value);
        setNative({...native, [e.target.name] : `${e.target.value}`});
    };
    const clickUp = e => {
        const name = e.target.getAttribute('name');
        setNative({...native, person:{
            ...native.person,
            [name] : (native.person[name] + 1),
        }})
        console.log(native.person[name])
    }
    const clickDown = e => {
        const name = e.target.getAttribute('name');
        setNative({...native, person:{
            ...native.person,
            [name] : (native.person[name]  && native.person[name] - 1),
        }})
    }

    const clickPerson = (e) => {
        e.preventDefault();
        e.stopPropagation();
        const details = document.querySelector('.details');
        if(details.classList.contains('open')){
            details.classList.remove('open');
            details.classList.add('closed');
        } else {
            details.classList.add('open');
            details.classList.remove('closed');
        }
    }

    return (
        <>
            <div className="section-one">
                <div className="inner-wrap">
                    <div className="container">
                        <div className="row inner-box">
                            <div className="inner-wrap col-xl-6">
                                <div className="inner-title text-lg">Make yourself at hom in <span>Our</span> hotel</div>
                                <div className="inner-desc text-sm">Simple - unique - friendly</div>
                                <div className="inner-btn">
                                    <div className="btn-search btn">Find a hotel</div>
                                    <div className="btn-watch">
                                        <i className="fa-solid fa-play"></i>
                                        <span>Watch video</span>
                                    </div>
                                </div>
                            </div>
                            <div className="inner-wrap col-xl-6">
                                <img  className="inner-img" src={background1} alt="#"/>
                            </div>
                        </div>
                    </div>  
                </div>            
                <div className="inner-search">
                    <div className="inner-search__item">
                        <label htmlFor="location">
                            <i className="fa-solid fa-location-dot"></i>
                            <span>Location</span>
                        </label>
                        <select id="location" name="location" value={native.location} onChange={onNativeChange}>
                            <option value="HCM">TP.Hồ Chí Minh</option>
                            <option value="DaNang">Đà Nẵng</option>
                            <option value="VungTau">Vũng Tàu</option>
                            <option value="HaNoi">Hà Nội</option>
                            <option value="NhaTrang">Nha Trang</option>
                        </select>
                    </div>
                    <div className="inner-search__item">
                        <label htmlFor="checkIn">
                            <i className="fa-regular fa-calendar"></i>
                            <span>Check-in</span>
                        </label>
                        <input type="date" id="checkIn" name="checkIn" value={native.checkIn} onChange={onNativeChange}/>
                    </div>
                    <div className="inner-search__item">
                        <label htmlFor="checkOut">
                            <i className="fa-regular fa-calendar"></i>
                            <span>Check-out</span>
                        </label>
                        <input type="date" name="checkOut" id="checkOut" value={native.checkOut} onChange={onNativeChange}/>
                    </div>
                    <div className="inner-search__item">
                        <div className="wrap" onClick={clickPerson}>
                            <div className="person">
                                <i className="fa-solid fa-user"></i>
                                <span>Person</span>
                            </div>
                            <div className="person__details">{native.person.adult} nguoi lon * {native.person.baby} tre em</div>
                        </div>
                        <div className="details closed">
                            <div className="row">
                                <div className="adult">
                                    Nguoi lon
                                </div>
                                <div className="adult-quality row">
                                    <div name="adult" className="adult-quality__down" onClick={clickDown}><span name="adult">-</span></div>
                                    <div className="adult-quality__value"><span>{native.person.adult}</span></div>
                                    <div name="adult" className="adult-quality__up" onClick={clickUp}><span name="adult">+</span></div>
                                </div>
                            </div>
                            <div className="row">
                                <div className="baby">
                                    Trem em
                                </div>
                                <div className="baby-quality row">
                                    <div name="baby" className="baby-quality__up" onClick={clickDown}><span name="baby">-</span></div>
                                    <div className="baby-quality__value"><span>{native.person.baby}</span></div>
                                    <div name="baby" className="baby-quality__down" onClick={clickUp}><span name="baby">+</span></div>
                                </div>
                            </div>
                            <div onClick={clickPerson} className="row">
                                <div className="btn details__done">
                                    Xong
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="inner-search__item">
                        <span>Search...</span>
                    </div>
                </div>
            </div>
        </>
    )
}

export default SectionOne;