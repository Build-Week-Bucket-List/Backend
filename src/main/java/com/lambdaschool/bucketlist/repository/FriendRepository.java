package com.lambdaschool.bucketlist.repository;

import com.lambdaschool.bucketlist.models.Friend;
import org.springframework.data.repository.CrudRepository;

public interface FriendRepository extends CrudRepository<Friend, Long> {

}
