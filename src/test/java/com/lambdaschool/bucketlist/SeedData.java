package com.lambdaschool.bucketlist;

import com.lambdaschool.bucketlist.models.Item;
import com.lambdaschool.bucketlist.models.Role;
import com.lambdaschool.bucketlist.models.User;
import com.lambdaschool.bucketlist.models.UserRoles;
import com.lambdaschool.bucketlist.repository.RoleRepository;
import com.lambdaschool.bucketlist.repository.UserRepository;
import com.lambdaschool.bucketlist.services.RoleService;
import com.lambdaschool.bucketlist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

////
//@Transactional
//@Component
public class SeedData implements CommandLineRunner
{
    private UserRepository userrepos;
    private RoleRepository rolerepos;

    public SeedData(UserRepository userrepos, RoleRepository rolerepos){
        this.userrepos = userrepos;
        this.rolerepos = rolerepos;
    }


    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        rolerepos.save(r1);
        rolerepos.save(r2);
        rolerepos.save(r3);

 // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));
        admins.add(new UserRoles(new User(), r3));
        User u1 = new User("Testadmin", "password", admins);
        u1.getItems().add(new Item("A creative man is motivated by the desire to achieve, not by the desire to beat others", u1));
        u1.getItems().add(new Item("The question isn't who is going to let me; it's who is going to stop me.", u1));
        userrepos.save(u1);

        // data, user
        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(), r3));
        datas.add(new UserRoles(new User(), r2));
        User u2 = new User("Testcinnamon", "1234567", datas);
        userrepos.save(u2);

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u3 = new User("Testbarnbarn", "ILuvM4th!", users);
        u3.getItems().add(new Item("Live long and prosper", u3));
        u3.getItems().add(new Item("The enemy of my enemy is the enemy I kill last", u3));
        u3.getItems().add(new Item("Beam me up", u3));
        userrepos.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u4 = new User("TestBob", "password", users);
        userrepos.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u5 = new User("TeatJane", "password", users);
        userrepos.save(u5);


    }
}