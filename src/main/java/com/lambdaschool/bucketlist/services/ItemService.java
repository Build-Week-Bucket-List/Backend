package com.lambdaschool.bucketlist.services;

import com.lambdaschool.bucketlist.models.Item;
import com.lambdaschool.bucketlist.models.Journal;
import com.lambdaschool.bucketlist.view.BucketList;

import java.util.List;

public interface ItemService
{
    List<Item> findAll();

    Item findItemById(long id);

    List<Item> findByUserName(String username);

    void delete(long id);

    Item save(Item item);

    Journal addToJournal(long id, String journalEntry);

    Item update(long id, Item item);

}
