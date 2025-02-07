package com.BootProject.Project.RouteController;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class gatewayRoutes {
    @Bean
    public RouterFunction<ServerResponse> getHome(){
       return route("HomePage").GET("/",http("http://localhost:8081/"))
               .build();
    }


    @Bean
    public RouterFunction<ServerResponse> addFriend(){
        return route("addFriend").POST("/addFriend",http("http://localhost:8081/addFriend")).build();
    }

    @Bean
    public RouterFunction<ServerResponse> checkRequests(){
        return route("checkSentRequests").POST("/getRequests",http("http://localhost:8081/getRequests")).build();
    }

    @Bean
    public RouterFunction<ServerResponse> checkReceivedRequests(){
        return route("checkReceivedRequests").POST("/ReceivedRequests",http("http://localhost:8081/ReceivedRequests")).build();
    }

    @Bean
    public RouterFunction<ServerResponse> getAllFriends(){
        return route("getFriends").POST("/getFriends",http("http://localhost:8081/getFriends")).build();
    }

    @Bean
    public RouterFunction<ServerResponse> changeStatus(){
        return route("ChangeStatus").POST("/changeStatus",http("http://localhost:8081/changeStatus")).build();
    }

    @Bean
    public RouterFunction<ServerResponse>senMessage(){
        return route("SendMessage").POST("/sendMessage",http("http://localhost:8081/sendMessage")).build();
    }

    @Bean
    public RouterFunction<ServerResponse>getMessages(){
        return route("getMessages").POST("/getMessages",http("http://localhost:8081/getMessages")).build();
    }
}
