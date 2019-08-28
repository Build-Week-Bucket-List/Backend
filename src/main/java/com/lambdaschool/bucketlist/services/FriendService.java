package com.lambdaschool.bucketlist.services;

import com.lambdaschool.bucketlist.models.Friend;
import com.lambdaschool.bucketlist.models.User;

import java.util.List;
import java.util.Optional;

public interface FriendService {
    Friend sendRequest(String request, String requester);

    Optional<Friend> findRequestById(long id);

    Friend save(Friend friend);

    Friend update(long requestid);

    List<Friend> getMyFriends(String username);

    List<Friend> getAcceptedFriends(String username);
}
