package com.lambdaschool.bucketlist.services;

import com.lambdaschool.bucketlist.exceptions.ResourceNotFoundException;
import com.lambdaschool.bucketlist.models.Item;
import com.lambdaschool.bucketlist.models.User;
import com.lambdaschool.bucketlist.models.UserRoles;
import com.lambdaschool.bucketlist.repository.FriendRepository;
import com.lambdaschool.bucketlist.repository.RoleRepository;
import com.lambdaschool.bucketlist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService
{

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private RoleRepository rolerepos;

    @Autowired
    private FriendRepository friendrepos;

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userrepos.findByUsername(username);
        if (user == null)
        {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthority());
    }

    public User findUserById(long id) throws ResourceNotFoundException
    {
        return userrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        userrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(long id)
    {
        if (userrepos.findById(id).isPresent())
        {
            userrepos.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public User save(User user)
    {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPasswordNoEncrypt(user.getPassword());

        ArrayList<UserRoles> newRoles = new ArrayList<>();
        for (UserRoles ur : user.getUserRoles())
        {
            newRoles.add(new UserRoles(newUser, ur.getRole()));
        }
        newUser.setUserRoles(newRoles);

        for (Item i : user.getItems())
        {
            newUser.getItems().add(new Item(i.getItemtitle(), newUser));
        }

        return userrepos.save(newUser);
    }


    @Transactional
    @Override
    public User update(User user, long id)
    {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User currentUser = userrepos.findByUsername(authentication.getName());

    if (currentUser != null)
    {
        if (id == currentUser.getUserid())
        {
            if (user.getUsername() != null)
            {
                currentUser.setUsername(user.getUsername());
            }

            if (user.getPassword() != null)
            {
                currentUser.setPasswordNoEncrypt(user.getPassword());
            }

            if (user.getUserRoles().size() > 0)
            {
                // with so many relationships happening, I decided to go
                // with old school queries
                // delete the old ones
                rolerepos.deleteUserRolesByUserId(currentUser.getUserid());

                // add the new ones
                for (UserRoles ur : user.getUserRoles())
                {
                    rolerepos.insertUserRoles(id, ur.getRole().getRoleid());
                }
            }

            if (user.getItems().size() > 0)
            {
                for (Item i : user.getItems())
                {
                    currentUser.getItems().add(new Item(i.getItemtitle(), currentUser));
                }
            }

            return userrepos.save(currentUser);
        } else
        {
            throw new ResourceNotFoundException(id + " Not current user");
        }
    } else
    {
        throw new ResourceNotFoundException(authentication.getName());
    }

}

    @Override
    public List<String> searchUsers(String username) {
        return userrepos.findLikeUsername(username);
    }

    @Override
    public List<Item> searchUsersLike(String username) {
        return userrepos.findByUsernameLike(username.toLowerCase());
    }
}
