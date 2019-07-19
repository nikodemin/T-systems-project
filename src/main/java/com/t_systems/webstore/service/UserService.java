package com.t_systems.webstore.service;

import com.t_systems.webstore.entity.User;

import java.util.List;

public interface UserService
{
    User findUser(String username);
    List<User> getAllUsers();
    User addUser(User user);
}
