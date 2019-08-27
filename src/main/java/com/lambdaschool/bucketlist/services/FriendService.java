package com.lambdaschool.bucketlist.services;

import com.lambdaschool.bucketlist.models.Friend;
import com.lambdaschool.bucketlist.models.UserRoles;

import java.util.List;

public interface FriendService {
    Friend sendRequest(String request, String requester);

    Friend save(Friend friend);

    Friend update(long requestid);

    List<Friend> getMyFriends(long id);
}
