package com.BootProject.Project.DTO;


import com.BootProject.Project.Models.User;
import com.BootProject.Project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDTO {
    UserRepository userRepository;

    @Autowired
    UserDTO(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findUser(String email){
        return userRepository.findByEmail(email);
    }

}
