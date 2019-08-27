package com.lambdaschool.bucketlist.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "journals")
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long journalentryid;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemid")
    @JsonIgnoreProperties({"item", "hibernateLazyInitializer"})
    private Item item;

    @Column(nullable = false)
    private String entry;

    public Journal(Item item, String entry) {
        this.item = item;
        this.entry = entry;
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
}
