package com.BootProject.Project.Service;

import com.BootProject.Project.Classes.EmailDetails;
import com.BootProject.Project.Config.EmailService;
import com.BootProject.Project.Models.*;
import com.BootProject.Project.Repository.EmailTokenRepository;
import com.BootProject.Project.Repository.TokenRepository;
import com.BootProject.Project.Repository.UserRepository;
import com.BootProject.Project.Classes.RegisterResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    TokenRepository tokenRepository;
    JWTService jwtService;
    PasswordEncoder passwordEncoder;
    EmailTokenRepository emailTokenRepository;

    @Value("${spring.link}")
    String link;

    EmailService emailService;

    @Autowired
    public AuthService(UserRepository userRepository,
                       AuthenticationManager authenticationManager,
                       TokenRepository tokenRepository,
                       JWTService jwtService,
                       PasswordEncoder passwordEncoder,
                       EmailTokenRepository emailTokenRepository,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.emailTokenRepository = emailTokenRepository;
        this.emailService = emailService;
    }


    private void revokeAllTokensByUser(User user){
        List<Token>tokens = tokenRepository.findByUserid(user);
        if(tokens.isEmpty()){
            return;
        }
        else{
            tokens.forEach(t->t.setLoggedOut(true));
        }

        tokenRepository.saveAll(tokens);
    }

    public RegisterResponse registerResponseEmail(User_Registeration_Validation user){

            EmailToken user_db = emailTokenRepository.findByEmail(user.getEmail());

            RegisterResponse response = new RegisterResponse();

            if(user_db != null) {
                emailTokenRepository.deleteById(user_db.getId());
            }

            EmailToken register_user = new EmailToken();
            register_user.setEmail(user.getEmail());
            register_user.setPassword(passwordEncoder.encode(user.getPassword()));
            register_user.setUsername(user.getUsername());
            register_user.setExp(System.currentTimeMillis() + 60*1000);
            register_user.setUuid(UUID.randomUUID().toString());
            register_user.setProfileLink(user.getProfileLink());
            EmailToken emailToken = emailTokenRepository.save(register_user);

            String Link = link+"token?token="+ emailToken.getUuid();
            String body = "Hi "+user.getUsername()+",\n"+"Your Registration is pending for approval.\n"
                    +"Click the Link to finish registration: \n" + Link
                    +"\n Thank you";

            EmailDetails emailDetails = new EmailDetails(
                    user.getEmail(),"Registration Request",body
            );
            String res = emailService.sendEmail(emailDetails);
            if(res.equals("Email Sent Successfully")){
                response.setMessage("Registration request Email Sent Successfully");
                response.setToken(null);
                response.setStatusCode(200);

            }else{
                response.setMessage("Cannot send mail");
                response.setToken(null);
                response.setStatusCode(500);
            }
            return response;
    }

    public RegisterResponse VerifyToken(String token){

        EmailToken token_db = emailTokenRepository.findByUuid(token);
        RegisterResponse response = new RegisterResponse();

        if(token_db == null){
            response.setMessage("Invalid Token");
            response.setToken(null);
            response.setStatusCode(403);

            return response;
        }else if(token_db.getExp() < System.currentTimeMillis()){
            response.setMessage("Token has expired");
            response.setToken(null);
            response.setStatusCode(403);
            emailTokenRepository.deleteById(token_db.getId());

            return response;
        }else{

            User user = new User();

            user.setEmail(token_db.getEmail());
            user.setPassword(token_db.getPassword());
            user.setUsername(token_db.getUsername());
            user.setProfileLink(token_db.getProfileLink());

            log.info("DELETE{}", token_db.getId().toString());
            emailTokenRepository.deleteById(token_db.getId());

            return registerUser(user);
        }

    }

    public RegisterResponse registerUser(User user){
        User user_db = userRepository.findByEmail(user.getEmail());
        if(user_db == null){
            User register_user = new User();
            register_user.setEmail(user.getEmail());
            register_user.setPassword(user.getPassword());
            register_user.setUsername(user.getUsername());
            register_user.setProfileLink(user.getProfileLink());
            User user1 =  userRepository.save(register_user);

            String token1 = saveuserToken(user1);
            return new RegisterResponse("Successfully Registered",200,token1);
        }else{
            return new RegisterResponse("Error Registering",200,null);
        }
    }

    public RegisterResponse authenticate(User_Login_Validation user){



        try{
            Authentication user_auth =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(),user.getPassword())
            );
            User user1 = (User)user_auth.getPrincipal();
            revokeAllTokensByUser(user1);
            String token = saveuserToken(user1);
            return new RegisterResponse("Authenticated",200,token);
        } catch (BadCredentialsException E){
            return new RegisterResponse(E.getMessage(),403,null);
        }
    }

    public  String saveuserToken(User user){

        String token = jwtService.getJWTToken(user);
        Token token1 = new Token();
        token1.setLoggedOut(false);
        token1.setToken(token);
        token1.setUserid(user);
        tokenRepository.save(token1);
        return token;
    }
}
