package com.BootProject.Project.RouteController;


import com.BootProject.Project.Classes.UserDTOResponse;
import com.BootProject.Project.DTO.UserDTO;
import com.BootProject.Project.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Routes {
    UserDTO userDTO;

    @Autowired
    Routes(UserDTO userDTO){
        this.userDTO = userDTO;
    }

    @RequestMapping(method = RequestMethod.POST,path="/findUser")
    public ResponseEntity<UserDTOResponse> userDTOResponse(@RequestBody User user){
        User user1 =  userDTO.findUser(user.email);
        UserDTOResponse userDTOResponse = new UserDTOResponse();
        if(user1 == null){
            System.out.println("Found");
            userDTOResponse.setStatusCode(200);
            userDTOResponse.setUse(null);
        }else{
            userDTOResponse.setStatusCode(200);
            userDTOResponse.setUse(user1);
        }
        return new ResponseEntity<>(userDTOResponse, HttpStatusCode.valueOf(userDTOResponse.getStatusCode()));
    }
}
