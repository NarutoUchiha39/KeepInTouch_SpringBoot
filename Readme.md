## *Keep In Touch*
Growing up we all can agree how difficult it is to keep in touch with our loved ones. *Well I have the thing for you*. Keep In Touch is a website where you can keep in touch with your loved ones. <br><br>
The website has the following features:<br>
1. *Search for friends with their registered email id*
2. *Have the ability to accept or reject friend requests*
3. *View the sent and recieved friend request*
4. *Send and recieve friend text message*
5. *Send media (Images,Pdf documents)*
6. *Email verification*

* ### <p>*Interested? Here's a small demo*</p>
[KeepInTouch.mp4](https://github.com/user-attachments/assets/2f104c77-f476-43bc-8ee6-7f57fcad8c28)

#### *Sounds interesting? Let's dive into the nitty gritty now, shall we?*
<br>

### <p align='center'> Made with ❤️ using </p>

<p align='center'>
    <img with=120px height=120px style="margin-right:30px" src='https://github.com/user-attachments/assets/c66ba914-a498-4e4f-a00f-cf510c1e2b14'/>
    <img with=120px height=120px style="margin-right:30px" src='https://github.com/user-attachments/assets/fe168162-d3d0-41e6-b5e4-72251bca6d2b'/>
    <img with=120px height=120px style="margin-right:30px" src='https://github.com/NarutoUchiha39/KeepInTouch/assets/104666748/76d209bc-3549-40d9-8732-2c8edca23a62'/>
</p>

<br><br><br>

* ### <p>*Microservices used in the project*</p>
1. API Gateway and Security Microservices:
    * This is a microservice that integrates Spring Security with API gateway.
    * The Spring Security checks for JWT Token in the header. If the JWT Token is not present then redirect the user back to the login page.
    * After the register or login request is sent to the microservice, the microservice then creates a JWT token and sends it to the frontend. The frontend then sends authenticated request to the security microservice.
    * The security microservice then authenticates the request by verifying the JWT token sent in the header. If the token is not expired and its signature is valid then the request is forwarded to the messaging backend.
    * For the register request, it is checked if the user already exist or not. If the user exists then an error is thrown. Else the user is sent a long to verify the person. After clicking the link, the JWT token is generated and sent to the frontend. If the user clicks the link after 60 seconds, the token is invalidated and the user needs to re register
    * For profile picture we make use of cloudinary to store images.
   #### *Libraries used in this microservice:*
   1. spring-cloud-starter-gateway-mvc: Used for api gateway. Declares routes that are defined in other microservices. The incoming requests are forwarded to their                             respective microservice
   2. spring-boot-starter-security: Used for creating security filters that are applied to the incoming requests. The filters check for presence and validity of JWT tokens in                  the incoming request. The CORS filters are also created using this library
   3. jjwt-api, jjwt-jackson, jjwt-impl: Used for creating utility function to create and validate JWT tokens.
   4. spring-boot-starter-mail: Library used for sending mail. Mail is sent through google smtp servers.
   5. spring-boot-starter-data-jpa: Used for creating repositories and persisting data in database
   6. postgresql: Used as database driver for connecting to postgresql database
   7. cloudinary-taglib,cloudinary-http5: Cloudinary is used for storing media in the cloud. Cloudinary generates urls of the uploaded documents.
   8. jackson-databind: Faster XML helps in processing JSON data. Jackson maps the JSON string to java classes(Deserializing) or the java object to JSON strings(Serializing). Jackson          helps in validating the incoming data too.   

2. Messaging Microservice:
   * The messaging microservice is responsible for sending friend request, recieving friend request and sending messages.
   * The messaging microservice accepts only authenticated requests only from the security microservice.
   * For media we make use of cloudinary to store the pdf files and images
   #### *Libraries used in messaging microservice:*
   1. spring-boot-starter-web: Used for creating routes and allow http methods to be made to the server by the frontend
   2. postgresql,cloudinary-taglib,cloudinary-http5,spring-boot-starter-data-jpa: Same as above
      
3. KeepInTouchModel:
   * The messaging and Project microservice share a common model named user_profile. In order to share the model between the microservices, a seperate project is created
   * In order to share the common model between the microservice, we create a shared entity manager in the messaging microservice.
   * The user_profile model is created in the Project microservice. The ddl command for the project is set to create-drop
   * The ddl command of the messaging microservice is set to 'update' so that the user_profile model is not created again.
   * In order to use the shared model, we make the microservice and use the command mvn install in order to install the service in our local maven repository
   * The shared model is included in the messaging microservice via the pom.xml file



   
* ### <p>*Flow of requests*</p>
<br><br>
<p align='center'>
    <img  src='https://github.com/user-attachments/assets/a7d926ef-e17f-4ae9-bc7d-50d3731cac19'/>
</p>

<br><br><br>
* ### <p>*Tables used in the project*</p>
<br><br>
<img src='https://github.com/user-attachments/assets/e6bb65b9-e157-4965-91e6-9a63d8d804ac'/>


