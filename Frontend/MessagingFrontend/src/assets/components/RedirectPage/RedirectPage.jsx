import { jwtDecode } from 'jwt-decode';
import React, { useEffect } from 'react'
import { useNavigate, useSearchParams } from 'react-router'

function RedirectPage(props) {

  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();

  async function verifyToken(token){

    const res = await fetch(`http://localhost:8080/register?token=${token}`)
    if(res.ok){
        const body = await res.json()
        const token = body.token
        const user = jwtDecode(token)
        props.setUserDetails({...user,token:token})
        navigate("/")
    }else{
        console.log(res.message)
        props.setNotification({
            message:"Invalid Token",
            class:"error"
        })

        navigate("/register")
    }
  }

  useEffect(()=>{
        let token = searchParams.get("token")
        if(token != null){
            verifyToken(token)
        }else{
            navigate("/login")
        }
  },[])

  return <div/>
}

export default RedirectPage
