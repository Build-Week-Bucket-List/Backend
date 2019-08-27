package com.lambdaschool.bucketlist.controllers;

import com.lambdaschool.bucketlist.models.ErrorDetail;
import com.lambdaschool.bucketlist.models.Friend;
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

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @GetMapping(value = "/users",
//                produces = {"application/json"})
//    public ResponseEntity<?> listAllUsers(HttpServletRequest request)
//    {
//        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        List<User> myUsers = userService.findAll();
//        return new ResponseEntity<>(myUsers, HttpStatus.OK);
//    }

//    @GetMapping(value = "/user",
//            produces = {"application/json"})
//    public ResponseEntity<?> getUser(HttpServletRequest request, Authentication authentication) {
//        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        User u = userrepos.findByUsername(authentication.getName());
//
//        return new ResponseEntity<>(u, HttpStatus.OK);
//    }
//
//    @GetMapping(value = "/user/{userId}",
//                produces = {"application/json"})
//    public ResponseEntity<?> getUser(HttpServletRequest request,
//                                     @PathVariable
//                                             Long userId)
//    {
//        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        User u = userService.findUserById(userId);
//        return new ResponseEntity<>(u, HttpStatus.OK);
//    }


//    @GetMapping(value = "/getusername",
//                produces = {"application/json"})
//    @ResponseBody
//    public ResponseEntity<?> getCurrentUserName(HttpServletRequest request, Authentication authentication)
//    {
//        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        return new ResponseEntity<>(authentication.getPrincipal(), HttpStatus.OK);
//    }

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
         friendService.sendRequest(username, authentication.getName());
         return new ResponseEntity<>("Friend request sent", HttpStatus.OK);
    }

    @ApiOperation(value = "Accept friend request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Friend request accepted", response = void.class),
            @ApiResponse(code = 404, message = "Request Id not found", response = ErrorDetail.class),
            @ApiResponse(code = 500, message = "Server error", response = ErrorDetail.class)
    })
    @PutMapping(value = "add/{requestid}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> acceptRequest(
            @ApiParam(value = "Request id", required = true, example = "4")
            @PathVariable long requestid){
        friendService.update(requestid);
        return  new ResponseEntity<>(null, HttpStatus.OK);
    }

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @PostMapping(value = "/user",
//                 consumes = {"application/json"},
//                 produces = {"application/json"})
//    public ResponseEntity<?> addNewUser(HttpServletRequest request, @Valid
//    @RequestBody
//            User newuser) throws URISyntaxException
//    {
//        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        newuser = userService.save(newuser);
//
//        // set the location header for the newly created resource
//        HttpHeaders responseHeaders = new HttpHeaders();
//        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userid}").buildAndExpand(newuser.getUserid()).toUri();
//        responseHeaders.setLocation(newUserURI);
//
//        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
//    }


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