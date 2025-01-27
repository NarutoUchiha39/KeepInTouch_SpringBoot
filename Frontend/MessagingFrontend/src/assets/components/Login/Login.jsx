import { useState } from "react"
import "./Login.css"
import { jwtDecode } from "jwt-decode"
import { useNavigate } from "react-router"
import { TailSpin } from "react-loader-spinner"


function Login(props) {

  const [userLogin,setUserLogin] = useState({
    email:"",
    password:""
  })

  let navigate = useNavigate()

  async function sendLogin(){

    props.setLoading(true)

    let res = await fetch("http://localhost:8080/login",{
        method:"POST",
        headers: {
          "Content-Type": "application/json",
        },
        body:JSON.stringify(userLogin),
    }).then(async(res)=>await res.json())

    if(res.statusCode == 403){
      props.setNotification({message:res.message,class:"error"})
      return
    }

    let store_token = res.token
    let token = jwtDecode(store_token)
    props.setUserDetails((prev)=>({...prev,...token,token:store_token}))
    props.setLoading(false)
    navigate("/")
  }

  function handleSubmit(e){
    e.preventDefault();
    sendLogin()  

 }


  return (
    <div className="login">

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
      

      <div className="loginForm">
          <form onSubmit={handleSubmit}>
            <div className="email input">
                <label className="email_id" htmlFor="email">Enter email </label>
                <input required name="email" id="email" value={userLogin.email} onChange={(e)=>setUserLogin(prev=>({...prev,email:e.target.value}))}/>
            </div>

            <div className="password input">
              <label className="password_id" htmlFor="password">Enter password </label>
              <input required type="password" name="password" id="password" onChange={(e)=>setUserLogin(prev=>({...prev,password:e.target.value}))} value={setUserLogin.password} />
            </div>

            <button type="submit"> Submit </button>
            
          </form>  
      </div>
      
    </div>
  )
}

export default Login
