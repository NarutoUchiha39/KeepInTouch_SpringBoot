package com.BootProject.Project.Service;

import com.BootProject.Project.Models.User;
import com.BootProject.Project.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    UserRepository userRepository;

    public String extractEmail(String token){
        return extractClaim(token,"email");
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token) < System.currentTimeMillis() ;
    }

    public Boolean isValid(String token, User user){

        String email = extractEmail(token);
        return user.getEmail().equals(email) && !isTokenExpired(token);
    }

    private Long extractExpiration(String token){
        return extractClaim(token,"expiration");
    }

    public String extractID(String token){
        return extractClaim(token,"id");
    }

    private <T> T extractClaim(String token, String claim){
        Claims claims = extractAllClaims(token);

        if(claim.equals("expiration")){
            return (T) claims.get("exp");
        }else if(claim.equals("email")){
            return (T) claims.get("email");
        }else{
            return (T) claims.get("id");
        }
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(SignWithKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey SignWithKey(){
        byte[]keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getJWTToken(User user){

        Map<String,String>map = new HashMap<>();

        map.put("email",user.getEmail());
        map.put("username",user.getUsername());
        map.put("profile_link",user.getProfileLink());
        map.put("id",user.getUserID().toString());

        String Token = Jwts.builder()
                .claims(map)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()*24*60*60*1000))
                .signWith(SignWithKey())
                .compact();

        return Token;

    }
}
