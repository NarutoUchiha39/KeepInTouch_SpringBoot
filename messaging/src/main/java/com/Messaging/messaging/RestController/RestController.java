package com.Messaging.messaging.RestController;

import com.Messaging.messaging.Class.AddFriendClass;
import com.Messaging.messaging.Response.GenericResponse;
import com.Messaging.messaging.Service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(method = RequestMethod.POST ,path = "/addFriend")
    public ResponseEntity<GenericResponse> addFriend(@RequestBody AddFriendClass user ){
        GenericResponse genericResponse= friendService.addFriend(user.getTo(),user.getFrom());
        return new ResponseEntity<>(genericResponse, HttpStatusCode.valueOf(genericResponse.getStatusCode()));
    }
}
