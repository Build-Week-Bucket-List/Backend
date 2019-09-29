package com.lambdaschool.bucketlist.controllers;

import com.lambdaschool.bucketlist.models.*;
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



    @GetMapping(value = "/username/{userName}",
                produces = {"application/json"})
    public ResponseEntity<?> findItemsByUserName(HttpServletRequest request,
                                                 @PathVariable
                                                         String userName)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        Response newResponse = new Response();

        List<Item> friendItems = itemService.findItemByUserName(userName.toLowerCase());
        if(friendItems == null){
            newResponse.setError("No Items for that user");
            return new ResponseEntity<>(newResponse, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(friendItems, HttpStatus.OK);

        }
    }

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
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(itemService.findItemById(item.getItemid()), responseHeaders, HttpStatus.CREATED);
    }

    @PostMapping(value = "/journal/{itemid}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addToJournal(@PathVariable long itemid,
                                          @RequestBody String journalentry){

        itemService.addToJournal(itemid, journalentry);
        return new ResponseEntity<>(itemService.findItemById(itemid), HttpStatus.OK);
    }

    @DeleteMapping(value = "/journal/{journalentryid}")
    public ResponseEntity<?> deleteJournalEntryById(HttpServletRequest request,
                                            @PathVariable
                                                    long journalentryid)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        itemService.deleteFromJournal(journalentryid);
        return new ResponseEntity<>(HttpStatus.OK);
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

    @PutMapping(value = "/item/{itemid}/journal/{journalid}")
    public ResponseEntity<?> UpdateJournalById(HttpServletRequest request,
                                            @PathVariable
                                                long itemid,
                                            @PathVariable
                                                    long journalid,
                                            @RequestBody Journal newJournal) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        Item itemToUpdate = itemService.findItemById(itemid);
        newJournal.setItem(itemToUpdate);

        itemService.updateJournal(journalid, newJournal);
        return new ResponseEntity<>(itemToUpdate, HttpStatus.OK);
    }
}
