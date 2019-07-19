package com.t_systems.webstore.service;

import com.t_systems.webstore.dao.UserDao;
import com.t_systems.webstore.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
    private final UserDao userDao;


    @Override
    public User findUser(String username)
    {
        return userDao.getUser(username);
    }

    @Override
    public List<User> getAllUsers()
    {
        return userDao.getAllUsers();
    }

    @Override
    public User addUser(User user)
    {
        if(userDao.getUser(user.getUsername()) == null &&
                userDao.getUserByEmail(user.getEmail()) == null)
        {
            return userDao.addUser(user);
        }
        return null;
    }
}
