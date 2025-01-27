package com.BootProject.Project.SecurityController;

import com.BootProject.Project.Config.EmailService;
import com.BootProject.Project.Models.User;
import com.BootProject.Project.Classes.RegisterResponse;
import com.BootProject.Project.Models.User_Login_Validation;
import com.BootProject.Project.Models.User_Registeration_Validation;
import com.BootProject.Project.Repository.UserRepository;
import com.BootProject.Project.Service.AuthService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@RestController
public class LoginRegister {

    private static final Logger log = LoggerFactory.getLogger(LoginRegister.class);
    AuthService authService;
    EmailService emailService;
    UserRepository userRepository;
    Cloudinary cloudinary;

    @Value("${spring.link}")
    String link;


    private static final String path = Paths.get(System.getProperty("user.dir"), "uploads").toString();

    @Autowired
    public LoginRegister(AuthService authService, EmailService emailService, UserRepository userRepository, Cloudinary cloudinary) {
        this.authService = authService;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.cloudinary = cloudinary;
    }

    public RegisterResponse sendFile(HttpServletRequest request) throws ServletException, IOException {

                    RegisterResponse response = new RegisterResponse();

                    Part file = request.getPart("file");
                    String name = request.getParameter("username");
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");

                    if(name == null || email == null || password == null){
                        response.setMessage("Invalid Input");
                        response.setStatusCode(403);
                        return response;
                    }

                    if(file.getSize() <= 4){
                        response.setMessage("No file uploaded");
                        response.setStatusCode(500);
                        return response;
                    }

                    String fileName = email+"_"+System.currentTimeMillis()+"_"+file.getSubmittedFileName();
                    OutputStream out = null;
                    File file1 = Files.createTempFile(fileName, null).toFile();
                    InputStream fileContent= null;

                    try{
                            out = new FileOutputStream(file1.getAbsolutePath());
                            fileContent = file.getInputStream();
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

                            User_Registeration_Validation user = new User_Registeration_Validation(email,password,name,profile_link);
                            User user1 = userRepository.findByEmail(user.getEmail());
                            if(user1 == null){
                                return  authService.registerResponseEmail(user);
                            }else{
                                response.setMessage("User already exists");
                                response.setToken(null);
                                response.setStatusCode(403);
                                return response;
                            }
                    }catch (Exception e){
                        log.error(e.fillInStackTrace().toString());
                        file1.delete();
                        response.setMessage("Internal Server error");
                        response.setStatusCode(500);
                        return response;

                    }finally {
                        file1.delete();
                        if (out != null) {
                            out.close();
                        }
                        if (fileContent != null) {
                            fileContent.close();
                        }
                    }
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET},path = "/register")
    public ResponseEntity<RegisterResponse> RegisterPage(HttpServletRequest request, HttpServletRequest response){

        RegisterResponse response1 = new RegisterResponse();

        if(request.getMethod().equals("POST")){

            try {
                RegisterResponse registerResponse = sendFile(request);
                return new ResponseEntity<>(registerResponse,HttpStatusCode.valueOf(registerResponse.getStatusCode()));

            }catch (IOException | ServletException e){
                response1.setStatusCode(500);
                response1.setMessage("Internal Server error");
                response1.setToken(null);

                return new ResponseEntity<>(response1,HttpStatusCode.valueOf(500));
            }
        } else if (request.getMethod().equals("GET")) {
            
            String parameter = request.getParameter("token");
            RegisterResponse response2 = authService.VerifyToken(parameter);
            return new ResponseEntity<>(response2,HttpStatusCode.valueOf(response2.getStatusCode()));
        }

        response1.setMessage("Method not allowed");
        return new ResponseEntity<>(response1,HttpStatusCode.valueOf(403));
    }

    @RequestMapping(method = RequestMethod.POST,path = "/login")
    public ResponseEntity<RegisterResponse> LoginPage(@Valid @RequestBody User_Login_Validation user){
        RegisterResponse response = authService.authenticate(user);
        return ResponseEntity.ok(response);
    }


}
