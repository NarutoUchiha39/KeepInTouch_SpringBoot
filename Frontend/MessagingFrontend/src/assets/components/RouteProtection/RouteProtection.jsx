import React from 'react'
import { Navigate } from 'react-router'

function RouteProtection(props) {
    console.log(props.userDetails)
  return (
    props.userDetails.username?props.children:<Navigate to={"/login"}/>
  )
}

export default RouteProtection
