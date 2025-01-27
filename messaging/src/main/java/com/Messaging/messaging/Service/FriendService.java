package com.Messaging.messaging.Service;

import com.Messaging.messaging.Models.FriendRequest;
import com.Messaging.messaging.Models.FriendRequestKey;
import com.Messaging.messaging.Repository.AddFriendRepository;
import com.Messaging.messaging.Response.GenericResponse;
import com.Messaging.messaging.Response.UserDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.Model.shared.User;
import org.springframework.web.client.RestTemplate;

@Service
public class FriendService {


    AddFriendRepository addFriendRepository;

    @Value("${spring.security.url}")
    String url;

    @Autowired
    public FriendService(AddFriendRepository addFriendRepository) {
        this.addFriendRepository = addFriendRepository;
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
