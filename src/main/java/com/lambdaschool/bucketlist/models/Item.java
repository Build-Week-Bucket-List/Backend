package com.lambdaschool.bucketlist.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

@ApiModel(value = "Item", description = "Item object")
@Entity
@Table(name = "items")
public class Item extends Auditable
{
    @ApiModelProperty(name = "itemid", value = "primary key for items in database. Automatically generated", example = "12", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemid;

    @ApiModelProperty(name = "itemtitle", value = "Title of bucket list item", required = true, example = "Climbing MT. Everest")
    @Column(nullable = false)
    private String itemtitle;

    @ApiModelProperty(name = "itemdesc", value = "Description of bucket list item", required = false, example = "I hope to climb within 5 years")
    private String itemdesc;

    @ApiModelProperty(name = "userid", value = "Automatically set by oauth", required = true)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid",
                nullable = false)
    @JsonIgnoreProperties({"item", "hibernateLazyInitializer"})
    private User user;

    private boolean completed;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "journalentryid")
    @JsonIgnoreProperties({"journal", "hibernateLazyInitializer"})
    private List<Journal> journal;


    public Item()
    {
    }

    public Item(String itemtitle, User user) {
        this.itemtitle = itemtitle;
        this.user = user;
    }

    public Item(String itemtitle, String itemdesc, User user) {
        this.itemtitle = itemtitle;
        this.itemdesc = itemdesc;
        this.user = user;
    }

    public Item(String itemtitle, String itemdesc, User user, boolean completed, List<Journal> journal) {
        this.itemtitle = itemtitle;
        this.itemdesc = itemdesc;
        this.user = user;
        this.completed = completed;
        this.journal = journal;
    }

    public long getItemid() {
        return itemid;
    }

    public void setItemid(long itemid) {
        this.itemid = itemid;
    }

    public String getItemtitle() {
        return itemtitle;
    }

    public void setItemtitle(String itemtitle) {
        this.itemtitle = itemtitle;
    }

    public String getItemdesc() {
        return itemdesc;
    }

    public void setItemdesc(String itemdesc) {
        this.itemdesc = itemdesc;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}