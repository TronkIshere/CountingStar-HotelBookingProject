import background4 from "../resource/img/background/background4.png"

function SectionFour(){
    return (
        <>
            <div className="section-four">
                <div className="container">
                    <div className="inner-title text-lg p-none">Just follow our Steps and Get Everything</div>
                    <div className="row">
                        <div className="col-xl-8">
                            <div className="inner-desc text">There are many variations of passages of Lorem Ispum available, but in from, by injected houmour,</div>
                            <ul className="advantage-list">
                                <li className="advantage-list__item">
                                    <div className="advantage-list__index"><span>01</span></div>
                                    <div className="advantage-content">
                                        <div className="advantage-content__title text-sm p-none">Find a Hotel Your Want to Visit</div>
                                        <div className="advantage-content__desc text p-none">Visit our Website and Browser your hotel, you can asved and ready to book</div>
                                    </div>
                                </li>
                                <li className="advantage-list__item">
                                    <div className="advantage-list__index"><span>02</span></div>
                                    <div className="advantage-content">
                                        <div className="advantage-content__title text-sm p-none">Find a Hotel Your Want to Visit</div>
                                        <div className="advantage-content__desc text p-none">Visit our Website and Browser your hotel, you can asved and ready to book</div>
                                    </div>
                                </li>
                                <li className="advantage-list__item">
                                    <div className="advantage-list__index"><span>03</span></div>
                                    <div className="advantage-content">
                                        <div className="advantage-content__title text-sm p-none">Find a Hotel Your Want to Visit</div>
                                        <div className="advantage-content__desc text p-none">Visit our Website and Browser your hotel, you can asved and ready to book</div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <div className="col-xl-4">
                            <div className="inner-img">
                                <img src={background4} alt="#"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default SectionFour;