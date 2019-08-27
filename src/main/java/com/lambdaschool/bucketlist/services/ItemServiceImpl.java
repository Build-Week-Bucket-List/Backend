package com.lambdaschool.bucketlist.services;

import com.lambdaschool.bucketlist.exceptions.ResourceNotFoundException;
import com.lambdaschool.bucketlist.models.Item;
import com.lambdaschool.bucketlist.models.Journal;
import com.lambdaschool.bucketlist.models.User;
import com.lambdaschool.bucketlist.repository.BucketListRepository;
import com.lambdaschool.bucketlist.repository.JournalRepository;
import com.lambdaschool.bucketlist.repository.UserRepository;
import com.lambdaschool.bucketlist.view.BucketList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "itemService")
public class ItemServiceImpl implements ItemService
{
    @Autowired
    private BucketListRepository listrepos;

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private JournalRepository journalrepos;

    @Override
    public List<Item> findAll()
    {
        List<Item> list = new ArrayList<>();
        listrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }



    @Override
    public Item findItemById(long id)
    {
        return listrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    @Override
    public void delete(long id)
    {
        if (listrepos.findById(id).isPresent())
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (listrepos.findById(id).get().getUser().getUsername().equalsIgnoreCase(authentication.getName()))
            {
                listrepos.deleteById(id);
            } else
            {
                throw new ResourceNotFoundException(id + " " + authentication.getName());
            }
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Item save(Item item)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userrepos.findByUsername(authentication.getName());

        item.setUser(currentUser);

        return listrepos.save(item);
    }

    @Override
    public List<Item> findByUserName(String username)
    {
        List<Item> list = new ArrayList<>();
        listrepos.findAll().iterator().forEachRemaining(list::add);

        list.removeIf(q -> !q.getUser().getUsername().equalsIgnoreCase(username));
        return list;
    }

    @Override
    public Journal addToJournal(long id, String journalEntry) {
        Journal newEntry = new Journal();
        newEntry.setEntry(journalEntry);
        newEntry.setItem(findItemById(id));
        System.out.println("*********************************"  + listrepos.findById(id));
        return journalrepos.save(newEntry);
    }

    @Override
    public Item update(long id, Item item) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userrepos.findByUsername(authentication.getName());

        item.setUser(currentUser);
        item.setItemid(id);
        return listrepos.save(item);
    }
}
