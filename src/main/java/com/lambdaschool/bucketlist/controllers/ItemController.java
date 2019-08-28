package com.lambdaschool.bucketlist.controllers;

import com.lambdaschool.bucketlist.models.ErrorDetail;
import com.lambdaschool.bucketlist.models.Item;
import com.lambdaschool.bucketlist.models.Journal;
import com.lambdaschool.bucketlist.models.User;
import com.lambdaschool.bucketlist.repository.UserRepository;
import com.lambdaschool.bucketlist.services.FriendService;
import com.lambdaschool.bucketlist.services.ItemService;
import com.lambdaschool.bucketlist.services.UserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/list")
public class ItemController
{
    private static final Logger logger = LoggerFactory.getLogger(RolesController.class);

    @Autowired
    ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private FriendService friendService;

    @ApiOperation(value = "Returns the users info including userid, username, and items")
    @GetMapping(value = "/user",
            produces = {"application/json"})
    public ResponseEntity<?> getUser(HttpServletRequest request, Authentication authentication) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        User u = userrepos.findByUsername(authentication.getName());
        u.setRequests(friendService.getMyFriends(u.getUsername()));
        u.setFriends(friendService.getAcceptedFriends(u.getUsername()));

        return new ResponseEntity<>(u, HttpStatus.OK);
    }



//    @GetMapping(value = "/{itemid}",
//                produces = {"application/json"})
//    public ResponseEntity<?> getItem(HttpServletRequest request,
//                                      @PathVariable
//                                              Long itemid)
//    {
//        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        Item q = itemService.findQuoteById(itemid);
//        return new ResponseEntity<>(q, HttpStatus.OK);
//    }


//    @GetMapping(value = "/username/{userName}",
//                produces = {"application/json"})
//    public ResponseEntity<?> findItemsByUserName(HttpServletRequest request,
//                                                 @PathVariable
//                                                         String userName)
//    {
//        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
//
//        List<Item> theItems = itemService.findByUserName(userName);
//        return new ResponseEntity<>(theItems, HttpStatus.OK);
//    }

//
    @ApiOperation(value = "Create new Bucket list item")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "itemtitle", required = true, example = "Climbing Mt. Everest"),
            @ApiImplicitParam(value = "itemdesc", required = false, example = "Hopefully within the next 5 years I will climb")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Bucket list item created successfully"),
            @ApiResponse(code = 500, message = "Server Error, could not create item", response = ErrorDetail.class)
    })
    @PostMapping(value = "/item", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewItem(HttpServletRequest request,
    @RequestBody Item item,
                                        Authentication authentication) throws URISyntaxException
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        itemService.save(item);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
//        URI newQuoteURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{itemid}").buildAndExpand(newItem.getItemid()).toUri();
//        responseHeaders.setLocation(newQuoteURI);
        return new ResponseEntity<>(itemService.findItemById(item.getItemid()), responseHeaders, HttpStatus.CREATED);
    }

    @PostMapping(value = "/journal/{itemid}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addToJournal(@PathVariable long itemid,
                                          @RequestBody String journalentry){

        itemService.addToJournal(itemid, journalentry);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping(value = "/item/{id}")
    public ResponseEntity<?> deleteItemById(HttpServletRequest request,
                                             @PathVariable
                                                     long id)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        itemService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/item/{id}")
    public ResponseEntity<?> UpdateItemById(HttpServletRequest request,
                                            @PathVariable
                                                    long id,
                                            @RequestBody Item newItem) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        itemService.update(id, newItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
