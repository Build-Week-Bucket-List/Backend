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

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
    public List<Item> findItemByUserName(String username) throws ResourceNotFoundException
    {
        User currentUser = userrepos.findByUsername(username);
        return listrepos.findItemsByUserId(currentUser.getUserid());
    }

    @Override
    public Journal addToJournal(long id, String journalEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userrepos.findByUsername(authentication.getName());

        Journal newEntry = new Journal();
        newEntry.setEntry(journalEntry);
        newEntry.setItem(findItemById(id));
        newEntry.setUsername(currentUser.getUsername());
        System.out.println("*********************************"  + listrepos.findById(id));
        return journalrepos.save(newEntry);
    }

    @Override
    public void deleteFromJournal(long id) {
        if(journalrepos.findById(id).isPresent()){
            journalrepos.deleteById(id);
        } else {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

    @Override
    @Transactional
    public Item update(long id, Item item) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userrepos.findByUsername(authentication.getName());

        Item currentItem = listrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));

        currentItem.setItemtitle(item.getItemtitle());
        currentItem.setItemdesc(item.getItemdesc());
        currentItem.setCompleted(item.isCompleted());
        currentItem.setImage(item.getImage());
        currentItem.setItemid(item.getItemid());
        currentItem.setUser(currentUser);
        currentItem.setCreated(item.getCreated());

//        if(item.getJournal().size() > 0){
//            for (Journal j : item.getJournal())
//                {
//                    currentItem.getJournal().add(new Journal(j.getItem(), j.getEntry()));
//                }
//
//        }


//        item.setUser(currentUser);
//        item.setItemid(id);
        return listrepos.save(currentItem);
    }

    @Override
    @Transactional
    public Journal updateJournal(long id, Journal journalEntry) {
        if(journalrepos.findById(id).isPresent()){
            Journal newJournalEntry = new Journal();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userrepos.findByUsername(authentication.getName());
            newJournalEntry.setUsername(currentUser.getUsername());
            newJournalEntry.setEntry(journalEntry.getEntry());
            newJournalEntry.setItem(journalEntry.getItem());
            newJournalEntry.setJournalentryid(id);
            journalrepos.findById(id);
            return journalrepos.save(newJournalEntry);
        } else {
            throw new ResourceNotFoundException(Long.toString(id));
        }

    }
}
