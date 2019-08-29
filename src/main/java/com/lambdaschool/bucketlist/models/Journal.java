package com.lambdaschool.bucketlist.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "journals")
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long journalentryid;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemid", nullable = false)
    @JsonIgnoreProperties({"item", "hibernateLazyInitializer"})
    private Item item;


    @Column(nullable = false)
    private String entry;

    private String username;


    public Journal(Item item, String entry) {
        this.item = item;
        this.entry = entry;
    }

    public Journal(Item item, String entry, String username) {
        this.item = item;
        this.entry = entry;
        this.username = username;
    }

    public Journal() {
    }

    public long getJournalentryid() {
        return journalentryid;
    }

    public void setJournalentryid(long journalentryid) {
        this.journalentryid = journalentryid;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
