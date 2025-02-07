package com.BootProject.Project.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class CorsConfig extends CorsConfiguration{

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/login").allowedOrigins("*");
                registry.addMapping("/register").allowedOrigins("*");
                registry.addMapping("/").allowedOrigins("*");
                registry.addMapping("/addFriend").allowedOrigins("*");
                registry.addMapping("/changeStatus").allowedOrigins("*");
                registry.addMapping("/getFriends").allowedOrigins("*");
                registry.addMapping("/sendMessage").allowedOrigins("*");
                registry.addMapping("/getMessages").allowedOrigins("*");

            }
        };
    }






}