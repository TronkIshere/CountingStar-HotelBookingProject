import "../resource/css/Footer.css"

function Footer(){
    return (
        <>
            <div className="footer">
                <div className="container">
                    <div className="row">
                        <div className="footer__summary col-xl-4">
                            <div className="summary">
                                <div className="summary__title">HOUSEHOTEL</div>
                                <div className="summary__content">La mot khach san rat dang trai nghiem mang lai nhieu cam giac thu vi cho khach hang</div>
                                <ul className="summary-social">
                                    <li className="summary-social__item">
                                        <i className="fa-brands fa-facebook-f"></i>
                                    </li>
                                    <li className="summary-social__item">
                                        <i className="fa-brands fa-instagram"></i>
                                    </li>
                                    <li className="summary-social__item">
                                        <i className="fa-brands fa-twitter"></i>
                                    </li>
                                    <li className="summary-social__item">
                                        <i className="fa-brands fa-youtube"></i>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div className="footer__quick col-xl-2">
                            <div className="quick">
                                <div className="quick__head">Quick Links</div>
                                <ul>
                                    <li className="quick__link"><a href="#">Home</a></li>
                                    <li className="quick__link"><a href="#">Room</a></li>
                                    <li className="quick__link"><a href="#">About Us</a></li>
                                    <li className="quick__link"><a href="#">Gellery</a></li>
                                    <li className="quick__link"><a href="#">Contact Us</a></li>
                                </ul>
                            </div>
                        </div>
                        <div className="col-xl-2">
                            <div className="service">
                                <div className="service__head">Services</div>
                                <ul>
                                    <li className="service__link"><a href="#">Restaurent</a></li>
                                    <li className="service__link"><a href="#">Swimming poll</a></li>
                                    <li className="service__link"><a href="#">Transport</a></li>
                                    <li className="service__link"><a href="#">Privacy Policy</a></li>
                                    <li className="service__link"><a href="#">Term & Condition</a></li>
                                </ul>
                            </div>
                        </div>
                        <div className="col-xl-4">
                            <div className="subscribe">
                                <div className="subscribe__head">
                                    Subscribe Now !
                                </div>
                                <div className="subscribe__input">
                                    <input type="email" placeholder="Hotel@example.com" id="email" name="email" autoComplete="off" />
                                    <div className="subscribe__search">
                                        <i className="fa-solid fa-paper-plane"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="copyright">
                        Copyright@2021 ---- All Rights reserved
                    </div>
                </div>
            </div>
        </>
    )
}

export default Footer;