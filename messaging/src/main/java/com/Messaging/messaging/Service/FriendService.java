package com.Messaging.messaging.Service;

import com.Messaging.messaging.Class.AddFriendClass;
import com.Messaging.messaging.Models.FriendRequest;
import com.Messaging.messaging.Models.FriendRequestKey;
import com.Messaging.messaging.Repository.AddFriendRepository;
import com.Messaging.messaging.Response.GenericResponse;
import com.Messaging.messaging.Response.UserDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.Model.shared.User;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class FriendService {


    AddFriendRepository addFriendRepository;

    @Value("${spring.security.url}")
    String url;

    @Autowired
    public FriendService(AddFriendRepository addFriendRepository) {
        this.addFriendRepository = addFriendRepository;
    }

    public List<FriendRequest> getReceivedRequests(Long id){

        return addFriendRepository.findAllReceivedtRequest(id);
    }

    public List<FriendRequest> getSentRequests(Long id){

        List<FriendRequest>friendRequests = addFriendRepository.findAllSentRequest(id);
        if(friendRequests == null){
            return null;
        }else {
            return friendRequests;
        }
    }

    public GenericResponse changeStatus(Long from, Long to, String status){

        Optional<FriendRequest> friendRequest = addFriendRepository.findById(new FriendRequestKey(to,from));
        GenericResponse genericResponse = new GenericResponse();

        if(friendRequest.isPresent()){
            FriendRequest friendRequest1 = friendRequest.get();
            FriendRequest friendRequest2 = new FriendRequest();
            friendRequest2.setFriendRequestKey(friendRequest1.getFriendRequestKey());
            friendRequest2.setFrom(friendRequest1.getFrom());
            friendRequest2.setTo(friendRequest1.getTo());
            friendRequest2.setStatus(status);
            addFriendRepository.save(friendRequest2);
        }else{
            genericResponse.setStatusCode(400);
            genericResponse.setMessage("Invalid request");
            return genericResponse;
        }

        genericResponse.setStatusCode(200);
        genericResponse.setMessage("Request "+status+" successfully");
        return  genericResponse;

    }

    public GenericResponse addFriend(String email, User from){
        RestTemplate restTemplate = new RestTemplate();
        User user1 = new User();
        user1.setEmail(email);
        ResponseEntity<UserDTOResponse> user2 =  restTemplate.postForEntity(url+"/findUser",user1, UserDTOResponse.class);
        ResponseEntity<UserDTOResponse> user3 =  restTemplate.postForEntity(url+"/findUser",from, UserDTOResponse.class);

        if(user2.getBody().getUse() != null){
            User user = user2.getBody().getUse();
            User user4 = user3.getBody().getUse();
            FriendRequestKey friendRequestKey = new FriendRequestKey(user.getUserID(),user4.getUserID());

            FriendRequest request = new FriendRequest();
            request.setFrom(user4);
            request.setTo(user);
            request.setStatus("Pending");
            request.setFriendRequestKey(friendRequestKey);
            addFriendRepository.save(request);
            return new GenericResponse("Sent request successfully!",200);
        }else{
            return new GenericResponse("User does not exists :(",500);
        }
    }

}
