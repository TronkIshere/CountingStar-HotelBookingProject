import React from 'react'
import './login.css'

const Login = ({ onClose }) => {
    return (
      <div className="modal_overlay" onClick={onClose}>
        <div className="signInForm" onClick={e => e.stopPropagation()}>
          <h1>Đăng nhập</h1>
          <input type="text" placeholder="Nhập Email" />
          <input type="password" placeholder="Nhập mật khẩu" />
          <button>Đăng nhập</button>
        </div>
      </div>
    );
  };

export default Login