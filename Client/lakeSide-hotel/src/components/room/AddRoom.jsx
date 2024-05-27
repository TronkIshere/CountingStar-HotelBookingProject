import React, { useState } from 'react'
import { addRoom } from '../utils/ApiFunctions'
import RoomTypeSelector from '../common/RoomTypeSelector'
import { Link } from "react-router-dom";


const AddRoom = () => {
    const[newRoom, setNewRoom] = useState({
        photo : null,
        roomType : "",
        roomPrice : "",
        roomDescription : ""
    })

    const[successMessage, setSuccessMessage] = useState("")
    const[imagePreview, setImagePreview] = useState("")
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

    const handleSubmit = async (e) => {
		e.preventDefault()
		try {
			const success = await addRoom(newRoom.photo, newRoom.roomType, newRoom.roomPrice, newRoom.roomDescription)
			if (success !== undefined) {
				setSuccessMessage("A new room was  added successfully !")
				setNewRoom({ photo: null, roomType: "", roomPrice: "", roomDescription: "" })
				setImagePreview("")
				setErrorMessage("")
			} else {
				setErrorMessage("Error adding new room")
			}
		} catch (error) {
			setErrorMessage(error.message)
		}
		setTimeout(() => {
			setSuccessMessage("")
			setErrorMessage("")
		}, 3000)
	}

  return (
    <>
    <section className='container mt-5 mb-5'>
        <h2 className='text-center mt-5 mb-2'>Add a New Room</h2>
        <div className='row justify-content-center'>
            <div className='col-md-8 col-lg-6'>
                {successMessage && (
                    <div className='alert alert-success fade show'>{successMessage}</div>
                )}

                {errorMessage && (
                    <div className='alert alert-danger fade show'>{errorMessage}</div>
                )}

                <form onSubmit={handleSubmit}>
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
                        <label htmlFor='roomDescription' className='form-label'>
                            Room Description
                        </label>
                        <input
                        required
                        className="form-control"
                        id="roomDescription"
                        name="roomDescription"
                        value={newRoom.roomDescription}
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

                    <div className='d-grid gap-2 d-md-flex mt-2'>
                        <Link to={"/existing-rooms"} className="btn btn-outline-info">
							Back
						</Link>       
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