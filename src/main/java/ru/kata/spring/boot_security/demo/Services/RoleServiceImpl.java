package ru.kata.spring.boot_security.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.Repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.models.Role;


import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> findAllRole() {
        return new HashSet<>(roleRepository.findAll());
    }

    public Role findRoleByAuthority(String name) {
        return roleRepository.findByName(name);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }
}
