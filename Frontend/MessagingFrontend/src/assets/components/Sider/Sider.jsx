import { Outlet } from "react-router"
import "./Sider.css"
import { LogoutOutlined,BellFilled,PlusCircleFilled } from "@ant-design/icons"
import AddFriend from "../AddFriend/AddFriend"
import { useState } from "react"
import { TailSpin } from "react-loader-spinner"

function Sider(props) {
  const [visible,setVisible] = useState(false)

  return (
    <div className="sider_container">

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

            <div className="profile">
                  <img src={`${props.userDetails.profile_link}`} style={{width:"3rem",height:"3rem",borderRadius:"1.1rem"}}/>
            </div>

            <div className="logout" style={{display:"flex",justifyContent:"center", alignItems:"center"}}>
                <LogoutOutlined style={{cursor:"pointer",fontSize:"2rem",color:"white",fontWeight:"bold"}}/>
            </div>

          </div>

          <div className="contacts">

          </div>

          <div className="bottom">
            <div className="addFriend" onClick={()=>(setVisible(true))}>
                <PlusCircleFilled style={{cursor:"pointer",fontSize:"1.5rem",color:"white",fontWeight:"bold"}}/>
            </div>
            
            <div  >
              <BellFilled style={{fontSize:"1.5rem",color:"white",fontWeight:"bold"}} />
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
