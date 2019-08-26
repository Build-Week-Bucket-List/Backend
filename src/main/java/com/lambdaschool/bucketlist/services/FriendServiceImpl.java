package com.lambdaschool.bucketlist.services;

import com.lambdaschool.bucketlist.models.Friend;
import com.lambdaschool.bucketlist.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service(value = "friendService")
@Transactional
public class FriendServiceImpl implements FriendService{
    @Autowired
    private FriendRepository friendrepos;

    @Override
    public Friend sendRequest(String request, String requester) {
        Friend newRequest = new Friend();
        newRequest.setFriendusername(request);
        newRequest.setRequester(requester);
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
}
