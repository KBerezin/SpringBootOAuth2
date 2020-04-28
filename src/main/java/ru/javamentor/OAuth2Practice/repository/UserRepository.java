package ru.javamentor.OAuth2Practice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.javamentor.OAuth2Practice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
