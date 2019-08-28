package com.lambdaschool.bucketlist.services;

import com.lambdaschool.bucketlist.models.Friend;
import com.lambdaschool.bucketlist.models.User;
import com.lambdaschool.bucketlist.repository.FriendRepository;
import com.lambdaschool.bucketlist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service(value = "friendService")
@Transactional
public class FriendServiceImpl implements FriendService{
    @Autowired
    private FriendRepository friendrepos;

    @Autowired
    private UserRepository userrepos;

    @Override
    public Friend sendRequest(String request, String requester) {
//        if(friendrepos.searchIfRequestExists(request, requester) != null){
//            System.out.println("********************************************************************     The request doesn't exist" );
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            User currentUser = userrepos.findByUsername(authentication.getName());
//            Friend newRequest = new Friend();
//            newRequest.setFriendusername(request);
//            newRequest.setRequester(requester);
//            newRequest.setUser(currentUser);
//            return friendrepos.save(newRequest);
//        } else {
//            System.out.println("***************************************************** It exists");
//
//            return null;
//        }
//
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userrepos.findByUsername(authentication.getName());
        Friend newRequest = new Friend();
        newRequest.setFriendusername(request);
        newRequest.setRequester(requester);
        newRequest.setUser(currentUser);
//        System.out.println(friendrepos.searchIfRequestExists(request, requester));
        return friendrepos.save(newRequest);


    }

    @Override
    public Optional<Friend> findRequestById(long id) {
        return friendrepos.findById(id);
    }

    @Override
    public Friend save(Friend friend) {
        return friendrepos.save(friend);
    }

    @Override
    @Transactional
    public Friend update(long requestid) {
    Friend newFriend = friendrepos.findById(requestid).orElseThrow(() -> new EntityNotFoundException(Long.toString(requestid)));

    newFriend.setAccepted(!newFriend.isAccepted());
    return friendrepos.save(newFriend);
    }

    @Override
    public List<Friend> getMyFriends(String username) {
        System.out.println("*******************************************************************************************" + friendrepos.getFriendRequests(username));
        return friendrepos.getFriendRequests(username.toLowerCase());
    }

    @Override
    public List<Friend> getAcceptedFriends(String username) {
        return friendrepos.getAcceptedFriends(username.toLowerCase());
    }
}
