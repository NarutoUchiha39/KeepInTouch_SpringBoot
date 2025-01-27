import React from 'react'
import "./navbar.css"
import { Outlet } from 'react-router'

function Navbar() {

  return (
    <div className="container">
        <div className='navbar'>
            <div className="left">
                <div className="websiteName" style={{fontSize:"20px"}}>Keep In Touch</div>
            </div>
            
            <div className="right_nav">
                <div className="login_nav" style={{fontSize:"20px"}}>Login</div>
                <div className="register_nav" style={{fontSize:"20px"}}>Register</div>
            </div>
        </div>
        <Outlet/>
    </div>
  )
}

export default Navbar
