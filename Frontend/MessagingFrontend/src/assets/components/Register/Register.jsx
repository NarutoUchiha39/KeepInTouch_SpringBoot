import React, { useState } from 'react'
import "./Register.css"
import doggo from "../../images/doggo.jpg"
import { TailSpin } from 'react-loader-spinner'
function Register(props) {

  const[registerUser,setRegisterUser] = useState({
    username:null,
    password:null,
    email:null,
    profile:null
  })

  

  async function registerUser_(){
    
    let formData = new FormData()
    formData.append("username",registerUser.username)
    formData.append("email",registerUser.email)
    formData.append("password",registerUser.password)
    formData.append("file",registerUser.profile)

    props.setLoading(true)

    let res = await fetch("http://localhost:8080/register",{
      method:"POST",
      body:formData
    }).then(async(res)=>await res.json())

    props.setLoading(false)
    
    if (res.statusCode == 200) {
          props.setNotification({message:res.message,class:"success"})
    }else{
      props.setNotification({message:res.message,class:"error"})
    }

  }

  function handleSubmit(e){
      e.preventDefault()
      registerUser_()

      // console.log(registerUser)
  }

  return (
    <div className='registerContainer'>

      {
        props.loading?
          <TailSpin
          visible={true}
          height="80"
          width="80"
          color="#4fa94d"
          ariaLabel="tail-spin-loading"
          radius="1"
          wrapperStyle={{position:"fixed",width:"100%",height:"100vh",display:"flex",justifyContent:"center",alignItems:"center",backgroundColor: "rgba(0, 0, 0, 0.5)"
          }}
          wrapperClass=""
        />:null
      }

      


      {props.notification.message?
          <div className={`notification ${props.notification.class}`}>
                {props.notification.message}
          </div>:null
      }

      <form className='registerForm' onSubmit={handleSubmit}>

        <div className="left">

              <div className="Email input">
                  <label style={{marginBottom:"0.7rem",fontSize:"large"}} htmlFor="email">Enter Email</label>
                  <input onChange={(e)=>(setRegisterUser(prev=>({...prev,email:e.target.value})))} type="text" name="email" id="email" />
              </div>

              <div className="username input">
                <label style={{marginBottom:"0.7rem",fontSize:"large"}} htmlFor="username">Enter username</label>
                <input onChange={(e)=>(setRegisterUser(prev=>({...prev,username:e.target.value})))} type="text" name='username' id='username'/>
              </div>

              <div className="password input">
                <label style={{marginBottom:"0.7rem",fontSize:"large"}} htmlFor="password">Enter Password</label>
                <input type="password" onChange={(e)=>(setRegisterUser(prev=>({...prev,password:e.target.value})))} name='password' id="password" />
              </div>
              <button type='submit'>Submit</button>
        </div>

        <div className="right" style={{display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"center"}}>
            <img src={doggo} style={{width:"15rem", height:"14rem", borderRadius:"2.1rem", marginBottom:"2.1rem"}}/>
            <input onChange={(e)=>(setRegisterUser(prev=>({...prev,profile:e.target.files[0]})))} style={{width:"70%"}} type="file" name="file" id="file" />
        </div>
        
        
        

      </form>
    </div>
  )
}

export default Register
