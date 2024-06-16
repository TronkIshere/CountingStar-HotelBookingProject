import background2 from "../resource/img/background/backrground2.png"

function SectionTwo(){
    return (
        <>
            <div className="section-two container">
                <div className="row">
                    <div className="col-xl-6">
                        <div className="inner-img">
                            <img src={background2} alt="#"/>
                        </div>
                    </div>
                    <div className="col-xl-6">
                        <div className="inner-box">
                            <div className="inner-box__title">
                                About us
                            </div>
                            <div className="inner-title text-md">
                                The best holidays start <span>here.</span>
                            </div>
                            <div className="inner-desc text">There are many variations of passages of Lorem Ipsum avalibale, but in form, by injected humour, of Lorem Ipsum available, but in form. There</div>
                            <div className="inner-btn btn">Read more</div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default SectionTwo;
