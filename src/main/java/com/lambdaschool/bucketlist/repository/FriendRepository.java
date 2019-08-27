package com.lambdaschool.bucketlist.repository;

import com.lambdaschool.bucketlist.models.Friend;
import com.lambdaschool.bucketlist.view.GetFriends;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendRepository extends CrudRepository<Friend, Long> {

    @Query(value = "SELECT * FROM friends WHERE userid = 4", nativeQuery = true)
    List<Friend> getFriendRequests(long id);
}
