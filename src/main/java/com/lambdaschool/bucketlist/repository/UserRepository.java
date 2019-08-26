package com.lambdaschool.bucketlist.repository;

import com.lambdaschool.bucketlist.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUsername(String username);
}
