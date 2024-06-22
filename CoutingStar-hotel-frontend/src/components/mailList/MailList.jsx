import React from 'react'
import './mailList.css'

const MailList = () => {
  return (
    <div className='mail'>
        <h1 className="mailTitle">Save time, save money!</h1>
        <span className="mailDesc">Đăng ký và hưởng thêm ưu đãi</span>
        <div className="mailInputContainer">
            <input type='text' placeholder='Nhập Email' />
            <button>Đăng ký</button>
        </div>
    </div>
  )
}

export default MailList