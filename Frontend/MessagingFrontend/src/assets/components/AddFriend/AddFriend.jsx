import { Button, Modal } from 'antd'
import React, { useState } from 'react'

function AddFriend(props) {

    const[friendName,setFriendName] = useState(null)
    const [confirmLoading,setConfirmLoading] = useState(false)

    async function sendRequest(){
        let res = await fetch("http://localhost:8080/addFriend",{
          method:"POST",
          body:JSON.stringify({
            email:friendName
          }),
          headers:{"Content-Type":"application/json", 
            "Authorization":`Bearer ${props.userDetails.token}`}
          
        }).then(async(res)=>await res.json())

        console.log(res)
    }

    async function sendTest(){
      setConfirmLoading(true)
      let res = await fetch("http://localhost:8080/addFriend",{
        method:"POST",
        body:JSON.stringify({
          "to":friendName.email,
          "from":{"email":props.userDetails.email,"username":props.userDetails.username,"id":parseInt(props.userDetails.id)}
        }),
        headers:{"Content-Type":"application/json", 
          "Authorization":`Bearer ${props.userDetails.token}`}
      }).then(async(res)=>await res.json())

      setConfirmLoading(false)
      
      if(res.statusCode == 500){
        props.setNotification({class:"error",message:res.message})
      }else{
        props.setVisible(false)
        props.setNotification({class:"success",message:res.message})
      }
      
  }

    function AddFriend(){
         console.log(props.userDetails)
         if(friendName == null){
                alert("Enter friendname !")
                return;
         }

         sendTest()
    
    }

  function handleCancel(){
    props.setVisible(false)
  }


  return (
    <>
    <Modal
     title="Add Friend"
     centered={true}
     onOk={AddFriend}
     onCancel={handleCancel}
     visible={props.visible}
     confirmLoading={confirmLoading}
     okText={"Send Request"}
     
    >

        <div className="EnterFriendName">
            
                <div className="AddFriend_Modal" style={{display:"flex",flexDirection:"column"}}>
                    <label htmlFor='addFriend' id='addFriend' style={{marginBottom:"0.7rem",fontSize:"1rem"}}>Enter Friend Name</label>
                    <input onChange={(e)=>(setFriendName((prev)=>({...prev,email:e.target.value})))} name='addFriend' id='addFriend' type='text'/>
                </div>
            
        </div>

    </Modal>
    </>
  )
}

export default AddFriend
