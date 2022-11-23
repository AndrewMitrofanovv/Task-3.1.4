package ru.kata.spring.boot_security.demo.Services;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.Set;

public interface RoleService {

    Set<Role> getAllRoles();

    void save(Role role);

    void saveAll(Set<Role> roles);

    Role findRoleByName(String name);
}
