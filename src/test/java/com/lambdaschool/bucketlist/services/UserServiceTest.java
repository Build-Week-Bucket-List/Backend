package com.lambdaschool.bucketlist.services;

import com.lambdaschool.bucketlist.BucketListApplication;
import com.lambdaschool.bucketlist.BucketListApplicationTest;
import com.lambdaschool.bucketlist.models.Role;
import com.lambdaschool.bucketlist.models.User;
import com.lambdaschool.bucketlist.models.UserRoles;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BucketListApplicationTest.class)
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    void tearDown() {
    }

    @Test
    void findAll() {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    void findUserById() {
        assertEquals("TestBob", userService.findUserById(12).getUsername());
    }

    @Test
    void delete() {
    }

    @Test
    void save() {
//        Role r2 = new Role("user");
//        List users = new ArrayList<>();
//        users.add(new UserRoles(new User(), r2));
//        User newUser = new User("NewUserTest", "password", users);
        userService.save(new User("NewUsernametest", "password"));
    assertEquals(6, userService.findAll().size());
    }

    @Test
    void update() {
    }

    @Test
    void searchUsers() {
    }

    @Test
    void searchUsersLike() {
    }
}