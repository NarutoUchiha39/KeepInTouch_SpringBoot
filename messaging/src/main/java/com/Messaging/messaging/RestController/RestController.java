package com.Messaging.messaging.RestController;

import com.Messaging.messaging.Class.*;
import com.Messaging.messaging.Models.FriendRequest;
import com.Messaging.messaging.Response.GenericListOfRequests;
import com.Messaging.messaging.Response.GenericResponse;
import com.Messaging.messaging.Response.UserDTOResponse;
import com.Messaging.messaging.Service.FriendService;
import org.Model.shared.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private static final Logger log = LoggerFactory.getLogger(RestController.class);
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

    @RequestMapping(method = RequestMethod.POST, path = "/getFriends")
    public ResponseEntity<GetAllFriends> getFriends(@RequestBody GetFriends getFriends){
        GetAllFriends getAllFriends = friendService.getFriends(getFriends.getId());
        return new ResponseEntity<>(getAllFriends,HttpStatusCode.valueOf(getAllFriends.getStatusCode()));
    }

    @RequestMapping(method = RequestMethod.POST,path="/changeStatus")
    public ResponseEntity<GenericResponse>changeStatus(@RequestBody ChangeRequestStatus changeRequestStatus){
        GenericResponse genericResponse = friendService.changeStatus(changeRequestStatus.getFrom(), changeRequestStatus.getTo(), changeRequestStatus.getStatus());
        return new ResponseEntity<>(genericResponse,HttpStatusCode.valueOf(genericResponse.getStatusCode()));
    }

    @RequestMapping(method = RequestMethod.POST,path = "getMessages")
    public ResponseEntity<List<com.Messaging.messaging.Models.Messages>> getMessages(@RequestBody GetFriendRequestID getFriendRequestID){
        return new ResponseEntity<>(friendService.getMessages(getFriendRequestID),HttpStatusCode.valueOf(200));
    }

    @RequestMapping(method = RequestMethod.POST,path = "/sendMessage")
    public ResponseEntity<GenericResponse> sendMessage(@ModelAttribute("Messages") Messages file){
            if(file.getMessage() == null && file.getFile() == null){
                return new ResponseEntity<>(new GenericResponse("Please enter a message or attach a file!",500),HttpStatusCode.valueOf(400));
            }
            try {
                return new ResponseEntity<>(friendService.sendMessage(file),HttpStatusCode.valueOf(200));
            }catch (IOException e){
                log.error(String.valueOf(e.fillInStackTrace()));
                return new ResponseEntity<>(new GenericResponse("Internal Server error",500),HttpStatusCode.valueOf(500));
            }

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
