package com.Messaging.messaging.RestController;

import com.Messaging.messaging.Class.AddFriendClass;
import com.Messaging.messaging.Class.ChangeRequestStatus;
import com.Messaging.messaging.Class.GetFriendRequestID;
import com.Messaging.messaging.Models.FriendRequest;
import com.Messaging.messaging.Response.GenericListOfRequests;
import com.Messaging.messaging.Response.GenericResponse;
import com.Messaging.messaging.Service.FriendService;
import org.Model.shared.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    FriendService friendService;

    @Autowired
    RestController(FriendService friendService){
        this.friendService = friendService;
    }

    @RequestMapping(method = RequestMethod.GET, path="/")
    public String Home(){
        return "@";
    }

    @RequestMapping(method = RequestMethod.POST,path = "/ReceivedRequests")
    public ResponseEntity<GenericListOfRequests> sentRequests(@RequestBody GetFriendRequestID user){

        List<FriendRequest> friendRequests = friendService.getReceivedRequests(user.getId());
        GenericListOfRequests genericListOfRequests = new GenericListOfRequests();
        if(friendRequests == null){
            genericListOfRequests.setAllRequests(null);
            genericListOfRequests.setStatusCode(200);
        }else{
            genericListOfRequests.setAllRequests(friendRequests);
            genericListOfRequests.setStatusCode(200);
        }
        return new ResponseEntity<>(genericListOfRequests,HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.POST,path="/changeStatus")
    public ResponseEntity<GenericResponse>changeStatus(@RequestBody ChangeRequestStatus changeRequestStatus){
        GenericResponse genericResponse = friendService.changeStatus(changeRequestStatus.getFrom(), changeRequestStatus.getTo(), changeRequestStatus.getStatus());
        return new ResponseEntity<>(genericResponse,HttpStatusCode.valueOf(genericResponse.getStatusCode()));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/getRequests")
    public ResponseEntity<GenericListOfRequests> getRequests(@RequestBody GetFriendRequestID user){

        List<FriendRequest> friendRequests = friendService.getSentRequests(user.getId());
        GenericListOfRequests genericListOfRequests = new GenericListOfRequests();
        if(friendRequests == null){
            genericListOfRequests.setAllRequests(null);
            genericListOfRequests.setStatusCode(200);
        }else{
            genericListOfRequests.setAllRequests(friendRequests);
            genericListOfRequests.setStatusCode(200);
        }

        return new ResponseEntity<>(genericListOfRequests,HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.POST ,path = "/addFriend")
    public ResponseEntity<GenericResponse> addFriend(@RequestBody AddFriendClass user ){
        GenericResponse genericResponse= friendService.addFriend(user.getTo(),user.getFrom());
        return new ResponseEntity<>(genericResponse, HttpStatusCode.valueOf(genericResponse.getStatusCode()));
    }
}
