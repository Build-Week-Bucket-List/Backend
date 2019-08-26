package com.lambdaschool.bucketlist.repository;

import com.lambdaschool.bucketlist.models.Item;
import com.lambdaschool.bucketlist.view.BucketList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BucketListRepository extends CrudRepository<Item, Long>
{
//    SELECT * FROM ITEMS WHERE userid = 4
//    @Query(value = "SELECT * FROM items WHERE userid = :id", nativeQuery = true)
//    List<BucketList> findByUserName(String username);
}
