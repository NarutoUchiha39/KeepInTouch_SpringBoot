package com.Messaging.messaging.Service;

import com.Messaging.messaging.Class.*;
import com.Messaging.messaging.Models.FriendRequest;
import com.Messaging.messaging.Models.FriendRequestKey;
import com.Messaging.messaging.Models.Friends;
import com.Messaging.messaging.Repository.AddFriendRepository;
import com.Messaging.messaging.Repository.FriendRepository;
import com.Messaging.messaging.Repository.MessagesRepository;
import com.Messaging.messaging.Response.GenericResponse;
import com.Messaging.messaging.Response.UserDTOResponse;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.Model.shared.User;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class FriendService {


    AddFriendRepository addFriendRepository;
    FriendRepository friendRepository;
    MessagesRepository messagesRepository;
    Cloudinary cloudinary;

    @Value("${spring.security.url}")
    String url;

    @Autowired
    public FriendService(AddFriendRepository addFriendRepository, FriendRepository friendRepository, MessagesRepository messagesRepository,Cloudinary cloudinary) {
        this.addFriendRepository = addFriendRepository;
        this.messagesRepository = messagesRepository;
        this.friendRepository = friendRepository;
        this.cloudinary = cloudinary;
    }

    public List<FriendRequest> getReceivedRequests(Long id){

        return addFriendRepository.findAllReceivedtRequest(id);
    }

    public List<com.Messaging.messaging.Models.Messages> getMessages(GetFriendRequestID getFriendRequestID){
        return messagesRepository.getAllMessages(getFriendRequestID.getId(), getFriendRequestID.getTo());
    }

    public GenericResponse sendMessage(Messages messages) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        User from = new User();
        from.setEmail(messages.getFrom());

        User to = new User();
        to.setEmail(messages.getTo());

        ResponseEntity<UserDTOResponse> user2 =  restTemplate.postForEntity(url+"/findUser",to, UserDTOResponse.class);
        ResponseEntity<UserDTOResponse> user3 =  restTemplate.postForEntity(url+"/findUser",from, UserDTOResponse.class);


        com.Messaging.messaging.Models.Messages messages1 = new com.Messaging.messaging.Models.Messages();
        if(messages.getMessage() != null){
            messages1.setMessages(messages.getMessage());
        }
        messages1.setFrom(user3.getBody().getUse());
        messages1.setTo(user2.getBody().getUse());
        if(messages.getFile() != null){
            String fileName = messages.getFile().getOriginalFilename()+"_"+System.currentTimeMillis()+"_"+messages.getFrom();
            File file1 = Files.createTempFile(fileName, null).toFile();
            InputStream fileContent= null;
            OutputStream out = null;
            try{
                out = new FileOutputStream(file1.getAbsolutePath());
                fileContent = messages.getFile().getInputStream();
                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = fileContent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                Map params = ObjectUtils.asMap("public_id", fileName,
                        "resource_type", "image");

                Map uploadResult = cloudinary.uploader().upload(new File(file1.getAbsolutePath()), params);
                String profile_link = uploadResult.get("url").toString();
                System.out.println(profile_link);
                file1.delete();
                messages1.setLink(profile_link);
            }catch (Exception e){
                e.fillInStackTrace();
                return new GenericResponse("Internal Server Error",500);
            }finally {
                if (out != null) {
                    out.close();
                }
                if (fileContent != null) {
                    fileContent.close();
                }
            }
        }
        messagesRepository.save(messages1);
        return new GenericResponse("Message Sent successfully",200);
    }

    public GetAllFriends getFriends(Long userid){

        GetAllFriends getAllFriends = new GetAllFriends();
        List<Friends> friends = friendRepository.getAllFriends(userid);
        getAllFriends.setFriendsList(friends);
        getAllFriends.setStatusCode(200);
        return getAllFriends;
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

            if(status.equals("accept")){
                Friends friends = new Friends();
                friends.setFriendRequestKey(new FriendRequestKey(to,from));
                friends.setFrom(friendRequest1.getFrom());
                friends.setTo(friendRequest1.getTo());
                friendRepository.save(friends);
            }

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
