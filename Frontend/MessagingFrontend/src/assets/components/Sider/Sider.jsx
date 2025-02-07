import { Outlet, useNavigate } from "react-router"
import "./Sider.css"
import { LogoutOutlined,BellFilled,PlusCircleFilled } from "@ant-design/icons"
import AddFriend from "../AddFriend/AddFriend"
import { useEffect, useState } from "react"
import { TailSpin } from "react-loader-spinner"

function Sider(props) {
  const [visible,setVisible] = useState(false)
  const[friends,setFriends] = useState(null)
  let navigate = useNavigate()

  function setChats(chatID,friends){
      props.setFriendsChat(friends)
      return navigate(`/chats`)
  }
  
  async function getAllFriends(){
        let res = await fetch("http://localhost:8080/getFriends",{
          method:"POST",
          body:JSON.stringify({
            "id":props.userDetails.id
          }),
          headers:{"Authorization":`Bearer ${props.userDetails.token}`,"Content-type":"application/json"}
        }).then(async(res)=>await res.json())

        setFriends(res.friendsList)
  }

  useEffect(()=>{
      getAllFriends()
  },[])

  return (
    <div className="sider_container" style={{backgroundColor:"#ECECEC"}}>

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
                <div className={`notification_sider ${props.notification.class}`}>
                      {props.notification.message}
                </div>:null
            }


      <AddFriend setNotification={props.setNotification} notification={props.notification} 
      loading={props.loading} setLoading={props.setLoading} visible={visible} setVisible={setVisible} userDetails={props.userDetails}/>

        <div className="sider">

          <div className="top" >

            <div className="profile" style={{display:"flex"}}>
                  <img src={`${props.userDetails.profile_link}`} style={{width:"3rem",height:"3rem",borderRadius:"1.1rem",marginRight:"2rem"}}/>
                  <div className="username" style={{display:"flex",justifyContent:"center",alignItems:"center",color:"white",fontSize:"1.2rem"}}>{`${props.userDetails.username}`}</div>
            </div>

            <div className="logout" style={{display:"flex",justifyContent:"center", alignItems:"center"}}>
                <LogoutOutlined style={{cursor:"pointer",fontSize:"2rem",color:"white",fontWeight:"bold"}}/>
            </div>

          </div>

          <div className="contacts" style={{padding:"1rem"}}>
            {
             friends&&friends.map((value,index)=>{
              if(value.to.userID != props.userDetails.id){
                return(
                  <>
                      
                      <div className="contact" onClick={()=>{setChats(value.message_id,value.to)}}  style={{cursor:"pointer", display:"flex",backgroundColor:"white",padding:"1rem",borderRadius:"1.1rem"}}>
                            <img src={value.to.profileLink} style={{width:"3rem",borderRadius:"0.7rem",marginRight:"20%"}}/>         
                            <div className="name" style={{display:"flex",justifyContent:"center",alignItems:"center"}}>{value.to.username}</div>
                          </div>
                      
                  </>
                )
              }else{
                return(
                  <>
                
                  <div className="contact" onClick={()=>{setChats(value.message_id,value.from)}} style={{cursor:"pointer",display:"flex",backgroundColor:"white",padding:"1rem",borderRadius:"1.1rem"}}>
                        <img src={value.from.profileLink} style={{width:"3rem",borderRadius:"0.7rem",marginRight:"20%"}}/>         
                        <div className="name" style={{display:"flex",justifyContent:"center",alignItems:"center"}}>{value.from.username}</div>
                  </div>
                </>
                )
              }
                
            }) 
            }

          </div>

          <div className="bottom">
            <div className="addFriend" onClick={()=>(setVisible(true))}>
                <PlusCircleFilled style={{cursor:"pointer",fontSize:"1.5rem",color:"white",fontWeight:"bold"}}/>
            </div>
            
            <div className="checkFriendRequest" onClick={()=>(navigate("/checkRequests"))} >
              <BellFilled style={{fontSize:"1.5rem",color:"white",fontWeight:"bold",cursor:"pointer"}} />
            </div>
          </div>

        </div>

        <div className="content">
              <Outlet/>
        </div>
        
        
    </div>
  )
}

export default Sider
