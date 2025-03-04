import { useState } from 'react'
import { Route, Routes } from 'react-router'
import Login from './assets/components/Login/Login'
import Register from './assets/components/Register/Register'
import Main from './assets/components/Main/Main'
import Navbar from './assets/components/navbar/navbar'
import RouteProtection from './assets/components/RouteProtection/RouteProtection'
import RedirectPage from './assets/components/RedirectPage/RedirectPage'
import Sider from './assets/components/Sider/Sider'
import FriendRequests from './assets/components/FriendRequests/FriendRequests'
import Chats from './assets/components/Chats/Chats'

function App() {

  const [userDetails,setUserDetails] = useState({
    email: null,
    exp:null,
    iat:null ,
    id:null,
    username:null,
    profile_link:null,
    token:null
  })

  const [friendsChat,setFriendsChat] = useState(null)

  const[notification,setNotification] = useState({
    message:null,
    class:null
  })

  const[loading,setLoading] = useState(null)

  return (
    <>
      <Routes>

          <Route element={<Sider loading={loading} setLoading={setLoading} notification={notification} setNotification={setNotification} userDetails={userDetails} setUserDetails={setUserDetails} setFriendsChat={setFriendsChat}/>}>
              <Route path='/' element={<RouteProtection userDetails={userDetails}><Main/></RouteProtection>} />

              <Route path='/chats' element={<RouteProtection userDetails={userDetails}><Chats friendsChat={friendsChat} userDetails={userDetails} loading={loading} setLoading={setLoading} notification={notification} setNotification={setNotification} /></RouteProtection>} />
          </Route>

          <Route path='/checkRequests' element={<RouteProtection userDetails={userDetails}><FriendRequests userDetails={userDetails} loading={loading} setLoading={setLoading} notification={notification} setNotification={setNotification}/></RouteProtection>}/>


          <Route element={<Navbar/>}>
              <Route path='/login' element={<Login loading={loading} setLoading={setLoading} userDetails={userDetails} setUserDetails={setUserDetails} notification={notification} setNotification={setNotification} />} />

              <Route path='/register' element={<Register loading={loading} setLoading={setLoading} userDetails={userDetails} notification={notification} setNotification={setNotification} />} />

              <Route path='/token' element={<RedirectPage notification={notification} setNotification={setNotification} userDetails={userDetails} setUserDetails={setUserDetails}/>} />

          </Route>

      </Routes>
    </>
  )
}

export default App
