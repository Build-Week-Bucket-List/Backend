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
    @JoinColumn(name = "itemid",
            nullable = false)
    @JsonIgnoreProperties({"journal", "hibernateLazyInitializer"})
    private User user;

    @Column(nullable = false)
    private String entry;

    public Journal(User user, String entry) {
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }
}
