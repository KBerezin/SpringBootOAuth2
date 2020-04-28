package ru.javamentor.OAuth2Practice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.OAuth2Practice.entity.Role;
import ru.javamentor.OAuth2Practice.repository.RoleRepository;

import java.util.List;


@Service
@Transactional
public class RoleServiceImpl {

    private final RoleRepository repo;

    public RoleServiceImpl(RoleRepository repo) {
        this.repo = repo;
    }

    public void save(Role user) {
        repo.save(user);
    }

    public List<Role> listAll() {
        return repo.findAll();
    }

    public Role get(Long id) {
        return repo.findById(id).get();
    }

    public Role findByRole(String role) {
        return repo.findByRole(role);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
