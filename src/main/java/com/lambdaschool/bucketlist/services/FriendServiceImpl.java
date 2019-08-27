package com.lambdaschool.bucketlist.services;

import com.lambdaschool.bucketlist.models.Friend;
import com.lambdaschool.bucketlist.models.User;
import com.lambdaschool.bucketlist.models.UserRoles;
import com.lambdaschool.bucketlist.repository.FriendRepository;
import com.lambdaschool.bucketlist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service(value = "friendService")
@Transactional
public class FriendServiceImpl implements FriendService{
    @Autowired
    private FriendRepository friendrepos;

    @Autowired
    private UserRepository userrepos;

    @Override
    public Friend sendRequest(String request, String requester) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userrepos.findByUsername(authentication.getName());
        Friend newRequest = new Friend();
        newRequest.setFriendusername(request);
        newRequest.setRequester(requester);
        newRequest.setUser(currentUser);
        return friendrepos.save(newRequest);
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
    public List<Friend> getMyFriends(long id) {
        return friendrepos.getFriendRequests(id);
    }
}
