import background3 from "../resource/img/background/background3.png"

function SectionThree(){
    return (
        <>
            <div className="section-three">
                <div className="container">
                    <div className="inner-title text-md">Our Rooms</div>
                    <div className="inner-desc text">Enjoy our array of amenites for a relaxing stay</div>
                    <div className="row"> 
                        <div className="col-xl-4">
                            <div className="room">
                                <img className="room__img" src={background3} alt="#"/>
                                <div className="room-content">
                                    <span>
                                        <div className="room-content__name text-sm">Standard Room</div>
                                        <div className="room-content__price">
                                            <span>$ 96</span>
                                            <span>/</span>
                                            <span className="text p-none">Night</span></div>
                                    </span>
                                    <div className="room-content__intro text">
                                        La mot khach san day du tien ghi va thoai mai rat phu hop cho nguoi kha gia
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="col-xl-4">
                            <div className="room">
                                <img className="room__img" src={background3} alt="#"/>
                                <div className="room-content">
                                    <span>
                                        <div className="room-content__name text-sm">Standard Room</div>
                                        <div className="room-content__price">
                                            <span>$ 96</span>
                                            <span>/</span>
                                            <span className="text p-none">Night</span></div>
                                    </span>
                                    <div className="room-content__intro text">
                                        La mot khach san day du tien ghi va thoai mai rat phu hop cho nguoi kha gia
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="col-xl-4">
                            <div className="room">
                                <img className="room__img" src={background3} alt="#"/>
                                <div className="room-content">
                                    <span>
                                        <div className="room-content__name text-sm">Standard Room</div>
                                        <div className="room-content__price">
                                            <span>$ 96</span>
                                            <span>/</span>
                                            <span className="text p-none">Night</span></div>
                                    </span>
                                    <div className="room-content__intro text">
                                        La mot khach san day du tien ghi va thoai mai rat phu hop cho nguoi kha gia
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default SectionThree;
