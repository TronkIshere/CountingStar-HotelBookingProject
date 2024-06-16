import "../resource/css/Header.css"

function Header(){
    return (
        <>
            <div className="header container">
                <div className="nav row">
                    <div className="logo">
                        <img src="./assets/img/logo/logo.ico" alt="#"/>
                    </div>
                    <div className="menu">
                        <ul>
                            <li className="menu__item">Home</li>
                            <li className="menu__item">Roome</li>
                            <li className="menu__item">Services</li>
                            <li className="menu__item">Blog</li>
                            <li className="menu__item">Contact Us</li>
                        </ul>
                        <div className="menu__btn">Sign up</div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default Header;