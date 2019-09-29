package com.lambdaschool.bucketlist.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long requestid;

    private String friendusername;

    @Column(nullable = false)
    private String requester;

    private boolean accepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid",
            nullable = false)
    @JsonIgnoreProperties({"friend", "hibernateLazyInitializer"})
    private User user;


    public Friend(String friendusername, String requester, boolean accepted, User user) {
        this.friendusername = friendusername;
        this.requester = requester;
        this.accepted = accepted;
        this.user = user;
    }

    public Friend() {
    }

    public long getRequestid() {
        return requestid;
    }

    public void setRequestid(long requestid) {
        this.requestid = requestid;
    }

    public String getFriendusername() {
        return friendusername;
    }

    public void setFriendusername(String friendusername) {
        this.friendusername = friendusername;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
