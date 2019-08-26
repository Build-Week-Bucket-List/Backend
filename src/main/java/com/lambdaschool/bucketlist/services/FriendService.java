package com.lambdaschool.bucketlist.services;

import com.lambdaschool.bucketlist.models.Friend;

public interface FriendService {
    Friend sendRequest(String request, String requester);

    Friend save(Friend friend);

    Friend update(long requestid);
}
