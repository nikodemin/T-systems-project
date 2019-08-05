package com.t_systems.webstore.service.api;

import com.t_systems.webstore.exception.UserExistsException;
import com.t_systems.webstore.model.dto.UserDto;
import com.t_systems.webstore.model.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

public interface UserService {
    User findUser(String username);

    User findUserByEmail(String email);

    List<User> getAllUsers();

    void addUser(User user) throws UserExistsException;

    void changeUser(String username, UserDto userDto) throws ParseException;
}
