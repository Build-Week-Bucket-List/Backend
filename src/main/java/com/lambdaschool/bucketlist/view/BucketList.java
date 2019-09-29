package com.lambdaschool.bucketlist.view;

import java.util.Date;

public interface BucketList {
    long getItemid();
    String getItem();
    boolean getCompleted();
    long getUserid();
    Date getCreated_date();
}
