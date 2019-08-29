package com.lambdaschool.bucketlist.services;

import com.lambdaschool.bucketlist.models.Item;
import com.lambdaschool.bucketlist.models.User;

import java.util.List;

public interface UserService
{

    List<User> findAll();

    User findUserById(long id);

    void delete(long id);

    User save(User user);

    User update(User user, long id);

    List<String> searchUsers(String username);

    List<Item> searchUsersLike(String username);


}