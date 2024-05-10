import React, { useState } from 'react'
import { addRoom } from '../utils/ApiFunctions'
import RoomTypeSelector from '../common/RoomTypeSelector'


const AddRoom = () => {
    const[newRoom, setNewRoom] = useState({
        photo : null,
        roomType : "",
        roomPrice : ""
    })

    const[imagePreview, setImagePreview] = useState("")
    const[successMessage, setSuccessMessage] = useState("")
    const[errorMessage, setErrorMessage] = useState("")

    const handleRoomInputChange = (e) => {
        const name = e.target.name 
        let value = e.target.value 
        if(name === "roomPrice") {
            if (!isNaN(value)) {
                value = parseInt(value);
            } else {
                value = ""
            }
        }
        setNewRoom({...newRoom, [name]: value})
    }

    const handleInamgeChange = (e) =>{
        const selectedImage = e.target.files[0]
        setNewRoom({...newRoom, photo: selectedImage})
        setImagePreview(URL.createObjectURL(selectedImage))
    }

    const handleSumit = async(e) =>{
        e.preventDefault()
        try {
            const success = await addRoom(newRoom.photo, newRoom.roomType, newRoom.roomPrice)  
            if(success !== undefined) {
                setSuccessMessage("A new room was added to the database")
                setNewRoom({photo: null, roomType: "", roomPrice: ""})
                setImagePreview("")
                setErrorMessage("")
            } else {
                setErrorMessage("Error adding room")
            }
        } catch(error) {
            setErrorMessage(error.message)
        }
    }

  return (
    <>
    <section className='container mt-5 mb-5'>
        <div className='row justify-content-center'>
            <div className='col-md-8 col-lg'>
                <h2 className='mt-5 mb-2'>Add a New Room</h2>

                <form onSubmit={handleSumit}>
                    <div className='mb-3'>
                        <label htmlFor='roomType' className='form-label'>
                            Room Type
                        </label>
                        <div>
                            <RoomTypeSelector
                                handleRoomInputChange = {handleRoomInputChange}
                                newRoom={newRoom}
                            />
                        </div>
                    </div>

                    <div className='mb-3'>
                        <label htmlFor='roomPrice' className='form-label'>
                            Room Price
                        </label>
                        <input
                        required
                        type="number"
                        className="form-control"
                        id="roomPrice"
                        name="roomPrice"
                        value={newRoom.roomPrice}
                        onChange={handleRoomInputChange}/>
                    </div>

                    <div className='mb-3'>
                        <label htmlFor='roomPhoto' className='form-label'>
                            Room Photo
                        </label>
                        <input
                        id='photo'
                        name='photo'
                        type='file'
                        className='form-control'
                        onChange={handleInamgeChange}/>
                        {imagePreview && (
                            <img src={imagePreview}
                            alt='Preview Room Photo'
                            style={{maxWidth: "400px", maxHeight: "400px"}}
                            className='mb-3'/>
                        )}
                    </div>

                    <div className='d-grid d-md-flex mt-2'>
                        <button className='btn btn-outline-primary ml-5'>
                            Save Room
                        </button>
                    </div>
                </form>
            </div>

        </div>
    </section>    
    </>
  )
}

export default AddRoom