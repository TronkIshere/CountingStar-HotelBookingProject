import moment from 'moment'
import { useState } from 'react'
import { getAvailableRooms } from '../utils/ApiFunctions'
import {Container, Col, Row, Form, Button} from 'react-bootstrap'
import RoomTypeSelector from './RoomTypeSelector'
import RoomSearchResults from './RoomSearchResult'

const RoomSearch = () => {
    const[searchQuery, setSearchQuery]= useState({
        checkInDate :"",
        checkOutDate :"",
        roomType :""
    })
    const[errorMessage, setErrorMessage] =useState("")
    const[availableRooms, setAvailableRooms] = useState([])
    const[isLoading, setIsLoading] = useState(false)

    const handleSearch = (e) =>{
        e.preventDefault();
        const checkIn = moment(searchQuery.checkInDate)
        const checkOut = moment(searchQuery.checkOutDate)
        if(!checkIn.isValid() || !checkOut.isValid()){
            setErrorMessage("Please, enter valid date range")
            return
        }
        if(!checkOut.isSameOrAfter(checkIn)){
            setErrorMessage("Check-In date must come before check-out date")
            return
        }
        setIsLoading(true)
        getAvailableRooms(searchQuery.checkInDate, searchQuery.checkOutDate, searchQuery.roomType).then((Response) =>{
            setAvailableRooms(Response.data)
            setTimeout(() =>{
                setIsLoading(false)
            }, 2000)
        }).catch((error) =>{
            console.error(error)
        }).finally(() =>{
            setIsLoading(false)
        })
    }

    const handleInputChange = (e) =>{
        const { name, value } = e.target 
        setSearchQuery({ ...searchQuery, [name]: value })
        const checkIn = moment(searchQuery.checkInDate)
        const checkOut = moment(searchQuery.checkOutDate)
        if(checkIn.isValid() && checkOut.isValid()){
            setErrorMessage("")
        }
    }

    const handleClearSearch = () => {
		setSearchQuery({
			checkInDate: "",
			checkOutDate: "",
			roomType: ""
		})
		setAvailableRooms([])
	}

  return (
    <>
    <Container className='RoomSearch-form mt-5 mb-5 py-3 shadow'>
        <Form onSubmit={handleSearch}>
            <Row className='justify-content-center'>
                <Col xs={12} md={3}>
                    <Form.Group controlId='checkInDate'>
                        <Form.Label>Check-in date</Form.Label>
                        <Form.Control
                        type='date'
                        name='checkInDate'
                        value={searchQuery.checkInDate}
                        onChange={handleInputChange}
                        min={moment().format("YYYY-MM-DD")}>
                        </Form.Control>
                    </Form.Group>
                </Col>

                <Col xs={12} md={3}>
                    <Form.Group controlId='checkOutDate'>
                        <Form.Label>Check-out date</Form.Label>
                        <Form.Control
                        type='date'
                        name='checkOutDate'
                        value={searchQuery.checkOutDate}
                        onChange={handleInputChange}
                        min={moment().format("YYYY-MM-DD")}>
                        </Form.Control>
                    </Form.Group>
                </Col>

                <Col xs={12} md={3}>
                    <Form.Group>
                        <Form.Label>Room Type</Form.Label>
                        <div>
                            <RoomTypeSelector
                                handleRoomInputChange={handleInputChange}
                                newRoom={searchQuery}
                            />
                        </div>
                    </Form.Group>
                </Col>

                <Col xs={12} md={2} className='search-submit'>
                    <Button variant='secondary' type='submit'>
                        Search
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="search-icon size-6">
  <path strokeLinecap="round" strokeLinejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
</svg>


                    </Button>
                </Col>
            </Row>
        </Form>

        {isLoading ? (
            <p>finding available rooms .....</p>
        ): availableRooms ?(
            <RoomSearchResults
                results={availableRooms}
                onClearSearch={handleClearSearch}
            />
        ):(
            <p>No rooms available for the selected dates and room type</p>
        )}
        {errorMessage && <p className='text-danger'>{errorMessage}</p>}
    </Container>

    </>
  )
}

export default RoomSearch