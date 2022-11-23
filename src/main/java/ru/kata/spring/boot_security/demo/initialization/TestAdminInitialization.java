package ru.kata.spring.boot_security.demo.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.Repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.Services.RoleService;
import ru.kata.spring.boot_security.demo.Services.UserService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class TestAdminInitialization {

    private final RoleService roleService;
    private final UserService userService;
    private RoleRepository roleRepository;


    @Autowired
    public TestAdminInitialization(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initRoles() {
        Set<Role> roles = new HashSet<>();
        Optional<Role> roleAdmin = Optional.ofNullable(roleService.findRoleByName("ROLE_ADMIN"));
        Optional<Role> roleUser = Optional.ofNullable(roleService.findRoleByName("ROLE_USER"));
        if (roleAdmin.isEmpty()) {
            roles.add(new Role("ROLE_ADMIN"));
        }
        if (roleUser.isEmpty()) {
            roles.add(new Role("ROLE_USER"));
        }
        roleRepository.saveAll(roles);
    }

    @PostConstruct
    private void createTestAdmin() {
        Optional<User> admin = Optional.ofNullable(userService.findUserByEmail("testAdmin@mail.ru"));
        if (admin.isEmpty()) {
            User testAdmin = new User("testAdmin@mail.ru", "testadmin");
            testAdmin.setUserRoles(roleService.getAllRoles());
            userService.saveUser(testAdmin);
        }
    }
}
