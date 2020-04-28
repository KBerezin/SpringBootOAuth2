package ru.javamentor.OAuth2Practice.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.javamentor.OAuth2Practice.entity.User;
import ru.javamentor.OAuth2Practice.exception.UserAlreadyExistsException;

import java.util.List;

public interface UserService {

    UserDetails loadUserByUsername(String username);

    void save(User user) throws UserAlreadyExistsException;

    void update(User user);

    List<User> listAll();

    User get(Long id);

    void delete(Long id);
}
