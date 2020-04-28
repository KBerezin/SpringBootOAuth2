package ru.javamentor.OAuth2Practice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.javamentor.OAuth2Practice.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole (String role);
}
