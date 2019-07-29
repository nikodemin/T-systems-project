package com.t_systems.webstore.service.impl;

import com.t_systems.webstore.dao.UserDao;
import com.t_systems.webstore.exception.UserExistsException;
import com.t_systems.webstore.model.dto.UserDto;
import com.t_systems.webstore.model.entity.Address;
import com.t_systems.webstore.model.entity.User;
import com.t_systems.webstore.model.enums.UserRole;
import com.t_systems.webstore.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Configuration
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public User findUser(String username) {
        return userDao.getUser(username);
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public void addUser(User user) throws UserExistsException {

        if (userDao.getUser(user.getUsername()) == null &&
                userDao.getUserByEmail(user.getEmail()) == null) {

            user.setPassword(passwordEncoder().encode(user.getPassword()));
            userDao.addUser(user);
            return;
        }
        throw new UserExistsException();
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userDao.getUser(username) != null)
            return userDao.getUser(username);
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public User convertToUser(UserDto userDto) {
        Address address = modelMapper.map(userDto, Address.class);
        User user = modelMapper.map(userDto, User.class);
        user.setRole(UserRole.USER);
        user.setAddress(address);
        return user;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
