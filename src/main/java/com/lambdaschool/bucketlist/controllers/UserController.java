package com.lambdaschool.bucketlist.controllers;

import com.lambdaschool.bucketlist.models.ErrorDetail;
import com.lambdaschool.bucketlist.models.Friend;
import com.lambdaschool.bucketlist.models.Response;
import com.lambdaschool.bucketlist.models.User;
import com.lambdaschool.bucketlist.repository.UserRepository;
import com.lambdaschool.bucketlist.services.FriendService;
import com.lambdaschool.bucketlist.services.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(RolesController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserRepository userrepos;


    @GetMapping(value = "/search/{username}", produces = {"application/json"})
    public ResponseEntity<?> searchUsers(@PathVariable String username){
        List<String> foundUsers = userService.searchUsers(username);
        System.out.println("*****************************************************" + userService.searchUsers(username));
        return new ResponseEntity<>(foundUsers, HttpStatus.OK);
    }

    @ApiOperation(value = "Send friend request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Friend request sent", response = void.class),
            @ApiResponse(code = 404, message = "Username not found", response = ErrorDetail.class),
            @ApiResponse(code = 500, message = "Server error", response = ErrorDetail.class)
    })
    @PostMapping(value = "add/{username}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addFriend(
            @ApiParam(value = "Username", required = true, example = "JaneDoenomo")
            @PathVariable
                    String username,
            Authentication authentication){

        Response newResponse = new Response();
        if(friendService.sendRequest(username, authentication.getName()) != null){
            newResponse.setMessage("Friend Request Sent!");
        } else {
            newResponse.setError("Request already exists");
        }
//        friendService.sendRequest(username, authentication.getName());
        return new ResponseEntity<>(newResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Accept friend request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Friend request accepted", response = void.class),
            @ApiResponse(code = 404, message = "Request Id not found", response = ErrorDetail.class),
            @ApiResponse(code = 500, message = "Server error", response = ErrorDetail.class)
    })
    @PutMapping(value = "/add/{requestid}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> acceptRequest(
            @ApiParam(value = "Request id", required = true, example = "4")
            @PathVariable long requestid){
        friendService.update(requestid);
        return  new ResponseEntity<>(null, HttpStatus.OK);
    }



//    @PutMapping(value = "/user/{id}")
//    public ResponseEntity<?> updateUser(HttpServletRequest request,
//                                        @RequestBody
//                                                User updateUser,
//                                        @PathVariable
//                                                long id)
//    {
//        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        userService.update(updateUser, id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserById(HttpServletRequest request,
                                            @PathVariable
                                                    long id)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}