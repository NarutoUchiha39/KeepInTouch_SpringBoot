import React, { useEffect, useState } from 'react'
import "./Chats.css"
import { useParams } from 'react-router'
import TextArea from 'antd/es/input/TextArea'
import { PlusCircleFilled, SendOutlined } from '@ant-design/icons'
import { Button } from 'antd'

function Chats(props) {

  let[chats,setChats] = useState(null)

  async function fetchChats(){
    
    let res  = await fetch("http://localhost:8080/getMessages",{
      method:"POST",
      body:JSON.stringify({
          id:parseInt(props.userDetails.id),
          to:props.friendsChat.userID
      }),
      headers:{"Authorization":`Bearer ${props.userDetails.token}`,"Content-type":"application/json"}
    }).then(async(res)=>await res.json())

    let recieved_chats = []

    res.map((value,index)=>{
      recieved_chats.push({message:value.messages,link:value.link,
                          to:value.to.userID,from:value.from.userID
      })
    })

    setChats(recieved_chats)
  }
  useEffect(()=>{
      fetchChats()
  },[])

  let[text,setText] = useState(null)
  let [loading,setLoading] = useState(false)

  async function sendMessage_(form){
    setLoading(true)
    let res = await fetch("http://localhost:8080/sendMessage",{
      method:"POST",
      body:form,
      headers:{"Authorization":`Bearer ${props.userDetails.token}`}
    }).then(async(res)=>await res.json())
    
    setLoading(false)
    if(res.statusCode == 500){
      props.setNotification({
        message:"Something went wrong :(",
        class:"error"
      })
    }
    
    console.log(res)
  }

  function sendMessage(event){
    console.log("Ok")

      if(text==null){
        props.setNotification({
          message:"Please type a message or select a file",
          class:"error"
        })
      }
      
      event.preventDefault()
      let form =  new FormData()
      if(text.message != null){
        form.append("message",text.message)
      }
      form.append("from",props.userDetails.email)
      form.append("to",props.friendsChat.email)
      if(text.file != null){
        form.append("file",text.file)
      }
      
      sendMessage_(form)
      
  }

  return (
    <div className='chatSection'>

      <div className="top_chat">
            <div className="userdetails" style={{display:"flex",padding:"0.7rem"}}>
                
                <div className="profile_link">
                  <img style={{width:"40px",borderRadius:"0.5rem",marginRight:"1rem"}} src={props.friendsChat.profileLink} />
                </div>

                <div className="username" style={{color:"white",fontSize:"1.3rem",display:"flex",justifyContent:"center",alignItems:"center"}}>
                  {props.friendsChat.username}
                </div>  
            </div>
      </div>

      <div className="middle_chat" style={{padding:"2rem"}}>
                    {
                      chats&&chats.map((value,index)=>{
                        console.log(value.from)
                        if(value.from == parseInt(props.userDetails.id)){
                            return  <div className='messages' style={{display:"flex",justifyContent:"end",marginBottom:"2rem"}}>
                                  <div className="message" style={{padding:"1rem",backgroundColor:"white",borderRadius:"1.1rem"}}>
                                      {value.message}
                                  </div>

                                  
                              </div>
                        }else{
                          return <div className='messages' style={{display:"flex",justifyContent:"start",marginBottom:"2rem"}}>
                                    <div className="message" style={{padding:"1rem",backgroundColor:"lightsteelblue",borderRadius:"1.1rem"}}>
                                        {value.message}
                                    </div>
                                    
                              </div>
                        }
                        
                      })
                    }
      </div>

      <form onSubmit={sendMessage} className="bottom_chat" style={{display:"flex",justifyContent:"center",alignItems:"center"}}>
          <TextArea onChange={(e)=>{setText((prev)=>({...prev,message:e.target.value}))}} style={{width:"80%",marginRight:"1rem",resize:"none"}} rows={1}/>
          <input type='file' onChange={(e)=>{setText((prev)=>({...prev,file:e.target.files[0]}))}} id="file" hidden/>
            <label htmlFor='file' style={{cursor:"pointer"}} ><PlusCircleFilled style={{color:"blue",fontSize:"1.7rem",marginRight:"1rem"}}/></label>
          
          <Button htmlType='submit' loading={loading} type='submit' style={{background:"transparent"}}>
            <SendOutlined style={{color:"blue",fontSize:"2rem",cursor:"pointer"}}/>
          </Button>
      </form>
      
    </div>
  )
}

export default Chats
