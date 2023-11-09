package com.vijay.travel.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.vijay.travel.model.User;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> getUserByUsername(String username);

    User createUser(User user);

    User editUser(User user);

    User deleteUser(String username);
}
