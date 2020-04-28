package ru.javamentor.OAuth2Practice.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.OAuth2Practice.entity.User;
import ru.javamentor.OAuth2Practice.exception.UserAlreadyExistsException;
import ru.javamentor.OAuth2Practice.repository.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            throw new UserAlreadyExistsException("Username " + user.getUsername() + " already exists");
        }
        update(user);
    }

    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User get(Long id) {
        final Optional<User> UserById = userRepository.findById(id);
        return UserById.orElse(null);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}

