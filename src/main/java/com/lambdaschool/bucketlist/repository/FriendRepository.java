package com.lambdaschool.bucketlist.repository;

import com.lambdaschool.bucketlist.models.Friend;
import com.lambdaschool.bucketlist.view.GetFriends;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends CrudRepository<Friend, Long> {

    @Query(value = "SELECT * FROM friends WHERE LOWER(friendusername) = :myfriendslist AND accepted = false", nativeQuery = true)
    List<Friend> getFriendRequests(@Param("myfriendslist") String username);
//      List<Friend> getFriendRequests();
    @Query(value = "SELECT * FROM friends WHERE accepted = true AND friendusername = :username OR requester = :username", nativeQuery = true)
    List<Friend> getAcceptedFriends(@Param("username") String user);


    @Query(value = "SELECT * FROM friends WHERE friendusername = :username OR friendusername = :requester AND requester = :requester OR requester = :username", nativeQuery = true)
    Friend searchIfRequestExists(@Param("requester") String requester, @Param("username") String username);
}
