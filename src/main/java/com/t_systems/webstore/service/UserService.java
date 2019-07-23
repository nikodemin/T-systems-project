package com.t_systems.webstore.service;

import com.t_systems.webstore.entity.Address;
import com.t_systems.webstore.entity.User;

import java.util.List;

public interface UserService
{
    User findUser(String username);
    List<User> getAllUsers();
    boolean addUser(User user);
    void addAddress(Address address);
}
