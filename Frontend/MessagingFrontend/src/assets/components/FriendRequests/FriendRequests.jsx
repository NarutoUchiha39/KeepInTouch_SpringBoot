import React, { useEffect, useState } from 'react'
import "./FriendRequests.css"
import { CheckOutlined, CloseOutlined } from '@ant-design/icons'
import { TailSpin } from 'react-loader-spinner'

function FriendRequests(props) {
  let [sent, setSentRequests] = useState([])
  let [received, setReceived] = useState([])
  let [sentVisible, setSentVisible] = useState(true)
  let [receivedVisible, setReceivedVisible] = useState(false)

  async function getRequests() {
    props.setLoading(true)
    
    let res = await fetch("http://localhost:8080/getRequests", {
      method: "POST",
      body: JSON.stringify({"id": parseInt(props.userDetails.id)}),
      headers: {
        "Authorization": `Bearer ${props.userDetails.token}`,
        "Content-type": "application/json"
      }
    }).then(async(res) => await res.json())

    let res2 = await fetch("http://localhost:8080/ReceivedRequests", {
      method: "POST",
      body: JSON.stringify({"id": parseInt(props.userDetails.id)}),
      headers: {
        "Authorization": `Bearer ${props.userDetails.token}`,
        "Content-type": "application/json"
      }
    }).then(async(res) => await res.json())
    
    setSentRequests(res.allRequests)
    setReceived(res2.allRequests)
    props.setLoading(false)
  }

  useEffect(() => {
    getRequests()
  }, [])

  return (
    <>
      
      <TailSpin
        visible={props.loading}
        height="80"
        width="80"
        color="#4fa94d"
        ariaLabel="tail-spin-loading"
        radius="1"
        wrapperStyle={{
          position: "fixed",
          width: "100%",
          height: "100vh",
          display: props.loading?"flex":"none",
          justifyContent: "center",
          alignItems: "center",
          backgroundColor: "rgba(0, 0, 0, 0.5)",
        }}
        wrapperClass=""
      />

      {!props.loading && (
        <div className='friendRequest'>
          <div className="requests_control">
            <div className="control">
              <div className="sent" onClick={() => {
                setReceivedVisible(false)
                setSentVisible(true)
              }}>Sent</div>
              <div className="reject" onClick={() => {
                setSentVisible(false)
                setReceivedVisible(true)
              }}>Received</div>
            </div>
          </div>

          <div className="requests">
            <div className="details">
              {!sentVisible ? (
                received.length !== 0 ? received.map((value, index) => (
                  <div className="row" key={index}>
                    <div className="username_profile">
                      <div className="profile_image" style={{marginRight: "1rem"}}>
                        <img className='profile' src={value.to.profileLink} alt="Profile" />
                      </div>
                      <div className="username" style={{display: "flex", alignItems: "center", justifyContent: "center"}}>
                        {value.from.username}
                      </div>
                    </div>

                    <div className="accept_reject">
                      <div className="accept" style={{cursor: "pointer", color: "green", display: "flex", alignItems: "center", justifyContent: "center"}}>
                        <CheckOutlined/>
                      </div>
                      <div className="reject" style={{cursor: "pointer", color: "red", display: "flex", alignItems: "center", justifyContent: "center"}}>
                        <CloseOutlined/>
                      </div>
                    </div>
                  </div>
                )) : (
                  <div>No requests received yet!</div>
                )
              ) : (
                sent.length !== 0 ? sent.map((value, index) => (
                  <div className="received" key={index}>
                    <div className="row" key={index}>
                    <div className="username_profile">
                      <div className="profile_image" style={{marginRight: "1rem"}}>
                        <img className='profile' src={value.to.profileLink} alt="Profile" />
                      </div>
                      <div className="username" style={{display: "flex", alignItems: "center", justifyContent: "center"}}>
                        {value.to.username}
                      </div>
                    </div>

                    <div className="status" style={{justifyContent:"center",alignItems:"center",display:"flex"}}>
                      {value.status}
                    </div>
                  </div>
                  </div>
                )) : (
                  <div>No requests sent yet!</div>
                )
              )}
            </div>
          </div>
        </div>
      )}
    </>
  )
}

export default FriendRequests